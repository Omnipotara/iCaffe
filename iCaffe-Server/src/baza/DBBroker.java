/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import domen.DomainObject;
import model.Prodavac;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KategorijaMusterije;
import model.Musterija;
import model.Racun;

/**
 *
 * @author Omnix
 */
public class DBBroker {

    // --- DEFAULT CRUD metode ---
    public <T extends DomainObject<T>> boolean insert(T obj) throws SQLException {
        PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(obj.getInsertQuery());
        obj.fillInsertStatement(ps);
        int affectedRows = ps.executeUpdate();
        Konekcija.getInstance().getKonekcija().commit();
        return affectedRows > 0;

    }

    public <T extends DomainObject<T>> boolean update(T obj) throws SQLException {
        PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(obj.getUpdateQuery());
        obj.fillUpdateStatement(ps);
        int affectedRows = ps.executeUpdate();
        Konekcija.getInstance().getKonekcija().commit();
        return affectedRows > 0;
    }

    public <T extends DomainObject<T>> boolean delete(T obj) throws SQLException {
        PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(obj.getDeleteQuery());
        obj.fillDeleteStatement(ps);
        int affectedRows = ps.executeUpdate();
        Konekcija.getInstance().getKonekcija().commit();
        return affectedRows > 0;

    }

    public <T extends DomainObject<T>> T select(T obj) throws SQLException {
        PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(obj.getSelectQuery());
        obj.fillSelectStatement(ps);
        ResultSet rs = ps.executeQuery();
        return obj.createFromResultSet(rs);
    }

    public <T extends DomainObject<T>> List<T> selectAll(T obj) throws SQLException {
        PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(obj.getSelectAllQuery());
        obj.fillSelectAllStatement(ps);
        ResultSet rs = ps.executeQuery();
        return obj.createListFromResultSet(rs);
    }

    // --- SPECIFICNE metode ---

    public void smanjiVreme(int id) {
        try {
            String upit = "UPDATE musterija SET preostaloVreme = preostaloVreme - 1 WHERE id = ? AND preostaloVreme > 0";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setInt(1, id);

            int updated = ps.executeUpdate();
            if (updated > 0) {
                Konekcija.getInstance().getKonekcija().commit();
            } else {
                Konekcija.getInstance().getKonekcija().rollback();
            }
        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException e) {
            }
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int insertRacun(Racun r) throws SQLException {
        String upit = "INSERT INTO racun (datum, ukupnacena, prodavacId, musterijaId) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit, Statement.RETURN_GENERATED_KEYS)) {

            LocalDate local = r.getDatum().toLocalDate();
            ps.setDate(1, java.sql.Date.valueOf(local));
            ps.setDouble(2, r.getUkupnaCena());
            ps.setInt(3, r.getProdavac().getId());
            ps.setInt(4, r.getMusterija().getId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    r.setId(id);
                    return id;
                }
            }
        }
        return -1;
    }

    public int vratiMaxRBZaRacun(int racunId) {
        String upit = "SELECT COALESCE(MAX(rb), 0) FROM stavka_racuna WHERE racunId = ?";
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit)) {
            ps.setInt(1, racunId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) + 1;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }
}
