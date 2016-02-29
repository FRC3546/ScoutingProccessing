package us.andrewdickinson.frc.scoutingproccessing;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andrew on 2/28/16.
 */
public class TBACommunication {
    private static TBACommunication instance = new TBACommunication(DataDefinitions.TBAConnection.eventCode);
    private String eventCode;
    private JSONArray matches;

    public static TBACommunication getInstance() {
        return instance;
    }

    public TBACommunication(String eventCode){
        this.eventCode = eventCode;
        try {
            getMatches();
        } catch (IOException e){
            e.printStackTrace();
        }
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

        throw new IllegalArgumentException();
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
}
