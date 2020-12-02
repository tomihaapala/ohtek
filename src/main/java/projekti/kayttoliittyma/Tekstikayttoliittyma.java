/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.kayttoliittyma;

import projekti.tietokanta.Tietokantayhteydet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import projekti.tietokanta.TilausMateriaali;
import projekti.tietokanta.VarastoMateriaali;

/**
 *
 * @author Tomi
 */
public class Tekstikayttoliittyma {

    //Sovelluksen varsinainen käynnistysmetodi.
 
    public String kaynnista() throws SQLException, ClassNotFoundException {
        Tietokantayhteydet t = new Tietokantayhteydet();
        t.luo();
        Scanner input = new Scanner(System.in);
        System.out.println("Tekstikäyttoliittymä käynnistetty");

        //Käyttöliittymä jakautuu Tavaran vastaanottajan- ja suunnittelijan käyttöliittymiin
        while (true) {
            System.out.println("Tavaran vastaanottaja: 1");
            System.out.println("Suunnittelija: 2");
            int valintaYleis = Integer.parseInt(input.nextLine());
            if (valintaYleis == 1) {

                //Tavaran vastaanottaja voi lisätä materiaalin, poistaa materiaalin tai tarkastella varaston sisältöä(käytetyt, käyttämättömät, kaikki)
                System.out.println("Tavaran vastaanottajan käyttöliittymä");
                System.out.println("Haluatko lisätä tuotteen? Paina 1");
                System.out.println("Tulosta kaikki? Paina 2");
                System.out.println("Poista materiaali? Paina 3");
                int valintaVastaanotto = Integer.parseInt(input.nextLine());

                if (valintaVastaanotto == 1) {
                    System.out.println("Anna tuotteen sulatetunnus:");
                    String sulatetunnus = input.nextLine();
                    System.out.println("Anna tuotteen pituus:");
                    int pituus = Integer.parseInt(input.nextLine());
                    System.out.println("Anna tuotteen korkeus:");
                    int korkeus = Integer.parseInt(input.nextLine());
                    System.out.println("Anna tuotteen varastopaikka:");
                    String varastoPaikka = input.nextLine();
                    t.lisaa(sulatetunnus, pituus, korkeus, varastoPaikka);

                }
                if (valintaVastaanotto == 2) {
                    t.tulostaKaikki();
                }

                if (valintaVastaanotto == 3) {
                    System.out.println("Anna poistettavan materiaalin sulatetunnus: ");
                    String sulatetunnus = input.nextLine();
                    t.poistaMateriaali(sulatetunnus);
                }
            }

            if (valintaYleis == 2) {

                // Suunnittelija voi tarkastella varaston sisältöä tai tehdä tilauksen
                System.out.println("Suunnittelijan käyttöliittymä \n");
                System.out.println("Haluatko tarkastella varastoa? Paina 1");
                System.out.println("Haluatko tehdä tilauksen? Paina 2");
                ArrayList<TilausMateriaali> materiaalilista = new ArrayList<>();
                int valintaSuunnittelija = Integer.parseInt(input.nextLine());
                if (valintaSuunnittelija == 1) {
                    t.tulostaKaikki();
                }

                if (valintaSuunnittelija == 2) {

                    while (true) {

                        // Suunnittelijan tilauksentekovalikko. Hän kerää listan tarvitsemistaan materiaaleista ja lähettää sen 
                        // optimointilogiikalle(toteutetaan kolmannessa iteraatiossa)
                        System.out.println("Lisää uusi materiaali tilaukseen? Paina 1");
                        System.out.println("Lähetä tilaus? Paina 2");
                        System.out.println("Näytä tilaus? Paina 3");
                        System.out.println("Lopeta? Paina 4");
                        int valintaSuunnittelija2 = Integer.parseInt(input.nextLine());

                        if (valintaSuunnittelija2 == 1) {

                            System.out.println("Anna tuotteen pituus:");
                            int pituus = Integer.parseInt(input.nextLine());
                            System.out.println("Anna tuotteen korkeus:");
                            int korkeus = Integer.parseInt(input.nextLine());

                            materiaalilista.add(new TilausMateriaali(pituus, korkeus));

                        }
                        if (valintaSuunnittelija2 == 2) {

                            for (TilausMateriaali tm : materiaalilista) {
                                t.optimoi(tm);
                            }

                            ArrayList<VarastoMateriaali> tulokset = t.getTulokset();

                            for (VarastoMateriaali vm : tulokset) {

                            }
                            t.nollaaTulokset();
                            materiaalilista.clear();

                        }
                        if (valintaSuunnittelija2 == 3) {
                            for (TilausMateriaali tm : materiaalilista) {
                                System.out.println(tm);
                            }
                        }

                        if (valintaSuunnittelija2 == 4) {
                            materiaalilista.clear();
                            break;

                        }
                    }
                }
            }
        }
    }
}
