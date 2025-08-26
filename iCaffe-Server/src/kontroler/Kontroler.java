/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
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
    public static Kontroler getInstance(){
        if(instance == null){
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

    public Prodavac uloguj(Prodavac p) {
        return dbb.uloguj(p);
    }
    
    
    
    
}
