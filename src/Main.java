import at.dbpklausur.DBPKlausurHelper;
import at.dbpklausur.models.KlausurProjekte;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello JDBC");
        String url = "jdbc:sqlite:C:\\Users\\aurel\\OneDrive - FH JOANNEUM\\Dokumente\\JAVA\\DBPKlausur_ZOUAOUI.db";

        DBPKlausurHelper helper = new DBPKlausurHelper(url);
        helper.createTableKlausurProjekte();

        //Aufgabe 3
        KlausurProjekte k1 = new KlausurProjekte();
        k1.setProjektbezeichnung("Regenzeit");
        k1.setProjekttyp("Saison");
        k1.setBudget(1000);
        k1.setLaufzeit(365);
        helper.insertProjekt(k1);
        System.out.printf("Projekt %s wurde hinzugefügt, die neue id ist",k1.getProjektId());

        //Aufgabe 4a
        KlausurProjekte klausurProjekte = helper.getProjektById(2);
        System.out.printf("Projekt gefunden %s\n",klausurProjekte.toString());

        //Aufgabe 4b
        System.out.println("\nAlle Projekte sortiert mit Laufzeit und Projekttyp\n");
        List<KlausurProjekte> suchergebnis = helper.getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(40);
        System.out.println(suchergebnis);
        System.out.printf("Projekte gefunden %s\n",suchergebnis.toString());

        //Aufgabe 6a
        KlausurProjekte aufwendigstenProjekte = helper.getProjektMitDenMeistenAufgaben();
        System.out.println(aufwendigstenProjekte);

        //Aufgabe 6c
        System.out.println("\n AufwandInStunden AVG  " + helper.getDurchschnittAufwandInStundenByProjektId(2));

        //Aufgabe 7
        //Verschiedene Informationen über Datenbanken und Tabellen abrufen
        System.out.println("Die Datenbank “KlausurZouaouiDB” beinhaltet die folgenden Tabellen: ");
        helper.printTableNames();

        //Aufgabe 8)
        helper.createAdditionalTableAndFillWithSampleValues();

        //Aufgabe 9)
        helper.transferBudget(1,0,500);





    }
}