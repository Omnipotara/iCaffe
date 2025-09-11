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
import model.Angazovanje;
import model.KategorijaMusterije;
import model.Musterija;
import model.Prodavac;
import model.Racun;
import operacije.DodajSO;
import operacije.IzmeniSO;
import operacije.ObrisiSO;
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

    /* -- DEFAULT CRUD OPERACIJE DOMENSKOG OBJEKTA -- */
    public <T extends DomainObject<T>> boolean dodaj(T object) {
        return new DodajSO<T>().execute(object);
    }

    public <T extends DomainObject<T>> boolean izmeni(T object) {
        try {
            return new IzmeniSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public <T extends DomainObject<T>> boolean obrisi(T object) {
        try {
            return new ObrisiSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public <T extends DomainObject<T>> List<T> vratiSve(T object) {
        try {
            return new VratiSveSO<T>().execute(object);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<T>();
    }

    /* -- SPECIFICNE METODE -- */
    public Prodavac ulogujProdavca(Prodavac p) {
        return dbb.ulogujProdavca(p);
    }

    public Musterija ulogujMusteriju(Musterija m) {
        return dbb.ulogujMusteriju(m);
    }

    public boolean odlogujMusteriju(Musterija m) {
        return dbb.odlogujMusteriju(m);
    }

    public void smanjiVreme(int id) {
        dbb.smanjiVreme(id);
    }

    public void istekloVreme(Musterija musterija) {
        ObradiKlijentskiZahtev zaPrekidanje;
        Kontroler.getInstance().odlogujMusteriju(musterija);

        for (ObradiKlijentskiZahtev okz : listaNiti) {
            if (okz.getUlogovani() != null && okz.getUlogovani().equals(musterija)) {
                zaPrekidanje = okz;
                zaPrekidanje.ugasiNit();
                return;
            }
        }
    }

    public List<Musterija> vratiListuOnlineMusterija() {
        return dbb.vratiListuOnlineMusterija();
    }

    public int insertRacun(Racun r) {
        try {
            return dbb.insertRacun(r);
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int vratiMaxRBZaRacun(int racunId) {
        return dbb.vratiMaxRBZaRacun(racunId);
    }
}
