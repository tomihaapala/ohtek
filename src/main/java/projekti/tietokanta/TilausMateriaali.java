/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekti.tietokanta;

/**
 * Class defining material information.
 *
 * @author Tomi Haapala
 */
public class TilausMateriaali {

    private int korkeus;
    private int pituus;


    /**
     * Constructor for order item
     */
    public TilausMateriaali(int pituus, int korkeus) {
        this.pituus = pituus;
        this.korkeus = korkeus;

    }

    public Integer getPituus() {
        return this.pituus;
    }

    public Integer getKorkeus() {
        return this.korkeus;
    }


    public String toString() {
        return "Pituus: " + this.pituus + " Korkeus: " + this.korkeus;
    }

}
