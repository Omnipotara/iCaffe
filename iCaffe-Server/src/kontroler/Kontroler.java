/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import domen.DomainObject;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Musterija;
import model.Prodavac;
import model.Racun;
import model.StavkaRacuna;
import operacije.DodajSO;
import operacije.IzmeniRacunSO;
import operacije.IzmeniSO;
import operacije.LoginMusterijaSO;
import operacije.LoginProdavacSO;
import operacije.LogoutMusterijaSO;
import operacije.LogoutProdavacSO;
import operacije.ObrisiSO;
import operacije.Operacija;
import operacije.UbaciRacunSO;
import operacije.VratiJednogSO;
import operacije.VratiSveSO;
import server.ObradiKlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Omnix
 */
public class Kontroler {

    private List<ObradiKlijentskiZahtev> sveKonekcije;
    private List<ObradiKlijentskiZahtev> listaMusterija;
    private List<ObradiKlijentskiZahtev> listaProdavaca;
    private static Kontroler instance;

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    private Kontroler() {
        sveKonekcije = new ArrayList<>();
        listaMusterija = new ArrayList<>();
        listaProdavaca = new ArrayList<>();
    }

    public List<ObradiKlijentskiZahtev> getSveKonekcije() {
        return sveKonekcije;
    }

    public void setSveKonekcije(List<ObradiKlijentskiZahtev> sveKonekcije) {
        this.sveKonekcije = sveKonekcije;
    }

    public List<ObradiKlijentskiZahtev> getListaMusterija() {
        return listaMusterija;
    }

    public void setListaMusterija(List<ObradiKlijentskiZahtev> listaMusterija) {
        this.listaMusterija = listaMusterija;
    }

    public List<ObradiKlijentskiZahtev> getListaProdavaca() {
        return listaProdavaca;
    }

    public void setListaProdavaca(List<ObradiKlijentskiZahtev> listaProdavaca) {
        this.listaProdavaca = listaProdavaca;
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

        for (ObradiKlijentskiZahtev okz : listaMusterija) {
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

    public Prodavac loginProdavac(Prodavac prodavac) {
        try {
            return (Prodavac) new LoginProdavacSO().execute(prodavac);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean logoutProdavac(Prodavac prodavac) {
        try {
            return (boolean) new LogoutProdavacSO().execute(prodavac);
        } catch (Exception ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void posaljiVremeUpdateProdavcima(Musterija musterija) {
        for (ObradiKlijentskiZahtev prodavac : listaProdavaca) {
            try {
                ServerskiOdgovor so = new ServerskiOdgovor();
                so.setOperacija(Operacija.VREME_UPDATE);
                so.setParam(musterija); // salje musteriju prodavcu

                prodavac.posaljiServerskiOdgovor(so);
            } catch (Exception e) {
                System.out.println("Greška pri slanju update-a prodavcu: " + e.getMessage());
            }
        }
    }

    public void obavestiProdavce(Musterija musterija) {

        try {
            Thread.sleep(500); // 100ms delay
        } catch (InterruptedException e) {
        }

        for (ObradiKlijentskiZahtev prodavac : listaProdavaca) {
            try {
                ServerskiOdgovor so = new ServerskiOdgovor();
                so.setOperacija(Operacija.LOGIN_NORTIFIKACIJA);
                so.setParam(musterija);
                prodavac.posaljiServerskiOdgovor(so);
                System.out.println("Poslata nortifikacija!");
            } catch (Exception e) {
                System.out.println("Greška pri slanju notifikacije: " + e.getMessage());
            }
        }
    }

    public void ocistiKonekcije() {
        sveKonekcije = new ArrayList<>();
        listaMusterija = new ArrayList<>();
        listaProdavaca = new ArrayList<>();
    }

    public void odjaviSvePrijavljene() {
        //Odjavljuje sve musterije
        for (ObradiKlijentskiZahtev musterija : listaMusterija){
            if (musterija != null && musterija.getUlogovani() != null){
                musterija.serverskiLogoutKupca(musterija.getUlogovani());
            }
        }
        
        //Odjavljuje sve zaposlene
        for (ObradiKlijentskiZahtev prodavac : listaProdavaca){
            if (prodavac != null && prodavac.isJeProdavac()){
                prodavac.serverskiLogoutProdavca();
            }
        }
    }

}
