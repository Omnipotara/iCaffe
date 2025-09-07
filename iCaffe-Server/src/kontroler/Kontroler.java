/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
import model.Musterija;
import model.Prodavac;
import server.ObradiKlijentskiZahtev;

/**
 *
 * @author Omnix
 */
public class Kontroler {

    private List<ObradiKlijentskiZahtev> listaNiti;
    private DBBroker dbb;
    private static Kontroler instance;

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

}
