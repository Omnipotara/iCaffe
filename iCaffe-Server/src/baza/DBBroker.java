/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import model.Prodavac;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omnix
 */
public class DBBroker {

    public Prodavac uloguj(Prodavac p) {
        try {
            String upit = "SELECT * FROM PRODAVAC WHERE username = ? AND password = ?";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                p.setId(rs.getInt("id"));
                p.setEmail(rs.getString("email"));
                p.setIme(rs.getString("ime"));
                p.setPrezime(rs.getString("prezime"));
                return p;
            }
            p = null;
            return p;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
