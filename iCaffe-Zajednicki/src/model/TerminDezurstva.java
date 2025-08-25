/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 *
 * @author Omnix
 */
public class TerminDezurstva implements Serializable{
    private int id;
    private String smena;
    private LocalTime vremeOd;
    private LocalTime vremeDo;

    public TerminDezurstva() {
    }

    public TerminDezurstva(int id, String smena, LocalTime vremeOd, LocalTime vremeDo) {
        this.id = id;
        this.smena = smena;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSmena() {
        return smena;
    }

    public void setSmena(String smena) {
        this.smena = smena;
    }

    public LocalTime getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(LocalTime vremeOd) {
        this.vremeOd = vremeOd;
    }

    public LocalTime getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(LocalTime vremeDo) {
        this.vremeDo = vremeDo;
    }

    @Override
    public String toString() {
        return "TerminDezurstva{" + "smena=" + smena + ", vremeOd=" + vremeOd + ", vremeDo=" + vremeDo + '}';
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
        final TerminDezurstva other = (TerminDezurstva) obj;
        return this.id == other.id;
    }
    
    
}
