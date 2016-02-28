package us.andrewdickinson.frc.scoutingproccessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Andrew on 2/28/16.
 */
public class HeaderManagement {
    HashMap<String, Header> headers;
    ArrayList<String> sheet_headers;

    public HeaderManagement() {
        headers = new HashMap<>();
    }

    public void addSheetHeaders(ArrayList<String> sheet_headers){
        if (this.sheet_headers != null) throw new IllegalStateException();
        this.sheet_headers = sheet_headers;
    }

    public void addHeader(String unique_identifier, String sheet_header,
                          int occurrence_index){
        //Check if that unique_id is already is use
        if (headers.containsKey(unique_identifier)) throw new IllegalArgumentException("Unique Id already in use");

        //Check for a pre-existing entry with the same sheet_header and occurrence index
        // or an entry with the same header that isn't designated as repeated
        Iterator it = headers.entrySet().iterator();
        while (it.hasNext()){
            Header header = (Header) ((Map.Entry) it.next()).getValue();
            if (sheet_header.equals(header.getIdentifier()) &&
                    occurrence_index == header.getRepeat_number()){
                throw new IllegalArgumentException("Header already in use at that occurence");
            }

            if (sheet_header.equals(header.getIdentifier()) &&
                    !header.isRepeated()){
                throw new IllegalArgumentException("Header already in use and not marked for repeat");
            }
        }


        headers.put(unique_identifier, new Header(sheet_header, occurrence_index));
    }

    public void addHeader(String unique_identifier, String header){
        addHeader(unique_identifier, header, -1);
    }

    public int indexForUID(String unique_identifier){
        if (sheet_headers == null) throw new IllegalStateException("You must first add sheet headers");

        Header header_spec = headers.get(unique_identifier);
        if (header_spec == null) throw new IllegalArgumentException("Spec for Unique id not found");

        ArrayList<String> headers_with_identifier = new ArrayList<>();
        for (String header : sheet_headers){
            if (header.equals(header_spec.getIdentifier())){
                headers_with_identifier.add(header);
            }
        }

        if (headers_with_identifier.size() == 0){
            throw new IllegalStateException("The specified header is not included in the sheet headers");
        } else if (headers_with_identifier.size() == 1) {
            return sheet_headers.indexOf(headers_with_identifier.get(0));
        } else {
            if (header_spec.isRepeated()) {
                return sheet_headers.indexOf(headers_with_identifier.get(header_spec.getRepeat_number()));
            } else {
                throw new IllegalStateException("There are multiple entries when the spec specifies only one");
            }
        }
    }

    private class Header {
        String identifier;
        int repeat_number;

        public Header(String identifier) {
            this.identifier = identifier;
            this.repeat_number = -1;
        }

        public Header(String identifier, int repeat_number) {
            this.identifier = identifier;
            this.repeat_number = repeat_number;
        }

        public boolean isRepeated(){
            return repeat_number != -1;
        }

        public String getIdentifier() {
            return identifier;
        }

        public int getRepeat_number() {
            return repeat_number;
        }
    }
}
