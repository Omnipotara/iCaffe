/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class Angazovanje implements Serializable {
    private Prodavac prodavac;
    private TerminDezurstva termin;
    private LocalDate datum;

    public Angazovanje() {
    }

    public Angazovanje(Prodavac prodavac, TerminDezurstva termin, LocalDate datum) {
        this.prodavac = prodavac;
        this.termin = termin;
        this.datum = datum;
    }

    public Prodavac getProdavac() {
        return prodavac;
    }

    public void setProdavac(Prodavac prodavac) {
        this.prodavac = prodavac;
    }

    public TerminDezurstva getTermin() {
        return termin;
    }

    public void setTermin(TerminDezurstva termin) {
        this.termin = termin;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    @Override
    public int hashCode() {
        int hash = 5;
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
        final Angazovanje other = (Angazovanje) obj;
        if (!Objects.equals(this.prodavac, other.prodavac)) {
            return false;
        }
        if (!Objects.equals(this.termin, other.termin)) {
            return false;
        }
        return Objects.equals(this.datum, other.datum);
    }

    @Override
    public String toString() {
        return "Angazovanje{" + "prodavac=" + prodavac + ", termin=" + termin + ", datum=" + datum + '}';
    }
    
    
    
}
