/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class StavkaRacuna implements Serializable {
    private int rb;
    private Racun racun;
    private int kolicina;
    private double cenaStavke;
    private double jedinicnaCena;
    private Usluga usluga;

    public StavkaRacuna() {
    }

    public StavkaRacuna(int rb, Racun racun, int kolicina, double cenaStavke, double jedinicnaCena, Usluga usluga) {
        this.rb = rb;
        this.racun = racun;
        this.kolicina = kolicina;
        this.cenaStavke = cenaStavke;
        this.jedinicnaCena = jedinicnaCena;
        this.usluga = usluga;
    }

    public int getRb() {
        return rb;
    }

    public void setRb(int rb) {
        this.rb = rb;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCenaStavke() {
        return cenaStavke;
    }

    public void setCenaStavke(double cenaStavke) {
        this.cenaStavke = cenaStavke;
    }

    public double getJedinicnaCena() {
        return jedinicnaCena;
    }

    public void setJedinicnaCena(double jedinicnaCena) {
        this.jedinicnaCena = jedinicnaCena;
    }

    public Usluga getUsluga() {
        return usluga;
    }

    public void setUsluga(Usluga usluga) {
        this.usluga = usluga;
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
        final StavkaRacuna other = (StavkaRacuna) obj;
        if (this.rb != other.rb) {
            return false;
        }
        return Objects.equals(this.racun, other.racun);
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "rb=" + rb + ", racun=" + racun + ", kolicina=" + kolicina + ", cenaStavke=" + cenaStavke + ", jedinicnaCena=" + jedinicnaCena + ", usluga=" + usluga + '}';
    }
    
    
    
}
