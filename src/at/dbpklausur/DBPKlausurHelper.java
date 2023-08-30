package at.dbpklausur;


import at.dbpklausur.models.KlausurProjektaufgaben;
import at.dbpklausur.models.KlausurProjekte;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBPKlausurHelper {
    private Connection conn;

    public DBPKlausurHelper(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    private String url = "jdbc:sqlite:C:\\Users\\aurel\\OneDrive - FH JOANNEUM\\Dokumente\\JAVA\\DBPKlausur_ZOUAOUI.db";


    //Aufgabe 2a
    public void createTableKlausurProjekte() {


        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateProjekte = "CREATE TABLE KlausurProjekte\n" +
                    "(\n" +
                    "    ProjektID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    Projektbezeichnung varchar(50), " +
                    "    Projekttyp varchar(50),\n" +
                    "    Budget double(10,2),\n" +
                    "    Laufzeit int\n" +
                    ")";

            Statement ddlCreateProjekteStmt = conn.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table KlausurProjekte succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Aufgabe 2b) (3 Punkte)
    // The LAST_INSERT_ID() function returns the AUTO_INCREMENT id
    // of the last row that has been inserted or updated in a table.
    public void createTableKlausurProjektaufgaben() {

        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateProjekte = "CREATE TABLE KlausurProjektaufgaben\n" +
                    "(\n" +
                    "    ProjektaufgabeID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    ProjektID INT,\n " +
                    "    Aufgabenbezeichnung VARCHAR (20),\n" +
                    "    AufwandInStunden    INT,\n" +
                    "    Laufzeit int\n" +
                    "    CONSTRAINT fk_projekt FOREIGN KEY (\n" +
                    "    ProjektId\n" +
                    "    )\n" +
                    "    REFERENCES KlausurProjekte (ProjektID)\n" +
                    ")";


            Statement ddlCreateProjekteStmt = conn.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table KlausurProjektaufgaben succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    //Aufgabe 2d
    //1. Foreign-Key-Constraints in der Tabellendefinition festlegen
    //2. Einschalten des Foreign-Key-Pragmas: Sie müssen das Pragma foreign_keys auf ON setzen,
    // um SQLite anzuweisen, die Foreign-Key-Constraints zu überwachen.
    //3.Transaktionsverarbeitung: connection.setAutoCommit( false );
    // es gibt kein AutoCommit mehr, alle Transatktionen müssen mit commit abgeschlossen werden

    // Aufgabe 3)
    public KlausurProjekte insertProjekt(KlausurProjekte neuesProjekt) {

        String insertProjekt = "INSERT INTO KlausurProjekte (\n" +
                "  Projektbezeichnung,\n" +
                "  Projekttyp,\n" +
                "  Budget,\n" +
                "  Laufzeit\n" +
                "   )\n" +
                "  VALUES (\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?,\n" +
                "  ?\n" +
                "  );";
        try {
            PreparedStatement pStmt = conn.prepareStatement(insertProjekt);
            pStmt.setString(1, neuesProjekt.getProjektbezeichnung());
            pStmt.setString(2, neuesProjekt.getProjekttyp());
            pStmt.setDouble(3, neuesProjekt.getBudget());
            pStmt.setDouble(4, neuesProjekt.getLaufzeit());
            pStmt.executeUpdate();

            int id = getLastInsertId();
            neuesProjekt.setProjektId(id);


        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }

        if (neuesProjekt != null) {
            System.out.println("automatisch vergebenen Autowert : " + neuesProjekt.getProjektId());

        }
        return neuesProjekt;
    }

    private int getLastInsertId() throws SQLException {

        Connection conn = DriverManager.getConnection(url);
        String readLastId = "SELECT last_insert_rowid() as rowid ";

        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(readLastId);
        rs.next();
        int id = rs.getInt("rowid");

        return id;

    }


    //Aufgabe 4a)
    public KlausurProjekte getProjektById(int projektId) {

        String selectProjekById = "SELECT ProjektID, Projektbezeichnung, Projekttyp, Budget, Laufzeit \n" +
                "FROM KlausurProjekte\n" +
                "WHERE ProjektID=?";

        KlausurProjekte kp = new KlausurProjekte();
        kp.setProjektId(projektId);

        try {
            PreparedStatement pStmt = conn.prepareStatement(selectProjekById);
            pStmt.setDouble(1, projektId);
            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                kp.setProjektbezeichnung(rs.getString("Projektbezeichnung"));
                kp.setProjekttyp(rs.getString("Projekttyp"));
                kp.setBudget(rs.getDouble("Budget"));
                kp.setLaufzeit(rs.getInt("Laufzeit"));
            } else {
                kp.setProjektbezeichnung("Projekt nicht vorhanden");
            }
            if (kp.getProjekttyp() == null) {
                System.out.println("Projekttyp is null!");
            }
        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }
        return kp;
    }


    //Aufgabe 4b
    public List<KlausurProjekte> getAlleProjekteSortedByProjekttypFilteredByMaxLaufzeit(int laufzeit) {

        ArrayList<KlausurProjekte> alleProjekte = new ArrayList<KlausurProjekte>();
        try {
            PreparedStatement pSuche = conn.prepareStatement("SELECT * FROM KlausurProjekte ORDER BY Projekttyp ASC \n" +
                    "WHERE laufzeit<= " + laufzeit);

            pSuche.setDouble(1, laufzeit);
            ResultSet rs = pSuche.executeQuery();
            while (rs.next()) {
                KlausurProjekte k = new KlausurProjekte();
                k.setProjektId(rs.getInt("ProjektID"));
                k.setLaufzeit(rs.getInt("laufzeit"));
                alleProjekte.add(k);
                alleProjekte.add(getProjektById(rs.getInt("ProjektID")));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getStackTrace());
        }
        return alleProjekte;
    }


    //Aufgabe 5a
    // public KlausurProjektaufgaben insertProjektaufgabe(KlausurProjektaufgaben aufgabe){

    // }

    //Aufgabe 6a
    public KlausurProjekte getProjektMitDenMeistenAufgaben() {
        String selectProjekte = "SELECT ProjektID\n" +
                " FROM KlausurProjektaufgaben ORDER BY ProjektID DESC";

        KlausurProjekte projekteMitDenMeistenAufgaben = new KlausurProjekte();

        try {
            ResultSet rs = conn.createStatement().executeQuery(selectProjekte);
            rs.next();
            projekteMitDenMeistenAufgaben = getProjektById(rs.getInt("ProjektID"));
        } catch (SQLException ex) {
            System.out.printf("%s", ex.getStackTrace());
        }

        return projekteMitDenMeistenAufgaben;
    }

    //Aufgabe 6c
    public double getDurchschnittAufwandInStundenByProjektId(int projektId) {
        double avgAufwandInStunden = 0;

        try {
            String query = "SELECT AVG(AufwandInStunden) AS durchschnitt FROM KlausurProjektaufgaben\n" +
                    "WHERE ProjektID=?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setDouble(1, projektId);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                avgAufwandInStunden = rs.getDouble("durchschnitt");
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return avgAufwandInStunden;
    }


    public void printTableNames() {
        try {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                String tableName = tables.getString("TABLE_NAME");
                System.out.println(tableName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //Aufgabe 8
    public void createAdditionalTableAndFillWithSampleValues() {
        try (Connection conn = DriverManager.getConnection(url)) {

            String ddlCreateProjekte =  "CREATE TABLE Aufgaben (\n" +
                    "AufgabeID INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "ProjektaufgabeId INT,\n" +
                    "Punkte INT,\n" +
                    "Kommentar VARCHAR (20),\n" +
                    "CONSTRAINT fk_projektaufgaben FOREIGN KEY (\n" +
                    "ProjektaufgabeId\n" +
                    ")\n" +
                    "REFERENCES KlausurProjektaufgaben (ProjektaufgabeID) \n" +
                    ");";

            Statement ddlCreateProjekteStmt = conn.createStatement();
            ddlCreateProjekteStmt.execute(ddlCreateProjekte);
            System.out.println("Table Aufgaben succesfully created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void transferBudget(int oldProjektId, int newProjektId, double budget){

        String update1 = "UPDATE KlausurProjekte set Budget = Budget - ? WHERE Projektid = ?";
        String update2 = "UPDATE KlausurProjekte set Budget = Budget + ? WHERE Projektid = ?";

        try{
            conn.setAutoCommit(false);
            PreparedStatement pStmt1 = conn.prepareStatement(update1);

            pStmt1.setInt(1,oldProjektId);
            pStmt1.setInt(2,newProjektId);
            pStmt1.setDouble(3, budget);

            PreparedStatement pStmt2 = conn.prepareStatement(update2);
            pStmt2.setDouble(3, budget);
            pStmt2.setInt(2, newProjektId);

            pStmt2.executeUpdate();
            conn.commit();

        }catch (SQLException ex){
            try {
                conn.rollback();
            }
            catch (SQLException transactionFehler){
                System.out.printf("%s", ex.getStackTrace());
            }

        }

    }
}





