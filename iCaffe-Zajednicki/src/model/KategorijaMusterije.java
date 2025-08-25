/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Omnix
 */
public class KategorijaMusterije implements Serializable{
    private int id;
    private String naziv;
    private int popust;

    public KategorijaMusterije() {
    }

    public KategorijaMusterije(int id, String naziv, int popust) {
        this.id = id;
        this.naziv = naziv;
        this.popust = popust;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
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
        final KategorijaMusterije other = (KategorijaMusterije) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return "KategorijaMusterije{" + "naziv=" + naziv + ", popust=" + popust + '}';
    }
    
    
}
