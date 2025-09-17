/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import domen.DomainObject;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class Prodavac implements Serializable, DomainObject<Prodavac> {

    private int id;
    private String ime;
    private String prezime;
    private String email;
    private String username;
    private String password;

    public Prodavac() {
    }

    public Prodavac(int id, String ime, String prezime, String email, String username, String password) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
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
        final Prodavac other = (Prodavac) obj;
        if (this.id != other.id) {
            return false;
        }
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return ime + " " + prezime;
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO prodavac (ime, prezime, email, username, password) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, ime);
        ps.setString(2, prezime);
        ps.setString(3, email);
        ps.setString(4, username);
        ps.setString(5, password);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE prodavac SET ime = ?, prezime = ?, email = ?, username = ?, password = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, ime);
        ps.setString(2, prezime);
        ps.setString(3, email);
        ps.setString(4, username);
        ps.setString(5, password);
        ps.setInt(6, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM prodavac WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        if (id == 0) {
            return "SELECT id, ime, prezime, email, username, password FROM prodavac WHERE username = ? AND password = ?";
        } else {
            return "SELECT id, ime, prezime, email, username, password FROM prodavac WHERE id = ?";
        }

    }

    @Override
    public Prodavac createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");

            return new Prodavac(id, ime, prezime, email, username, password);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        if (id == 0) {
            ps.setString(1, username);
            ps.setString(2, password);
        } else {
            ps.setInt(1, id);
        }
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT id, ime, prezime, email, username, password FROM prodavac";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        // Nema parametara (za sad)
    }

    @Override
    public List<Prodavac> createListFromResultSet(ResultSet rs) throws SQLException {
        List<Prodavac> lista = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");

            lista.add(new Prodavac(id, ime, prezime, email, username, password));
        }
        return lista;
    }

}
