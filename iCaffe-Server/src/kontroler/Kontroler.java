/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
import model.Angazovanje;
import model.KategorijaMusterije;
import model.Musterija;
import model.Prodavac;
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

    public boolean dodajNovoAngazovanje(Angazovanje a) {
        try {
            return dbb.insert(a);
        } catch (Exception e) {
        }
        return false;
    }

    public List<Angazovanje> vratiListuAngazovanja() {
        return dbb.selectAll(new Angazovanje());
    }

    public boolean obrisiAngazovanje(Angazovanje a) {
        return dbb.delete(a);
    }

    public boolean promeniAngazovanje(Angazovanje a) {
        return dbb.update(a);
    }

    public DBBroker getDbb() {
        return dbb;
    }

    public void setDbb(DBBroker dbb) {
        this.dbb = dbb;
    }

    public ServerForma getSf() {
        return sf;
    }

    public void setSf(ServerForma sf) {
        this.sf = sf;
    }

    public List<Musterija> vratiListuOnlineMusterija() {
        return dbb.vratiListuOnlineMusterija();
    }

    public List<KategorijaMusterije> vratiListuKategorijaMusterija() {
        return dbb.selectAll(new KategorijaMusterije());
    }

    public boolean obrisiKategorijuMusterije(KategorijaMusterije km) {
        return dbb.delete(km);
    }

    public boolean dodajNovuKategorijuMusterije(KategorijaMusterije km) {
        return dbb.insert(km);
    }

}
