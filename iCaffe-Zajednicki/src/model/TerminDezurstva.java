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
import java.time.LocalTime;

/**
 *
 * @author Omnix
 */
public class TerminDezurstva implements Serializable, DomainObject<TerminDezurstva> {

    private int id;
    private Smene smena;
    private LocalTime vremeOd;
    private LocalTime vremeDo;

    public TerminDezurstva() {
    }

    public TerminDezurstva(int id, Smene smena, LocalTime vremeOd, LocalTime vremeDo) {
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

    public Smene getSmena() {
        return smena;
    }

    public void setSmena(Smene smena) {
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

    @Override
    public String getInsertQuery() {
        return "INSERT INTO termin_dezurstva (smena, vremeOd, vremeDo) VALUES (?, ?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, smena.name());
        ps.setTime(2, java.sql.Time.valueOf(vremeOd));
        ps.setTime(3, java.sql.Time.valueOf(vremeDo));
    }

    @Override
    public String getUpdateQuery() {
        return "UPDATE termin_dezurstva SET smena = ?, vremeOd = ?, vremeDo = ? WHERE id = ?";
    }

    @Override
    public void fillUpdateStatement(PreparedStatement ps) throws SQLException {
        ps.setString(1, smena.name());
        ps.setTime(2, java.sql.Time.valueOf(vremeOd));
        ps.setTime(3, java.sql.Time.valueOf(vremeDo));
        ps.setInt(4, id);
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM termin_dezurstva WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, smena, vremeOd, vremeDo FROM termin_dezurstva WHERE id = ?";
    }

    @Override
    public TerminDezurstva createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int id = rs.getInt("id");
            Smene smena = Smene.valueOf(rs.getString("smena"));
            java.time.LocalTime vremeOd = rs.getTime("vremeOd").toLocalTime();
            java.time.LocalTime vremeDo = rs.getTime("vremeDo").toLocalTime();
            return new TerminDezurstva(id, smena, vremeOd, vremeDo);
        }
        return null;
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, id);
    }

}
