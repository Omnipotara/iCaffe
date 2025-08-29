/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import model.Prodavac;
import java.sql.*;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.KategorijaMusterije;
import model.Musterija;

/**
 *
 * @author Omnix
 */
public class DBBroker {

    public Prodavac ulogujProdavca(Prodavac p) {
        try {
            String upit = "SELECT * FROM PRODAVAC WHERE username = ? AND password = ?";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getPassword());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
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

    public Musterija ulogujMusteriju(Musterija m) {
        try {
            String upit = "SELECT * FROM MUSTERIJA m JOIN KATEGORIJA_MUSTERIJE km ON m.kategorijaId = km.id WHERE email = ? AND password = ?";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setString(1, m.getEmail());
            ps.setString(2, m.getPassword());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                m.setId(rs.getInt("m.id"));
                m.setUsername(rs.getString("m.username"));
                Duration d = Duration.ofSeconds(rs.getInt("m.preostaloVreme"));
                m.setPreostaloVreme(d);
                
                KategorijaMusterije km = new KategorijaMusterije(rs.getInt("km.id"), rs.getString("km.naziv"), rs.getInt("km.popust"));
                
                m.setKategorijaMusterije(km);

                return m;
            }
            m = null;
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
