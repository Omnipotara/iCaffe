/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import domen.DomainObject;
import java.sql.*;
import java.util.List;
import model.Racun;

/**
 *
 * @author Omnix
 */
public class DBBroker {

    // --- DEFAULT CRUD metode ---
    public <T extends DomainObject<T>> Object insert(T obj) throws SQLException {
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija()
                .prepareStatement(obj.getInsertQuery(), Statement.RETURN_GENERATED_KEYS)) {

            obj.fillInsertStatement(ps);
            int affectedRows = ps.executeUpdate();

            if (obj instanceof Racun) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1); // auto-inkrement primary key
                    }
                }
            }

            return affectedRows > 0; // boolean za sve ostalo osim racuna
        }
    }

    public <T extends DomainObject<T>> Object update(T obj) throws SQLException {
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija()
                .prepareStatement(obj.getUpdateQuery())) {
            obj.fillUpdateStatement(ps);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    public <T extends DomainObject<T>> Object delete(T obj) throws SQLException {
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija()
                .prepareStatement(obj.getDeleteQuery())) {
            obj.fillDeleteStatement(ps);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        }
    }

    public <T extends DomainObject<T>> T select(T obj) throws SQLException {
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija()
                .prepareStatement(obj.getSelectQuery())) {
            obj.fillSelectStatement(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return obj.createFromResultSet(rs);
            }
        }
    }

    public <T extends DomainObject<T>> List<T> selectAll(T obj) throws SQLException {
        try (PreparedStatement ps = Konekcija.getInstance().getKonekcija()
                .prepareStatement(obj.getSelectAllQuery())) {
            obj.fillSelectAllStatement(ps);
            try (ResultSet rs = ps.executeQuery()) {
                return obj.createListFromResultSet(rs);
            }
        }
    }

}
