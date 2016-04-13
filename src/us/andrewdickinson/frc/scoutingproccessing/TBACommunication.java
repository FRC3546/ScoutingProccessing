package us.andrewdickinson.frc.scoutingproccessing;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by Andrew on 2/28/16.
 */
public class TBACommunication {
    private static TBACommunication instance = new TBACommunication(DataDefinitions.TBAConnection.eventCode);
    private String eventCode;
    private JSONArray matches;
    private JSONArray ranks;
    private JSONArray teams;
    private JSONArray alliances;

    private ArrayList<Integer> eventDays;

    public static TBACommunication getInstance() {
        return instance;
    }

    public TBACommunication(String eventCode){
        this.eventCode = eventCode;
        eventDays = new ArrayList<>();
        try {
            getMatches();
            getRankings();
            getTeams();
            getElimAlliances();
            if (scheduleGenerated()) setupEventDays();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean scheduleGenerated(){
        return  matches.length() > 0;
    }

    private void setupEventDays(){
        if (!scheduleGenerated()) throw new UnsupportedOperationException();

        for (int i =  0; i < matches.length(); i++){
            JSONObject alliances = matches.getJSONObject(i).getJSONObject("alliances");

            long t = matches.getJSONObject(i).getLong("time");

            int day = LocalDateTime.ofEpochSecond(t, 0, ZoneOffset.UTC).get(ChronoField.DAY_OF_YEAR);
            if (!eventDays.contains(day)){
                eventDays.add(day);
            }
        }
    }

    public int getNumberOfMatches(){
        return matches.length();
    }

    public int getRank(int team){
        for (int i = 0; i < ranks.length(); i++){
            if (ranks.getJSONArray(i).getString(1).equals(Integer.toString(team))){
                return Integer.parseInt(ranks.getJSONArray(i).getString(0));
            }
        }

        return 0;
    }

    public String getTeamName(int team){
        for (int i = 0; i < teams.length(); i++){
            if (teams.getJSONObject(i).getInt("team_number") == team){
                return teams.getJSONObject(i).getString("nickname");
            }
        }

        return "NULL";
    }

    /**
     * Get a list of the teams at this event
     * @return the list of  teams
     */
    public ArrayList<Integer> getTeamNumbers(){
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < teams.length(); i++){
            numbers.add(Integer.parseInt(teams.getJSONObject(i).getString("key").substring(3)));
        }

        return numbers;
    }

    /**
     * Converts a match number, alliance, and station number into the corresponding team number
     * @param match The qualification match number to look up
     * @param alliance The alliance that the target team is on
     * @param station The station (1-indexed) that they occupy
     * @return The team number for the specified team
     * @throws IOException If a TBA api call fails
     */
    public int getTeam(int match, Alliance alliance, int station) throws IOException{
        if (matches == null) getMatches();

        for (int i =  0; i < matches.length(); i++){
            if (matches.getJSONObject(i).getInt("match_number") == match){
                JSONObject alliances = matches.getJSONObject(i).getJSONObject("alliances");

                JSONArray teams;
                if (alliance == Alliance.Red){
                    teams = alliances.getJSONObject("red").getJSONArray("teams");
                } else {
                    teams = alliances.getJSONObject("blue").getJSONArray("teams");
                }

                return Integer.parseInt(teams.getString(station - 1).substring(3));
            }
        }

        throw new IllegalArgumentException();
    }

    public int[] getRedTeams(int match) {
        for (int i =  0; i < matches.length(); i++){
            if (matches.getJSONObject(i).getInt("match_number") == match){
                JSONObject alliances = matches.getJSONObject(i).getJSONObject("alliances");

                JSONArray teams = alliances.getJSONObject("red").getJSONArray("teams");

                int[] team_nums = new int[3];
                for (int j = 0; j <= 2; j++){
                    team_nums[j] = Integer.parseInt(teams.getString(j).substring(3));
                }

                return team_nums;
            }
        }

        throw new IllegalArgumentException("Match number not found");
    }

    public int[] getBlueTeams(int match){
        for (int i =  0; i < matches.length(); i++){
            if (matches.getJSONObject(i).getInt("match_number") == match){
                JSONObject alliances = matches.getJSONObject(i).getJSONObject("alliances");

                JSONArray teams = alliances.getJSONObject("blue").getJSONArray("teams");

                int[] team_nums = new int[3];
                for (int j = 0; j <= 2; j++){
                    team_nums[j] = Integer.parseInt(teams.getString(j).substring(3));
                }

                return team_nums;
            }
        }

        throw new IllegalArgumentException();
    }

    public int[] getTeams(int match){
        return IntStream.concat(Arrays.stream(getRedTeams(match)), Arrays.stream(getBlueTeams(match))).toArray();
    }

    public int[] getAllianceTeams(int alliance){
        if (alliances == null) throw new UnsupportedOperationException();

        JSONObject allianceObj = alliances.getJSONObject(alliance - 1);
        JSONArray jsonTeams = allianceObj.getJSONArray("picks");

        int[] teams = new int[jsonTeams.length()];
        for (int i = 0; i < jsonTeams.length(); i++){
            teams[i] = Integer.parseInt(jsonTeams.getString(i).substring(3));
        }

        if (teams.length > 2){
            int temp = teams[0];
            teams[0] = teams[1];
            teams[1] = temp;
        }


        return teams;
    }

    private void getMatches() throws IOException{
        String url = DataDefinitions.TBAConnection.matches_url
                .replace(DataDefinitions.TBAConnection.eventCodeDelimiter, eventCode);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(DataDefinitions.TBAConnection.tbaAppIdHeaderName,
                DataDefinitions.TBAConnection.tbaAppId);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));

            String response = "";
            String line;
            while ((line = rd.readLine()) != null) {
                response += line;
            }

            JSONArray matches = new JSONArray(response);
            for (int i = matches.length() - 1; i >= 0; i--){
                if (!matches.getJSONObject(i)
                        .getString("comp_level").equals("qm")){
                    matches.remove(i);
                }
            }

            this.matches = matches;
        } catch (ClientProtocolException e){
            throw new IOException();
        } finally {
            if (res != null) res.close();
        }
    }

    private void getElimAlliances() throws IOException{
        String url = DataDefinitions.TBAConnection.alliances_url
                .replace(DataDefinitions.TBAConnection.eventCodeDelimiter, eventCode);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(DataDefinitions.TBAConnection.tbaAppIdHeaderName,
                DataDefinitions.TBAConnection.tbaAppId);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));

            String response = "";
            String line;
            while ((line = rd.readLine()) != null) {
                response += line;
            }

            JSONObject eventData = new JSONObject(response);
            JSONArray alliances = eventData.getJSONArray("alliances");

            this.alliances = alliances;
        } catch (ClientProtocolException e){
            throw new IOException();
        } finally {
            if (res != null) res.close();
        }
    }

    private void getRankings() throws IOException{
        String url = DataDefinitions.TBAConnection.rankings_url
                .replace(DataDefinitions.TBAConnection.eventCodeDelimiter, eventCode);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(DataDefinitions.TBAConnection.tbaAppIdHeaderName,
                DataDefinitions.TBAConnection.tbaAppId);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));

            String response = "";
            String line;
            while ((line = rd.readLine()) != null) {
                response += line;
            }

            JSONArray ranks = new JSONArray(response);

            this.ranks = ranks;
        } catch (ClientProtocolException e){
            throw new IOException();
        } finally {
            if (res != null) res.close();
        }
    }

    private void getTeams() throws IOException{
        String url = DataDefinitions.TBAConnection.teams_url
                .replace(DataDefinitions.TBAConnection.eventCodeDelimiter, eventCode);

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(DataDefinitions.TBAConnection.tbaAppIdHeaderName,
                DataDefinitions.TBAConnection.tbaAppId);

        CloseableHttpResponse res = null;
        try {
            res = httpClient.execute(httpGet);

            BufferedReader rd = new BufferedReader
                    (new InputStreamReader(res.getEntity().getContent()));

            String response = "";
            String line;
            while ((line = rd.readLine()) != null) {
                response += line;
            }

            this.teams = new JSONArray(response);
        } catch (ClientProtocolException e){
            throw new IOException();
        } finally {
            if (res != null) res.close();
        }
    }

    public boolean isMatchNotOnLastDay(int match){
        if (eventDays.size() == 0) throw new IllegalArgumentException("Must generate eventDays first");

        int last_day = eventDays.stream().max(Integer::compare).get();

        for (int i =  0; i < matches.length(); i++){
            if (matches.getJSONObject(i).getInt("match_number") == match){
                JSONObject alliances = matches.getJSONObject(i).getJSONObject("alliances");

                int t = matches.getJSONObject(i).getInt("time");
                int day = LocalDateTime.ofEpochSecond(t, 0, ZoneOffset.UTC).get(ChronoField.DAY_OF_YEAR);

                return day != last_day;
            }
        }

        throw new IllegalArgumentException("Match not found: " + match);
    }


    public void createPDF(String path) throws DocumentException, IOException{
        Document pdfDocument = new Document();
        PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(path));
        writer.setMargins(0,0,0,0);
        pdfDocument.open();

        Font reg = new Font(Font.FontFamily.TIMES_ROMAN, 18.0f);
        pdfDocument.add(new Phrase("Event Schedule - " + DataDefinitions.TBAConnection.eventCode, reg));

        PdfPTable table = getPDFMatchTable();
        table.setWidthPercentage(100);
        pdfDocument.add(table);

        pdfDocument.close();
    }

    public PdfPTable getPDFMatchTable(){
        Font small = new Font(Font.FontFamily.TIMES_ROMAN, 11.0f);

        PdfPTable table = new PdfPTable(7);
        table.addCell(new Phrase("Match", small));
        table.addCell(new Phrase("1", small));
        table.addCell(new Phrase("2", small));
        table.addCell(new Phrase("3", small));
        table.addCell(new Phrase("4", small));
        table.addCell(new Phrase("5", small));
        table.addCell(new Phrase("6", small));

        for (int i = 0; i < matches.length(); i++){
            PdfPCell cell = new PdfPCell(new Phrase((i + 1) + "", small));
            if (i % 2 == 0){
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            }

            table.addCell(cell);
            int[] teams = getTeams(i + 1);
            for (int team : teams){
                PdfPCell cell2 = new PdfPCell(new Phrase(team + "", small));
                if (i % 2 == 0){
                    cell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                }

                table.addCell(cell2);
            }
        }

        return table;
    }
}
