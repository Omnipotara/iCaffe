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
import java.time.Duration;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Omnix
 */
public class Musterija implements Serializable, DomainObject<Musterija> {

    private int id;
    private String email;
    private String username;
    private String password;
    private KategorijaMusterije kategorijaMusterije;
    private Duration preostaloVreme;

    public Musterija() {
    }

    public Musterija(int id, String email, String username, String password, KategorijaMusterije kategorijaMusterije, Duration preostaloVreme) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.kategorijaMusterije = kategorijaMusterije;
        this.preostaloVreme = preostaloVreme;
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

    public Duration getPreostaloVreme() {
        return preostaloVreme;
    }

    public void setPreostaloVreme(Duration preostaloVreme) {
        this.preostaloVreme = preostaloVreme;
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

    @Override
    public String getInsertQuery() {
        return "INSERT INTO musterija (email, username, password, kategorijaId, preostaloVreme) VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, email);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setInt(4, kategorijaMusterije != null ? kategorijaMusterije.getId() : 0);
        ps.setLong(5, preostaloVreme != null ? preostaloVreme.toSeconds() : 0);
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE musterija SET email = ?, username = ?, password = ?, kategorijaId = ?, preostaloVreme = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, email);
        ps.setString(2, username);
        ps.setString(3, password);
        ps.setInt(4, kategorijaMusterije != null ? kategorijaMusterije.getId() : 0);
        ps.setLong(5, preostaloVreme != null ? preostaloVreme.toSeconds() : 0);
        ps.setInt(6, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM musterija WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT m.id, m.email, m.username, m.password, m.kategorijaId, m.preostaloVreme, "
                + "k.naziv AS kategorijaNaziv, k.popust AS kategorijaPopust "
                + "FROM musterija m "
                + "LEFT JOIN kategorija_musterije k ON m.kategorijaId = k.id "
                + "WHERE m.id = ?";
    }

    @Override
    public Musterija createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {

            //Musterija
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");

            // Kategorija
            int kategorijaId = rs.getInt("kategorijaId");
            String naziv = rs.getString("kategorijaNaziv");
            int popust = rs.getInt("kategorijaPopust");
            KategorijaMusterije kategorija = new KategorijaMusterije(kategorijaId, naziv, popust);

            // preostalo vreme
            long vreme = rs.getLong("preostaloVreme");
            Duration preostalo = Duration.ofSeconds(vreme);

            return new Musterija(id, email, username, password, kategorija, preostalo);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT m.id, m.email, m.username, m.password, m.kategorijaId, m.preostaloVreme, "
                + "k.naziv AS kategorijaNaziv, k.popust AS kategorijaPopust "
                + "FROM musterija m "
                + "LEFT JOIN kategorija_musterije k ON m.kategorijaId = k.id";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        // Nema parametara (za sad)
    }

    @Override
    public List<Musterija> createListFromResultSet(ResultSet rs) throws SQLException {
        List<Musterija> list = new java.util.ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");

            // Kategorija
            int kategorijaId = rs.getInt("kategorijaId");
            String naziv = rs.getString("kategorijaNaziv");
            int popust = rs.getInt("kategorijaPopust");
            KategorijaMusterije kategorija = new KategorijaMusterije(kategorijaId, naziv, popust);

            // Preostalo vreme
            long vreme = rs.getLong("preostaloVreme");
            Duration preostalo = Duration.ofSeconds(vreme);

            Musterija m = new Musterija(id, email, username, password, kategorija, preostalo);
            list.add(m);
        }
        return list;
    }

}
