package kontroler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import komunikacija.Komunikacija;
import model.Musterija;
import model.Racun;
import model.StavkaRacuna;
import model.pomocne.RacunWrapper;
import model.pomocne.UlogovaniMusterija;
import operacije.Operacija;
import transfer.KlijentskiZahtev;
import view.LoginForma;
import view.ServerForma;
import view.musterija.MusterijaDetaljnijeForma;

/**
 *
 * @author Omnix
 */
public class Kontroler {

    // Odvojeni rezultati za različite tipove operacija
    private boolean poslednjiBooleanRezultat;
    private List poslednjaListaRezultat;
    private Object poslednjiObjekatRezultat;
    private int poslednjiIntRezultat;

    private ServerForma sf;
    private LoginForma lf;
    private MusterijaDetaljnijeForma mdf;

    private static Kontroler instance;

    public static Kontroler getInstance() {
        if (instance == null) {
            instance = new Kontroler();
        }
        return instance;
    }

    private Kontroler() {
    }

    // Getteri i setteri
    public ServerForma getSf() {
        return sf;
    }

    public void setSf(ServerForma sf) {
        this.sf = sf;
    }

    public LoginForma getLf() {
        return lf;
    }

    public void setLf(LoginForma lf) {
        this.lf = lf;
    }

    public MusterijaDetaljnijeForma getMdf() {
        return mdf;
    }

    public void setMdf(MusterijaDetaljnijeForma mdf) {
        this.mdf = mdf;
    }

    public void azurirajDetaljnuFormu(Musterija azuriranaMusterija) {
        if (mdf != null && mdf.getM().getId() == azuriranaMusterija.getId()) {
            mdf.azurirajVreme(azuriranaMusterija);
        }
    }

    // Callback metode za različite tipove rezultata
    public void obradiBooleanRezultat(boolean rezultat) {
        synchronized (this) {
            poslednjiBooleanRezultat = rezultat;
            notify();
        }
    }

    public void obradiListaRezultat(List rezultat) {
        synchronized (this) {
            poslednjaListaRezultat = rezultat;
            notify();
        }
    }

    public void obradiObjekatRezultat(Object rezultat) {
        synchronized (this) {
            poslednjiObjekatRezultat = rezultat;
            notify();
        }
    }

    public void obradiIntRezultat(int rezultat) {
        synchronized (this) {
            poslednjiIntRezultat = rezultat;
            notify();
        }
    }

    // Boolean operacije
    public boolean dodaj(Object objekat) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(objekat, Operacija.DODAJ);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiBooleanRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean izmeni(Object objekat) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(objekat, Operacija.IZMENI);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiBooleanRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean obrisi(Object objekat) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(objekat, Operacija.OBRISI);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiBooleanRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    public boolean odlogujMusteriju(Musterija musterija) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(musterija, Operacija.SERVER_LOGOUT);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiBooleanRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }

    // Lista operacije
    public List vratiSve(Object objekat) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(objekat, Operacija.VRATI_SVE);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjaListaRezultat != null ? poslednjaListaRezultat : new ArrayList();
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return new ArrayList();
        }
    }

    public void vratiSveMusterije() {
        
        KlijentskiZahtev zahtev = new KlijentskiZahtev(new Musterija(), Operacija.VRATI_SVE_MUSTERIJE);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
        } catch (IOException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("SALJEM ZAHTEV ZA MUSTERIJE");


    }

    public void vratiOnlineMusterije() {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(new UlogovaniMusterija(), Operacija.VRATI_ONLINE_MUSTERIJE);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
        } catch (IOException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    // Objekat operacije
    public Object vratiJednog(Object objekat) {
        KlijentskiZahtev zahtev = new KlijentskiZahtev(objekat, Operacija.LOGIN_PRODAVAC);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                
                return poslednjiObjekatRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

    // Int operacije
    public int ubaciRacun(Racun r, List<StavkaRacuna> listaStavki) {
        RacunWrapper rw = new RacunWrapper(r, listaStavki);
        KlijentskiZahtev zahtev = new KlijentskiZahtev(rw, Operacija.UBACI_RACUN);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiIntRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return -1;
        }
    }

    // Metoda za ažuriranje vremena
    public void azurirajVremeMusterije(Musterija musterija) {
        if (sf != null && sf.getListaMusterija() != null) {
            for (Musterija m : sf.getListaMusterija()) {
                if (m.getId() == musterija.getId()) {
                    m.setPreostaloVreme(musterija.getPreostaloVreme());

                    long vreme = m.getPreostaloVreme().getSeconds();
                    if (vreme == 3599 || vreme == 1799 || vreme == 599 || vreme == 0) {
                        vratiSveMusterije();
                        vratiOnlineMusterije();
                    }

                    return;
                }
            }
        }
    }

    public boolean izmeniRacun(Racun r, List<StavkaRacuna> listaStavki) {
        RacunWrapper rw = new RacunWrapper(r, listaStavki);
        KlijentskiZahtev zahtev = new KlijentskiZahtev(rw, Operacija.IZMENI_RACUN);
        try {
            Komunikacija.getInstance().posaljiZahtev(zahtev);
            synchronized (this) {
                wait(1000);
                return poslednjiBooleanRezultat;
            }
        } catch (Exception e) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, e);
            return false;
        }
    }
}
