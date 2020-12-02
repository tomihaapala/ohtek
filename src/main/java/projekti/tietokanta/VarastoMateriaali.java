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
public class VarastoMateriaali {

    private String sulatetunnus;
    private int korkeus;
    private int pituus;
    private String varastopaikka;

    /**
     * Constructor for handling the items in warehouse
     */
    public VarastoMateriaali(String sulatetunnus, int pituus, int korkeus, String varastopaikka) {
        this.pituus = pituus;
        this.korkeus = korkeus;
        this.sulatetunnus = sulatetunnus;
        this.varastopaikka = varastopaikka;

    }


    /**
     * Methods for getters
     */
    
    
    public Integer getPituus() {
        return this.pituus;
    }

    public String getSulate() {
        return this.sulatetunnus;

    }

    public String getVarastopaikka() {
        return this.varastopaikka;
    }

    public Integer getKorkeus() {
        return this.korkeus;
    }
    public String toString() {
        return "Sulate :"+this.sulatetunnus+ " Pituus: " + this.pituus + " Korkeus: " + this.korkeus + " Varasto: "+this.varastopaikka;
    }
}
