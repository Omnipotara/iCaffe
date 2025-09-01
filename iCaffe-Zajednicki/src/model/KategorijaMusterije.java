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

/**
 *
 * @author Omnix
 */
public class KategorijaMusterije implements Serializable, DomainObject<KategorijaMusterije> {

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

    @Override
    public String getInsertQuery() {
        return "INSERT INTO kategorija_musterije (naziv, popust) VALUES (?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, naziv);
        ps.setInt(2, popust);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE kategorija_musterije SET naziv = ?, popust = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, naziv);
        ps.setInt(2, popust);
        ps.setInt(3, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM kategorija_musterije WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT * FROM kategorija_musterije WHERE id = ?";
    }

    @Override
    public KategorijaMusterije createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int id = rs.getInt("id");
            String naziv = rs.getString("naziv");
            int popust = rs.getInt("popust");
            return new KategorijaMusterije(id, naziv, popust);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

}
