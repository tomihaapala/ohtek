/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.tietokanta;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Tomi
 */
public class Tietokantayhteydet {

    ArrayList<VarastoMateriaali> tulokset = new ArrayList<>();

    //Metodi tietokannan luomiseksi ellei tietokantaa ole jo luotu
    public void luo() throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
        }

        Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db");
        Statement s = db.createStatement();
        s.execute("CREATE TABLE IF NOT EXISTS Tuotteet (id INTEGER PRIMARY KEY, sulatetunnus TEXT, pituus INTEGER, korkeus INTEGER, varastopaikka TEXT, kayttamaton TEXT)");
        //lisaa(0, 0, 0, "0");
    }

    /**
     * Tietokanta-metodi yksittäisen materiaalin lisäämikseksi tietokantaan
     *
     * @param sulatetunnus Materiaalin yksilöivä tunniste
     * @param pituus Materiaalin pituus
     * @param korkeus Materiaalin korkeus
     * @param varastopaikka Materiaalille kirjausvaiheessa annettava
     * varastopaikka
     *
     *
     */
    public void lisaa(String sulatetunnus, int pituus, int korkeus, String varastopaikka) throws SQLException {
        PreparedStatement p;
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            p = db.prepareStatement("INSERT INTO Tuotteet(sulatetunnus, pituus, korkeus, varastopaikka, kayttamaton) VALUES (?,?,?,?,?)");
            p.setString(1, sulatetunnus);
            p.setInt(2, pituus);
            p.setInt(3, korkeus);
            p.setString(4, varastopaikka);
            p.setString(5, "Käyttämätön");

            //LISÄYS-, MUOKKAUS-, ja KÄYTTÖPÄIVÄMÄÄRÄT TULEE SEURAAVASSA ITERAATIOSSA
            
            p.executeUpdate();
            System.out.println("Tuote lisätty");
        }
        p.close();

    }

    // Metodi tietokannan sisällön tulostamiseen
    public void tulostaKaikki() throws SQLException {
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            Statement s = db.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM Tuotteet");
            while (r.next()) {
                System.out.println(r.getString("sulatetunnus") + " " + r.getInt("pituus") + " " + r.getInt("korkeus") + " " + r.getString("varastopaikka")
                        + " " + r.getString("kayttamaton"));

            }
            r.close();
            s.close();
            db.close();
        }
    }
    // Metodi koko tietokantataulun poistamiseksi 
    public void poistaMateriaalit() throws SQLException {
        PreparedStatement p;
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            p = db.prepareStatement("DROP TABLE Tuotteet");

            p.executeUpdate();
            System.out.println("Tuoteet poistettu");
        }
        p.close();

    }
    // Metodi yksittäisen materiaalin poistamiseksi
    public void poistaMateriaali(String sulatetunnus) throws SQLException {
        PreparedStatement p;
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            p = db.prepareStatement("DELETE FROM Tuotteet WHERE sulatetunnus =?");
            p.setString(1, sulatetunnus);
            p.executeUpdate();
            System.out.println("Tuote poistettu");
        }
        p.close();

    }

    // Metodi yksittäisen materiaalin asettamiseksi 'Käyttämätön'-statukselta 'Käytetty'-statukselle   
    public void asetaKaytetyksi(String sulatetunnus) throws SQLException {
        PreparedStatement p;
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            p = db.prepareStatement("UPDATE Tuotteet SET kayttamaton =? WHERE sulatetunnus =?");
            p.setString(1, "Käytetty");
            p.setString(2, sulatetunnus);
            p.executeUpdate();
            db.close();
            System.out.println("Tuote " + sulatetunnus + " asetettu käytetyksi ");
        }
        p.close();

    }
    
    // Metodi lähettää optimoinnin tulokset käyttöliittymälle
    public ArrayList<VarastoMateriaali> getTulokset() {
        return this.tulokset;
    }

    public void nollaaTulokset() {
        this.tulokset.clear();
    }
    // Metodi optimoi yksittäisen tilausmateriaalin sopivimmalle varastomateriaalille. Jos jäännöspalan pituus ylittää 1000(mm), palautetaan se
    // varastoon "-J"-loppuliitteellä
    public void optimoi(TilausMateriaali tm) throws SQLException {
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {

            String sulatetunnus = "";
            int pituus = 0;
            int korkeus = 0;
            int jaannos = 1000000;
            String varasto = "";
            Statement s = db.createStatement();

            try ( ResultSet r = s.executeQuery("SELECT * FROM Tuotteet WHERE korkeus =" + tm.getKorkeus() + " AND pituus >="
                    + tm.getPituus() + " AND kayttamaton LIKE '%Käyttämätön%'")) {

                if (!r.next()) {
                    System.out.println("Varastosta ei löytynyt sopivaa materiaalia\n");

                }

                while (r.next()) {
                    System.out.println("Resultsetissä on: " + r.getString("sulatetunnus"));

                    if (r.getInt("pituus") - tm.getPituus() < jaannos) {
                        jaannos = r.getInt("pituus") - tm.getPituus();
                        pituus = r.getInt("pituus");
                        korkeus = r.getInt("korkeus");
                        sulatetunnus = r.getString("sulatetunnus");
                        varasto = r.getString("varastopaikka");
                    }
                }

                if (pituus != 0) {
                    this.tulokset.add(new VarastoMateriaali(sulatetunnus, pituus, korkeus, varasto));
                    asetaKaytetyksi(sulatetunnus);
                }

                if (jaannos >= 1000 && pituus != 0) {
                    lisaa(sulatetunnus + "-J", pituus - tm.getPituus(), korkeus, varasto);
                }

            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                s.close();
                db.close();
            }
        }
    }

    // Ohjelmassa on bugi, joka ei anna ensimmäistä tietokantariviä suunnittelijan käyttöön. Tämä metodi tulee poistumaan myöhemmin.    
    public void asetaEkaRiviKaytetyksi() throws SQLException {
        PreparedStatement p;
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            p = db.prepareStatement("UPDATE Tuotteet SET kayttamaton = ? WHERE id =1 OR id =2");
            p.setInt(1, 0);
            p.executeUpdate();
            db.close();

        }
        p.close();

    }
}
