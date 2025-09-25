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
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class StavkaRacuna implements Serializable, DomainObject<StavkaRacuna> {

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

    public boolean isDifferent(StavkaRacuna other) {
        if (other == null) {
            return true;
        }
        return this.kolicina != other.kolicina || this.cenaStavke != other.cenaStavke;
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
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StavkaRacuna other = (StavkaRacuna) obj;

        if (this.racun == null || other.racun == null || this.usluga == null || other.usluga == null) {
            return false;
        }

        return Objects.equals(this.racun.getId(), other.racun.getId())
                && Objects.equals(this.usluga.getId(), other.usluga.getId());
    }

    @Override
    public String toString() {
        return "StavkaRacuna{" + "rb=" + rb + ", racun=" + racun + ", kolicina=" + kolicina + ", cenaStavke=" + cenaStavke + ", jedinicnaCena=" + jedinicnaCena + ", usluga=" + usluga + '}';
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO stavka_racuna (rb, racunId, kolicina, cenaStavke, jedinicnaCena, uslugaId) VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, rb);
        ps.setInt(2, racun != null ? racun.getId() : 0);
        ps.setInt(3, kolicina);
        ps.setDouble(4, cenaStavke);
        ps.setDouble(5, jedinicnaCena);
        ps.setInt(6, usluga != null ? usluga.getId() : 0);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE stavka_racuna SET kolicina = ?, cenaStavke = ?, jedinicnaCena = ?, uslugaId = ? WHERE rb = ? AND racunId = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, kolicina);
        ps.setDouble(2, cenaStavke);
        ps.setDouble(3, jedinicnaCena);
        ps.setInt(4, usluga != null ? usluga.getId() : 0);
        ps.setInt(5, rb);
        ps.setInt(6, racun != null ? racun.getId() : 0);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM stavka_racuna WHERE rb = ? AND racunId = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, rb);
        ps.setInt(2, racun != null ? racun.getId() : 0);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT s.rb, s.racunId, s.kolicina, s.cenaStavke, s.jedinicnaCena, s.uslugaId, "
                + "u.naziv AS uslugaNaziv, u.cena AS uslugaCena "
                + "FROM stavka_racuna s "
                + "LEFT JOIN usluga u ON s.uslugaId = u.id "
                + "WHERE s.racunId = ?";
    }

    @Override
    public StavkaRacuna createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int rb = rs.getInt("rb");
            int kolicina = rs.getInt("kolicina");
            double cenaStavke = rs.getDouble("cenaStavke");
            double jedinicnaCena = rs.getDouble("jedinicnaCena");

            // Racun ali samo ID
            int racunId = rs.getInt("racunId");
            Racun racun = new Racun();
            racun.setId(racunId);

            // Usluga
            int uslugaId = rs.getInt("uslugaId");
            String naziv = rs.getString("uslugaNaziv");
            double cena = rs.getDouble("uslugaCena");
            Usluga usluga = new Usluga(uslugaId, naziv, cena);

            return new StavkaRacuna(rb, racun, kolicina, cenaStavke, jedinicnaCena, usluga);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, racun != null ? racun.getId() : 0);
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT s.rb, s.racunId, s.kolicina, s.cenaStavke, s.jedinicnaCena, s.uslugaId, "
                + "u.naziv AS uslugaNaziv, u.cena AS uslugaCena "
                + "FROM stavka_racuna s "
                + "LEFT JOIN usluga u ON s.uslugaId = u.id "
                + "WHERE s.racunId = ?";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, racun.getId());
    }

    @Override
    public List<StavkaRacuna> createListFromResultSet(ResultSet rs) throws SQLException {
        List<StavkaRacuna> lista = new java.util.ArrayList<>();
        while (rs.next()) {
            int rb = rs.getInt("rb");
            int kolicina = rs.getInt("kolicina");
            double cenaStavke = rs.getDouble("cenaStavke");
            double jedinicnaCena = rs.getDouble("jedinicnaCena");

            // Racun samo ID
            int racunId = rs.getInt("racunId");
            Racun racun = new Racun();
            racun.setId(racunId);

            // Usluga
            int uslugaId = rs.getInt("uslugaId");
            String naziv = rs.getString("uslugaNaziv");
            double cena = rs.getDouble("uslugaCena");
            Usluga usluga = new Usluga(uslugaId, naziv, cena);

            StavkaRacuna stavka = new StavkaRacuna(rb, racun, kolicina, cenaStavke, jedinicnaCena, usluga);
            lista.add(stavka);
        }
        return lista;
    }

}
