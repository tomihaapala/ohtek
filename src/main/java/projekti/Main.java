/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti;

import projekti.kayttoliittyma.GraafinenKayttoliittyma;
import projekti.kayttoliittyma.Tekstikayttoliittyma;

import java.sql.SQLException;
import javafx.application.Application;

/**
 *
 * @author Tomi
 */
public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        // Tekstikayttoliittyma t = new Tekstikayttoliittyma();
        // t.kaynnista();
        
        Application.launch(GraafinenKayttoliittyma.class);

    }
}
