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
public class Musterija implements Serializable{
    private int id;
    private String email;
    private String username;
    private String password;
    private KategorijaMusterije kategorijaMusterije;

    public Musterija() {
    }

    public Musterija(int id, String email, String username, String password, KategorijaMusterije kategorijaMusterije) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.kategorijaMusterije = kategorijaMusterije;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KategorijaMusterije getKategorijaMusterije() {
        return kategorijaMusterije;
    }

    public void setKategorijaMusterije(KategorijaMusterije kategorijaMusterije) {
        this.kategorijaMusterije = kategorijaMusterije;
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
        final Musterija other = (Musterija) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return "Musterija{" + "id=" + id + ", email=" + email + ", username=" + username + '}';
    }
    
    
}
