import at.dbpklausur.DBPKlausurHelper;
import at.dbpklausur.models.KlausurProjektaufgaben;
import at.dbpklausur.models.KlausurProjekte;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        System.out.printf("Hello JDBC");
        String url = "jdbc:sqlite:C:\\Users\\aurel\\OneDrive - FH JOANNEUM\\Dokumente\\JAVA\\DBPKlausur_ZOUAOUI.db";

        //Aufgabe 2a)
        System.out.println("Aufgabe 2a");
        DBPKlausurHelper helper = new DBPKlausurHelper(url);
        helper.createTableKlausurProjekte();

        //Aufgabe 2b) --> siehe DBPKlausurHelper

        //Aufgabe 2c)
        System.out.println("Aufgabe 2c");
        helper.createTableKlausurProjekte();

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 3
        System.out.println("Aufgabe 3");
        KlausurProjekte k1 = new KlausurProjekte();
        k1.setProjektbezeichnung("Regenzeit");
        k1.setProjekttyp("Saison");
        k1.setBudget(1000);
        k1.setLaufzeit(365);
        helper.insertProjekt(k1);
        System.out.printf("Projekt %s wurde hinzugefügt, die neue id ist", k1.getProjektId());

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 4a
        System.out.println("Aufgabe 4a");
        KlausurProjekte klausurProjekte = helper.getProjektById(1);
        System.out.printf("Projekt gefunden %s\n", klausurProjekte.toString());


        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 4b
        System.out.println("Aufgabe 4b");
        System.out.println("\nAlle Projekte sortiert mit Laufzeit und Projekttyp\n");
        ArrayList<KlausurProjekte> suchergebnis = helper.getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(40);
        System.out.println(suchergebnis);
        System.out.printf("Projekte gefunden %s\n", suchergebnis.toString());

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 5a)
        //Wenn es versucht wird, eine Projektaufgabe mit einer nicht vorhandenen ProjektId zu erstellen:
        // wird ein Verstoß gegen die referentielle Integrität durch das FK verursacht
        System.out.println("Aufgabe 5a");
        System.out.printf("%nInsert neue Aufgaben einem bestehenden Projekt hinzu %n");
        KlausurProjektaufgaben neueAufgabe = new KlausurProjektaufgaben(1, "Schreiben", 4);
        //neueAufgabe.setProjektId(88);
        //neueAufgabe.setAufgabenbezeichnung("Lesen");
        //neueAufgabe.setAufwandInStunden(5);
        //neueAufgabe.setProjektaufgabeID(90);
        // neueAufgabe.setAufgabenbezeichnung("neue Aufgabe");
        helper.insertProjektaufgabe(neueAufgabe);
        System.out.printf("Projektaufgabe %s wurde hinzugefügt, die neue id ist", neueAufgabe.getProjektaufgabeID());
        //System.out.println(neueAufgabe.getProjektaufgabeID());
        //KlausurProjektaufgaben updateAufgabe = helper.insertProjektaufgabe(neueAufgabe);
        //updateAufgabe.getProjektId();
        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 6a
        System.out.println("Aufgabe 6a");
        KlausurProjekte aufwendigstenProjekte = helper.getProjektMitDenMeistenAufgaben();
        System.out.println("Das aufwendigste Projekt ist : " + aufwendigstenProjekte);

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 6c
        System.out.println("Aufgabe 6c");
        System.out.println("AufwandInStunden AVG " + helper.getDurchschnittAufwandInStundenByProjektId(2));

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 7
        System.out.println("Aufgabe 7");
        //Verschiedene Informationen über Datenbanken und Tabellen abrufen
        System.out.println("Die Datenbank “KlausurZouaouiDB” beinhaltet die folgenden Tabellen: ");
        helper.printTableNames();

        System.out.println("-----------------------");
        System.out.println(" ");

        //Aufgabe 8)
        helper.createAdditionalTableAndFillWithSampleValues();

        //Aufgabe 9)
        helper.transferBudget(1, 0, 500);

    }
}