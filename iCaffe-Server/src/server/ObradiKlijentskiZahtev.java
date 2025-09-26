package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;
import model.Musterija;
import domen.DomainObject;
import java.util.ArrayList;
import model.Prodavac;
import operacije.Operacija;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;
import model.pomocne.RacunWrapper;
import model.pomocne.UlogovaniMusterija;
import niti.MusterijaTimerNit;
import static operacije.Operacija.VRATI_ONLINE_MUSTERIJE;
import static operacije.Operacija.VRATI_SVE_MUSTERIJE;

public class ObradiKlijentskiZahtev extends Thread {

    private Socket s;
    private Musterija ulogovan = null;
    private boolean kraj = false;
    private boolean jeProdavac = false;
    private MusterijaTimerNit mtn;

    public ObradiKlijentskiZahtev(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
        try {
            while (!kraj) {
                KlijentskiZahtev kz = primiKlijentskiZahtev();
                ServerskiOdgovor so = new ServerskiOdgovor();

                if (kz == null) {
                    System.out.println("Klijentski zahtev je prazan : NEMA KONEKCIJE");
                    break;
                }

                switch (kz.getOperacija()) {
                    // MUSTERIJA OPERACIJE
                    case LOGIN:
                        Musterija m = (Musterija) kz.getParam();
                        Musterija ulogovan = Kontroler.getInstance().loginMusterija(m);

                        if (ulogovan.getId() > 0) {
                            this.ulogovan = ulogovan;
                            Kontroler.getInstance().getListaMusterija().add(this);
                            Kontroler.getInstance().getSveKonekcije().remove(this);

                            mtn = new MusterijaTimerNit(this.ulogovan);
                            mtn.start();

                            Kontroler.getInstance().obavestiProdavce(ulogovan);
                            //Kontroler.getInstance().posaljiVremeUpdateProdavcima(ulogovan);
                        }

                        so.setParam(ulogovan);
                        so.setOperacija(Operacija.LOGIN);

                        System.out.println(ulogovan.getPreostaloVreme().toSeconds());
                        posaljiServerskiOdgovor(so);

                        // Obavesti prodavce o novoj mušteriji
                        break;

                    case LOGOUT:
                        m = (Musterija) kz.getParam();
                        boolean odlogovan = Kontroler.getInstance().logoutMusterija(m);
                        Kontroler.getInstance().getListaMusterija().remove(this);
                        this.ulogovan = null;
                        mtn.setKraj(true);

                        so.setParam(odlogovan);
                        so.setOperacija(Operacija.LOGOUT);
                        posaljiServerskiOdgovor(so);

                        kraj = true;

                        // Obavesti prodavce o logout-u
                        Kontroler.getInstance().obavestiProdavce(m);
                        //Kontroler.getInstance().posaljiVremeUpdateProdavcima(m);
                        break;

                    case REGISTER:
                        m = (Musterija) kz.getParam();
                        List<Musterija> listaMusterija = Kontroler.getInstance().vratiSve(m);
                        boolean duplikat = false;

                        for (Musterija mus : listaMusterija) {
                            if (m.getEmail().equals(mus.getEmail())) {
                                so.setParam(-1);
                                posaljiServerskiOdgovor(so);
                                duplikat = true;
                                break;
                            }
                            if (m.getUsername().equals(mus.getUsername())) {
                                so.setParam(-2);
                                posaljiServerskiOdgovor(so);
                                duplikat = true;
                                break;
                            }
                        }

                        if (duplikat) {
                            break;
                        }

                        boolean dodat = Kontroler.getInstance().dodaj(m);
                        if (dodat) {
                            so.setParam(1);
                            Kontroler.getInstance().obavestiProdavce(m);
                            posaljiServerskiOdgovor(so);
                        } else {
                            so.setParam(null);
                            posaljiServerskiOdgovor(so);
                        }
                        break;

                    case AZURIRANJE_PASSWORD:
                        m = (Musterija) kz.getParam();
                        boolean izmenjeno = Kontroler.getInstance().izmeni(m);

                        if (izmenjeno) {
                            Kontroler.getInstance().obavestiProdavce(m);
                        }

                        so.setOperacija(Operacija.AZURIRANJE_PASSWORD);
                        so.setParam(izmenjeno);
                        posaljiServerskiOdgovor(so);
                        break;

                    case AZURIRANJE_USERNAME:
                        m = (Musterija) kz.getParam();
                        listaMusterija = Kontroler.getInstance().vratiSve(m);
                        duplikat = false;

                        for (Musterija mus : listaMusterija) {
                            if (m.getUsername().equals(mus.getUsername()) && m.getId() != mus.getId()) {
                                so.setParam(false);
                                so.setOperacija(Operacija.AZURIRANJE_USERNAME);
                                posaljiServerskiOdgovor(so);
                                duplikat = true;
                                break;
                            }
                        }

                        if (duplikat) {
                            break;
                        }

                        izmenjeno = Kontroler.getInstance().izmeni(m);

                        if (izmenjeno) {
                            Kontroler.getInstance().obavestiProdavce(m);
                        }

                        so.setParam(izmenjeno);
                        so.setOperacija(Operacija.AZURIRANJE_USERNAME);
                        posaljiServerskiOdgovor(so);
                        break;

                    // PRODAVAC OPERACIJE
                    case LOGIN_PRODAVAC:
                        Prodavac prodavac = (Prodavac) kz.getParam();

                        Prodavac ulogovanProdavac = Kontroler.getInstance().loginProdavac(prodavac);

                        if (ulogovanProdavac != null && ulogovanProdavac.getId() > 0) {
                            this.jeProdavac = true;
                            Kontroler.getInstance().getListaProdavaca().add(this);
                            Kontroler.getInstance().getSveKonekcije().remove(this);

                            System.out.println("SERVER: Šaljem objekat: " + ulogovanProdavac);
                            System.out.println("SERVER: ID: " + ulogovanProdavac.getId());

                            so.setParam(ulogovanProdavac);
                            System.out.println("Prodavac se uspesno ulogovao: " + ulogovanProdavac.getUsername());
                        } else {
                            so.setParam(null); // neuspesan login
                            System.out.println("Neuspesan login prodavca");
                        }

                        so.setOperacija(Operacija.VRATI_JEDNOG);
                        posaljiServerskiOdgovor(so);
                        break;

                    case LOGOUT_PRODAVAC:

                        this.jeProdavac = false;

                        so = new ServerskiOdgovor(true, Operacija.LOGOUT_PRODAVAC);
                        posaljiServerskiOdgovor(so);

                        // Log poruka
                        System.out.println("Prodavac se odlogovao");

                        kraj = true;
                        break;

                    // GENERIČKE OPERACIJE (uglavnom za prodavca)
                    case DODAJ:
                        DomainObject objekat = (DomainObject) kz.getParam();
                        boolean dodatObj = Kontroler.getInstance().dodaj(objekat);

                        so.setParam(dodatObj);
                        so.setOperacija(Operacija.DODAJ);
                        posaljiServerskiOdgovor(so);
                        break;

                    case IZMENI:
                        objekat = (DomainObject) kz.getParam();
                        boolean izmenjen = Kontroler.getInstance().izmeni(objekat);

                        so.setParam(izmenjen);
                        so.setOperacija(Operacija.IZMENI);
                        posaljiServerskiOdgovor(so);
                        break;

                    case OBRISI:
                        objekat = (DomainObject) kz.getParam();
                        boolean obrisan = Kontroler.getInstance().obrisi(objekat);

                        so.setParam(obrisan);
                        so.setOperacija(Operacija.OBRISI);
                        posaljiServerskiOdgovor(so);
                        break;

                    case VRATI_JEDNOG:
                        objekat = (DomainObject) kz.getParam();
                        DomainObject rezultat = Kontroler.getInstance().vratiJednog(objekat);

                        so.setParam(rezultat);
                        so.setOperacija(Operacija.VRATI_JEDNOG);
                        posaljiServerskiOdgovor(so);
                        break;

                    case VRATI_SVE:
                        objekat = (DomainObject) kz.getParam();
                        List lista = Kontroler.getInstance().vratiSve(objekat);

                        so.setParam(lista);
                        so.setOperacija(Operacija.VRATI_SVE);
                        posaljiServerskiOdgovor(so);
                        break;

                    case VRATI_SVE_MUSTERIJE:
                        listaMusterija = Kontroler.getInstance().vratiSve(new Musterija());

                        so = new ServerskiOdgovor();
                        so.setParam(listaMusterija);
                        so.setOperacija(VRATI_SVE_MUSTERIJE);
                        posaljiServerskiOdgovor(so);
                        break;

                    case VRATI_ONLINE_MUSTERIJE:
                        listaMusterija = Kontroler.getInstance().vratiSve(new UlogovaniMusterija());

                        so = new ServerskiOdgovor();
                        so.setParam(listaMusterija);
                        so.setOperacija(VRATI_ONLINE_MUSTERIJE);
                        posaljiServerskiOdgovor(so);
                        break;

                    case UBACI_RACUN:
                        RacunWrapper wrapper = (RacunWrapper) kz.getParam();
                        int racunId = Kontroler.getInstance().ubaciRacun(wrapper.getRacun(), wrapper.getStavke());

                        System.out.println("UBACEN RACUN: " + racunId);

                        so.setParam(racunId);
                        so.setOperacija(Operacija.UBACI_RACUN);
                        posaljiServerskiOdgovor(so);
                        break;

                    case IZMENI_RACUN:
                        wrapper = (RacunWrapper) kz.getParam();
                        boolean racunIzmenjen = Kontroler.getInstance().izmeniRacun(wrapper.getRacun(), wrapper.getStavke());

                        so.setParam(racunIzmenjen);
                        so.setOperacija(Operacija.IZMENI_RACUN);
                        posaljiServerskiOdgovor(so);
                        break;

                    case SERVER_LOGOUT:
                        Musterija musterijaZaLogout = (Musterija) kz.getParam();

                        // Trazi nit musterije
                        ObradiKlijentskiZahtev ciljanaNit = null;
                        for (ObradiKlijentskiZahtev nit : Kontroler.getInstance().getListaMusterija()) {
                            if (nit.getUlogovani() != null && nit.getUlogovani().getId() == musterijaZaLogout.getId()) {
                                ciljanaNit = nit;
                                break;
                            }
                        }

                        if (ciljanaNit != null) {
                            // Zove serverskiLogoutKupca u niti musterije
                            ciljanaNit.serverskiLogoutKupca(musterijaZaLogout);
                            so.setParam(true);
                        } else {
                            so.setParam(false); // musterija nije pronadjena, error
                        }

                        Kontroler.getInstance().obavestiProdavce(musterijaZaLogout);
                        so.setOperacija(Operacija.SERVER_LOGOUT);
                        posaljiServerskiOdgovor(so);
                        break;

                    default:
                        System.out.println("Nepoznata operacija: " + kz.getOperacija());
                        break;
                }
            }
        } finally {
            // Ukloni iz odgovarajuće liste pri zatvaranju
            if (jeProdavac) {
                Kontroler.getInstance().getListaProdavaca().remove(this);
            } else {
                Kontroler.getInstance().getListaMusterija().remove(this);
                if (mtn != null) {
                    mtn.setKraj(true);
                }
            }

            if (s != null && !s.isClosed()) {
                try {
                    s.close();
                    System.out.println("Korisnik se odvezao.");
                    System.out.flush();
                } catch (IOException ex) {
                    Logger.getLogger(ObradiKlijentskiZahtev.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public KlijentskiZahtev primiKlijentskiZahtev() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            KlijentskiZahtev kz = (KlijentskiZahtev) ois.readObject();
            return kz;

        } catch (EOFException | SocketException ex) {
            System.out.println("Korisnik se nasilno odvezao.");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(ObradiKlijentskiZahtev.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiServerskiOdgovor(ServerskiOdgovor so) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(so);
            oos.flush();
        } catch (EOFException | SocketException ex) {
            System.out.println("Korisnik se nasilno odvezao.");
        } catch (IOException ex) {
            Logger.getLogger(ObradiKlijentskiZahtev.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Getteri i setteri
    public Socket getS() {
        return s;
    }

    public void setS(Socket s) {
        this.s = s;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }

    public Musterija getUlogovani() {
        return ulogovan;
    }

    public void setUlogovani(Musterija ulogovan) {
        this.ulogovan = ulogovan;
    }

    public MusterijaTimerNit getMtn() {
        return mtn;
    }

    public void setMtn(MusterijaTimerNit mtn) {
        this.mtn = mtn;
    }

    public boolean isJeProdavac() {
        return jeProdavac;
    }

    public void setJeProdavac(boolean jeProdavac) {
        this.jeProdavac = jeProdavac;
    }

    public void ugasiNit() {
        Kontroler.getInstance().getListaMusterija().remove(this);
        this.ulogovan = null;
        if (mtn != null) {
            mtn.setKraj(true);
        }
        kraj = true;
    }

    public void serverskiLogoutKupca(Musterija m) {
        boolean odlogovan = Kontroler.getInstance().logoutMusterija(m);
        this.ulogovan = null;
        if (mtn != null) {
            mtn.setKraj(true);
        }

        ServerskiOdgovor so = new ServerskiOdgovor(odlogovan, Operacija.SERVER_LOGOUT);
        posaljiServerskiOdgovor(so);

        kraj = true;

        // Obavesti prodavce o logout-u
        Kontroler.getInstance().posaljiVremeUpdateProdavcima(m);
    }

    public void serverskiLogoutProdavca() {
        this.jeProdavac = false;

        ServerskiOdgovor so = new ServerskiOdgovor(true, Operacija.SERVER_LOGOUT_PRODAVCA);
        posaljiServerskiOdgovor(so);

        kraj = true;
    }
}
