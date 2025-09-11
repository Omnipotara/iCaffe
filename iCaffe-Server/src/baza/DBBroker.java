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

                if (daLiJeUlogovan(m)) {
                    m = new Musterija();
                    m.setId(-2);
                } else {
                    zabeleziUlogovanog(m);
                }
                return m;
            }
            m = new Musterija();
            m.setId(-1);
            return m;
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        m = new Musterija();
        m.setId(-1);
        return m;
    }

    public boolean daLiJeUlogovan(Musterija musterija) {
        try {
            String upit = "SELECT * FROM MUSTERIJA_ULOGOVANI WHERE id = ?";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setInt(1, musterija.getId());
            ResultSet rs = ps.executeQuery();

            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean zabeleziUlogovanog(Musterija musterija) {
        try {
            String upit = "INSERT INTO MUSTERIJA_ULOGOVANI "
                    + "(id, username) "
                    + "VALUES (?, ?)";
            try (PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit)) {
                ps.setInt(1, musterija.getId());
                ps.setString(2, musterija.getUsername());

                int inserted = ps.executeUpdate();
                Konekcija.getInstance().getKonekcija().commit();
                return inserted > 0;
            }
        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException e) {
            }
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean odlogujMusteriju(Musterija m) {
        try {
            String upit = "DELETE FROM MUSTERIJA_ULOGOVANI WHERE id = ?";
            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ps.setInt(1, m.getId());
            int deleted = ps.executeUpdate();
            Konekcija.getInstance().getKonekcija().commit();

            return deleted > 0;

        } catch (SQLException ex) {
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException ex1) {
                Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex1);
            }
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

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

    public List<Musterija> vratiListuOnlineMusterija() {
        List<Musterija> lista = new ArrayList<>();
        try {
            String upit = "SELECT m.id, m.email, m.username, m.password, m.kategorijaId, m.preostaloVreme, "
                    + "k.naziv AS kategorijaNaziv, k.popust AS kategorijaPopust "
                    + "FROM musterija_ulogovani mu "
                    + "JOIN musterija m ON mu.id = m.id "
                    + "LEFT JOIN kategorija_musterije k ON m.kategorijaId = k.id";

            PreparedStatement ps = Konekcija.getInstance().getKonekcija().prepareStatement(upit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                String username = rs.getString("username");
                String password = rs.getString("password");

                int kategorijaId = rs.getInt("kategorijaId");
                String naziv = rs.getString("kategorijaNaziv");
                int popust = rs.getInt("kategorijaPopust");
                KategorijaMusterije kategorija = new KategorijaMusterije(kategorijaId, naziv, popust);

                long vreme = rs.getLong("preostaloVreme");
                Duration preostalo = Duration.ofSeconds(vreme);

                Musterija m = new Musterija(id, email, username, password, kategorija, preostalo);
                lista.add(m);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lista;

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
