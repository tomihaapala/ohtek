/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import projekti.tietokanta.VarastoMateriaali;
import projekti.kayttoliittyma.Tekstikayttoliittyma;
import projekti.tietokanta.Tietokantayhteydet;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import projekti.tietokanta.TilausMateriaali;

/**
 *
 * @author Tomi
 */
public class ProjektiTestit {

    Tietokantayhteydet tietokantayhteydet;
    Tekstikayttoliittyma t;

    @BeforeEach
    public void setUp() throws SQLException {

    }

    //Testataan materiaali-olion luomista
    @Test
    public void varastoMateriaaliOlioTest() {

        VarastoMateriaali materiaali = new VarastoMateriaali("5", 5, 5, "H8");

        String testSulate = materiaali.getSulate();
        int testPituus = materiaali.getPituus();

        assertEquals("5", testSulate);
        assertEquals(5, testPituus);
        assertEquals("H8", materiaali.getVarastopaikka());
    }

    @Test
    public void tilausMateriaaliOlioTest() {

        TilausMateriaali materiaali = new TilausMateriaali(5, 5);

        int testPituus = materiaali.getPituus();
        int testiKorkeus = materiaali.getKorkeus();

        assertEquals(5, testPituus);
        assertEquals(5, testiKorkeus);

    }

    // Testataan optimointi-toiminnallisuuden toimivuus
    @Test
    public void optimointiTest() throws SQLException {
        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {

            db.setAutoCommit(false);
       
            Tietokantayhteydet ty = new Tietokantayhteydet();

            ty.luo();
        try{
            ty.lisaa("TEST1", 10000, 100, "H10");
            ty.lisaa("TEST2", 6000, 100, "H10");
            ty.lisaa("TEST3", 9000, 100, "H10");
            ty.lisaa("TEST4", 7000, 100, "H10");
            ty.lisaa("TEST5", 8000, 100, "H10");
            TilausMateriaali tm = new TilausMateriaali(1000, 100);
            ty.optimoi(tm);
            ArrayList<VarastoMateriaali> tulokset = ty.getTulokset();
            int tulos = 0;
            for(VarastoMateriaali vm : tulokset){
                tulos = vm.getPituus();
            }
            ty.nollaaTulokset();
            ty.poistaMateriaali("TEST1");
            ty.poistaMateriaali("TEST2");
            ty.poistaMateriaali("TEST3");
            ty.poistaMateriaali("TEST4");
            ty.poistaMateriaali("TEST5");
            ty.poistaMateriaali("TEST2-J");
           
            assertEquals(6000, tulos);
            assertNotEquals(7000, tulos);
            
              } finally {

            db.rollback();
            db.close();

         }

        }
    }

    //Testataan tietokannan toimivuus
    @Test
    public void tietokantaTest() throws SQLException {

        try ( Connection db = DriverManager.getConnection("jdbc:sqlite:tietokanta.db")) {
            try ( Statement stCheck = db.createStatement()) {
                db.setAutoCommit(false);

                // Setting input parameters: 
                tietokantayhteydet = new Tietokantayhteydet();
                String sulate = "5";
                int pituus = 5;
                int korkeus = 5;
                String varastopaikka = "H10";

                // Initial cleanup:
                stCheck.executeUpdate("DELETE * FROM Tuotteet");

                // Do the call
                this.tietokantayhteydet.luo();
                this.tietokantayhteydet.lisaa(sulate, pituus, korkeus, varastopaikka);

                // Database Checks:
                int tuoteId;
                // Check the Tuotteet table contains one row with the expected values:
                try ( ResultSet rs = stCheck.executeQuery("SELECT * FROM tuotteet")) {
                    assertTrue(rs.next());
                    tuoteId = rs.getInt("id");
                    assertEquals(sulate, rs.getString("sulatetunnus"));
                    assertEquals(pituus, rs.getInt("pituus"));
                    assertEquals(korkeus, rs.getInt("korkeus"));

                }

                // Check the Tuotteet table contains one row with the expected values:
                try ( ResultSet rs = stCheck.executeQuery("SELECT * FROM Tuotteet WHERE id=" + tuoteId)) {
                    assertTrue(rs.next());

                }
            } finally {
                // Undo the testing operations:
                db.rollback();
            }
        } catch (SQLException e) {

        }

    }

}
