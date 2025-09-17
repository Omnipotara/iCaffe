/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omnix
 */
public class UlogovaniMusterija extends Musterija {
    
    public UlogovaniMusterija(){
        
    }

    public UlogovaniMusterija(int id, String username) {
        super(id, null, username, null, null, null); // samo id i username su mi u bazi
    }

    @Override
    public String getInsertQuery() {
        return "INSERT INTO musterija_ulogovani (id, username) VALUES (?, ?)";
    }

    @Override
    public void fillInsertStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, getId());
        ps.setString(2, getUsername());
    }

    @Override
    public String getSelectQuery() {
        return "SELECT id, username FROM musterija_ulogovani WHERE id = ?";
    }

    @Override
    public void fillSelectStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, getId());
    }

    @Override
    public String getSelectAllQuery() {
        return "SELECT id, username FROM musterija_ulogovani";
    }

    @Override
    public void fillSelectAllStatement(PreparedStatement ps) throws SQLException {
        // Nema parametara za sad
    }

    @Override
    public Musterija createFromResultSet(ResultSet rs) throws SQLException {
        if (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            Musterija m = new Musterija();
            m.setId(id);
            m.setUsername(username);
            return m;
        }
        return null;
    }

    @Override
    public List<Musterija> createListFromResultSet(ResultSet rs) throws SQLException {
        List<Musterija> lista = new ArrayList<>();
        while (rs.next()) {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            Musterija m = new Musterija();
            m.setId(id);
            m.setUsername(username);
            lista.add(m);
        }
        return lista;
    }

    @Override
    public String getDeleteQuery() {
        return "DELETE FROM musterija_ulogovani WHERE id = ?";
    }

    @Override
    public void fillDeleteStatement(PreparedStatement ps) throws SQLException {
        ps.setInt(1, getId());
    }

}
