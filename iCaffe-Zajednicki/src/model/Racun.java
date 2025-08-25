/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author Omnix
 */
public class Racun implements Serializable{
    private int id;
    private LocalDateTime datum;
    private double ukupnaCena;
    private Prodavac prodavac;
    private Musterija musterija;

    public Racun() {
    }

    public Racun(int id, LocalDateTime datum, double ukupnaCena, Prodavac prodavac, Musterija musterija) {
        this.id = id;
        this.datum = datum;
        this.ukupnaCena = ukupnaCena;
        this.prodavac = prodavac;
        this.musterija = musterija;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDatum() {
        return datum;
    }

    public void setDatum(LocalDateTime datum) {
        this.datum = datum;
    }

    public double getUkupnaCena() {
        return ukupnaCena;
    }

    public void setUkupnaCena(double ukupnaCena) {
        this.ukupnaCena = ukupnaCena;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public Musterija getMusterija() {
        return musterija;
    }

    public void setMusterija(Musterija musterija) {
        this.musterija = musterija;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Racun other = (Racun) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "Racun{" + "id=" + id + ", datum=" + datum + ", ukupnaCena=" + ukupnaCena + ", prodavac=" + prodavac + ", musterija=" + musterija + '}';
    }
    
    
    
}
