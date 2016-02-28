package us.andrewdickinson.frc.scoutingproccessing;

import java.io.IOException;
import java.util.*;

/**
 * Created by Andrew on 2/28/16.
 */
public class Driver {
    public static void main(String[] args) throws IOException {
        ArrayList<Team> teams = Importer
                .importTeams("/home/Andrew/gdrive/Master_Spreadsheet/Master_Spreadsheet.xlsx");

    }
}
