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
import java.util.List;

/**
 *
 * @author Omnix
 */
public class Usluga implements Serializable, DomainObject<Usluga> {

    private int id;
    private String naziv;
    private double cena;

    public Usluga() {
    }

    public Usluga(int id, String naziv, double cena) {
        this.id = id;
        this.naziv = naziv;
        this.cena = cena;
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

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    @Override
    public int hashCode() {
        int hash = 3;
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
        final Usluga other = (Usluga) obj;
        return this.id == other.id;
    }

    @Override
    public String toString() {
        return naziv + " - " + cena;
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO usluga (naziv, cena) VALUES (?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, naziv);
        ps.setDouble(2, cena);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE usluga SET naziv = ?, cena = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, naziv);
        ps.setDouble(2, cena);
        ps.setInt(3, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM usluga WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, naziv, cena FROM usluga WHERE id = ?";
    }

    @Override
    public Usluga createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            double cena = rs.getDouble("cena");
            return new Usluga(id, naziv, cena);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT id, naziv, cena FROM usluga";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        // Nema parametara (za sad)
    }

    @Override
    public List<Usluga> createListFromResultSet(ResultSet rs) throws SQLException {
        List<Usluga> lista = new java.util.ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            double cena = rs.getDouble("cena");
            lista.add(new Usluga(id, naziv, cena));
        }
        return lista;
    }

}
