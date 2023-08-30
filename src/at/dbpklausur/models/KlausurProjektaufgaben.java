package at.dbpklausur.models;

public class KlausurProjektaufgaben {

    private int ProjektaufgabeID;
    private int ProjektId;

    private String Aufgabenbezeichnung;
    private int AufwandInStunden;



    public int getProjektaufgabeID() {
        return ProjektaufgabeID;
    }

    public void setProjektaufgabeID(int projektaufgabeID) {
        ProjektaufgabeID = projektaufgabeID;
    }

    public int getProjektId() {
        return ProjektId;
    }

    public void setProjektId(int projektId) {
        ProjektId = projektId;
    }

    public String getAufgabenbezeichnung() {
        return Aufgabenbezeichnung;
    }

    public void setAufgabenbezeichnung(String aufgabenbezeichnung) {
        Aufgabenbezeichnung = aufgabenbezeichnung;
    }

    public int getAufwandInStunden() {
        return AufwandInStunden;
    }

    public void setAufwandInStunden(int aufwandInStunden) {
        AufwandInStunden = aufwandInStunden;
    }
}

