/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBBroker;
import domen.DomainObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Angazovanje;
import model.KategorijaMusterije;
import model.Musterija;
import model.Prodavac;
import model.Racun;
import model.StavkaRacuna;
import operacije.DodajSO;
import operacije.IzmeniRacunSO;
import operacije.IzmeniSO;
import operacije.LoginMusterijaSO;
import operacije.LogoutMusterijaSO;
import operacije.ObrisiSO;
import operacije.UbaciRacunSO;
import operacije.VratiJednogSO;
import operacije.VratiSveSO;
import server.ObradiKlijentskiZahtev;
import view.ServerForma;

/**
 *
 * @author Omnix
 */
public class Kontroler {

    private List<ObradiKlijentskiZahtev> listaNiti;
    private DBBroker dbb;
    private static Kontroler instance;
    private ServerForma sf;

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    private Kontroler() {
        listaNiti = new ArrayList<>();
        dbb = new DBBroker();
    }

    public List<ObradiKlijentskiZahtev> getListaNiti() {
        return listaNiti;
    }

    public void setListaNiti(List<ObradiKlijentskiZahtev> listaNiti) {
        this.listaNiti = listaNiti;
    }

    public ServerForma getSf() {
        return sf;
    }

    public void setSf(ServerForma sf) {
        this.sf = sf;
    }

    public <T extends DomainObject<T>> boolean dodaj(T object) {
        try {
            return (boolean) new DodajSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public <T extends DomainObject<T>> boolean izmeni(T object) {
        try {
            return (boolean) new IzmeniSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public <T extends DomainObject<T>> boolean obrisi(T object) {
        try {
            return (boolean) new ObrisiSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public <T extends DomainObject<T>> T vratiJednog(T object) {
        try {
            return (T) new VratiJednogSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public <T extends DomainObject<T>> List<T> vratiSve(T object) {
        try {
            return (List<T>) new VratiSveSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<T>();
    }

    public Musterija loginMusterija(Musterija m) {
        try {
            return (Musterija) new LoginMusterijaSO().execute(m);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean logoutMusterija(Musterija m) {
        try {
            return (boolean) new LogoutMusterijaSO().execute(m);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void istekloVreme(Musterija musterija) {
        ObradiKlijentskiZahtev zaPrekidanje;
        Kontroler.getInstance().logoutMusterija(musterija);

        for (ObradiKlijentskiZahtev okz : listaNiti) {
            if (okz.getUlogovani() != null && okz.getUlogovani().equals(musterija)) {
                zaPrekidanje = okz;
                zaPrekidanje.ugasiNit();
                return;
            }
        }
    }

    public int ubaciRacun(Racun r, List<StavkaRacuna> listaStavki) {
        try {
            UbaciRacunSO so = new UbaciRacunSO(listaStavki);
            int racunId = (int) so.execute(r);
            return racunId;
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean izmeniRacun(Racun racun, List<StavkaRacuna> noveStavke) {
        try {
            return (boolean) new IzmeniRacunSO(noveStavke).execute(racun);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
