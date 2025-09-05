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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class Angazovanje implements Serializable, DomainObject<Angazovanje> {

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
    public String getInsertQuery() {
        return "INSERT INTO angazovanje (prodavacId, terminId, datum) VALUES (?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, prodavac.getId());
        ps.setInt(2, termin.getId());
        ps.setObject(3, datum);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE angazovanje SET datum = ? WHERE prodavacId = ? AND terminId = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setDate(1, java.sql.Date.valueOf(datum));
        ps.setInt(2, prodavac.getId());
        ps.setInt(3, termin.getId());
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM angazovanje WHERE prodavacId = ? AND terminId = ? AND datum = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, prodavac.getId());
        ps.setInt(2, termin.getId());
        ps.setObject(3, datum);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT a.datum, "
                + "p.id AS prodavacId, p.ime, p.prezime, p.email, p.username, "
                + "t.id AS terminId, t.smena, t.vremeOd, t.vremeDo "
                + "FROM angazovanje a "
                + "JOIN prodavac p ON a.prodavacId = p.id "
                + "JOIN termin_dezurstva t ON a.terminId = t.id "
                + "WHERE a.prodavacId = ? AND a.datum = ?";
    }

    @Override
    public Angazovanje createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            // Prodavac
            Prodavac p = new Prodavac();
            p.setId(rs.getInt("prodavacId"));
            p.setIme(rs.getString("ime"));
            p.setPrezime(rs.getString("prezime"));
            p.setEmail(rs.getString("email"));
            p.setUsername(rs.getString("username"));

            // Termin
            TerminDezurstva t = new TerminDezurstva();
            t.setId(rs.getInt("terminId"));
            t.setSmena(Smene.valueOf(rs.getString("smena").toUpperCase()));
            t.setVremeOd(rs.getTime("vremeOd").toLocalTime());
            t.setVremeDo(rs.getTime("vremeDo").toLocalTime());

            // Datum angažovanja
            LocalDate d = rs.getObject("datum", LocalDate.class);

            return new Angazovanje(p, t, d);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, prodavac != null ? prodavac.getId() : 0);
        ps.setObject(2, datum);
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT a.datum, "
                + "p.id AS prodavacId, p.ime, p.prezime, p.email, p.username, "
                + "t.id AS terminId, t.smena, t.vremeOd, t.vremeDo "
                + "FROM angazovanje a "
                + "JOIN prodavac p ON a.prodavacId = p.id "
                + "JOIN termin_dezurstva t ON a.terminId = t.id";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        // Nema parametara (za sad)
    }

    @Override
    public List<Angazovanje> createListFromResultSet(ResultSet rs) throws SQLException {
        List<Angazovanje> lista = new ArrayList<>();
        while (rs.next()) {
            // Prodavac
            Prodavac p = new Prodavac();
            p.setId(rs.getInt("prodavacId"));
            p.setIme(rs.getString("ime"));
            p.setPrezime(rs.getString("prezime"));
            p.setEmail(rs.getString("email"));
            p.setUsername(rs.getString("username"));

            // Termin
            TerminDezurstva t = new TerminDezurstva();
            t.setId(rs.getInt("terminId"));
            t.setSmena(Smene.valueOf(rs.getString("smena").toUpperCase()));
            t.setVremeOd(rs.getTime("vremeOd").toLocalTime());
            t.setVremeDo(rs.getTime("vremeDo").toLocalTime());

            // Datum angažovanja
            LocalDate d = rs.getObject("datum", LocalDate.class);

            lista.add(new Angazovanje(p, t, d));
        }
        return lista;
    }

}
