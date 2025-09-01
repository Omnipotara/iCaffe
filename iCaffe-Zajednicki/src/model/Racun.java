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
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author Omnix
 */
public class Racun implements Serializable, DomainObject<Racun> {

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

    @Override
    public String getInsertQuery() {
        return "INSERT INTO racun (datum, ukupnacena, prodavacId, musterijaId) VALUES (?, ?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setTimestamp(1, Timestamp.valueOf(datum));
        ps.setDouble(2, ukupnaCena);
        ps.setInt(3, prodavac.getId());
        ps.setInt(4, musterija.getId());
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE racun SET datum = ?, ukupnacena = ?, prodavacId = ?, musterijaId = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setObject(1, datum);
        ps.setDouble(2, ukupnaCena);
        ps.setInt(3, prodavac.getId());
        ps.setInt(4, musterija.getId());
        ps.setInt(5, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM racun WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT r.id, r.datum, r.ukupnacena, "
                + "p.id AS prodavacId, p.ime AS prodavacIme, p.prezime AS prodavacPrezime, "
                + "p.email AS prodavacEmail, p.username AS prodavacUsername, "
                + "m.id AS musterijaId, m.email AS musterijaEmail, m.username AS musterijaUsername, "
                + "m.password AS musterijaPassword, m.kategorijaId AS kategorijaId, m.preostaloVreme AS preostaloVreme, "
                + "k.naziv AS kategorijaNaziv, k.popust AS kategorijaPopust "
                + "FROM racun r "
                + "JOIN prodavac p ON r.prodavacId = p.id "
                + "JOIN musterija m ON r.musterijaId = m.id "
                + "LEFT JOIN kategorija_musterije k ON m.kategorijaId = k.id "
                + "WHERE r.id = ?";
    }

    @Override
    public Racun createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            // Prodavac
            Prodavac p = new Prodavac(
                    rs.getInt("prodavacId"),
                    rs.getString("prodavacIme"),
                    rs.getString("prodavacPrezime"),
                    rs.getString("prodavacEmail"),
                    rs.getString("prodavacUsername"),
                    null
            );

            // Kategorija musterije
            KategorijaMusterije k = new KategorijaMusterije(
                    rs.getInt("kategorijaId"),
                    rs.getString("kategorijaNaziv"),
                    rs.getInt("kategorijaPopust")
            );

            // Musterija
            long vreme = rs.getLong("preostaloVreme");
            Musterija m = new Musterija(
                    rs.getInt("musterijaId"),
                    rs.getString("musterijaEmail"),
                    rs.getString("musterijaUsername"),
                    rs.getString("musterijaPassword"),
                    k,
                    java.time.Duration.ofSeconds(vreme)
            );

            // Racun
            return new Racun(
                    rs.getInt("id"),
                    rs.getTimestamp("datum").toLocalDateTime(),
                    rs.getDouble("ukupnacena"),
                    p,
                    m
            );
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

}
