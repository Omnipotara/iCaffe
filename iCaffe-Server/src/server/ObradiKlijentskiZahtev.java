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
import niti.MusterijaTimerNit;
import operacije.Operacija;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Omnix
 */
public class ObradiKlijentskiZahtev extends Thread {

    private Socket s;
    private Musterija ulogovan = null;
    private boolean kraj = false;
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
                    case Operacija.LOGIN:
                        Musterija m = (Musterija) kz.getParam();
                        Musterija ulogovan = Kontroler.getInstance().ulogujMusteriju(m);

                        if (ulogovan.getId() > 0) {
                            this.ulogovan = ulogovan;
                            Kontroler.getInstance().getListaNiti().add(this);
                            mtn = new MusterijaTimerNit(this.ulogovan);
                            mtn.start();
                        }

                        so.setParam(ulogovan);
                        posaljiServerskiOdgovor(so);

                        Kontroler.getInstance().getSf().osveziTabelu();
                        break;

                    case Operacija.LOGOUT:
                        m = (Musterija) kz.getParam();
                        boolean odlogovan = Kontroler.getInstance().odlogujMusteriju(m);
                        Kontroler.getInstance().getListaNiti().remove(this);
                        this.ulogovan = null;
                        mtn.setKraj(true);

                        so.setParam(odlogovan);
                        so.setOperacija(Operacija.LOGOUT);
                        posaljiServerskiOdgovor(so);

                        kraj = true;

                        Kontroler.getInstance().getSf().osveziTabelu();
                        break;

                    case Operacija.REGISTER:
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
                            posaljiServerskiOdgovor(so);
                            Kontroler.getInstance().getSf().osveziTabelu();
                        } else {
                            so.setParam(null);
                            posaljiServerskiOdgovor(so);
                        }

                        break;

                    case Operacija.AZURIRANJE_PASSWORD:
                        m = (Musterija) kz.getParam();
                        boolean izmenjeno = Kontroler.getInstance().izmeni(m);
                        so.setOperacija(Operacija.AZURIRANJE_PASSWORD);
                        so.setParam(izmenjeno);
                        posaljiServerskiOdgovor(so);
                        break;

                    case Operacija.AZURIRANJE_USERNAME:
                        m = (Musterija) kz.getParam();
                        listaMusterija = Kontroler.getInstance().vratiSve(m);
                        duplikat = false;

                        for (Musterija mus : listaMusterija) {
                            if (m.getUsername().equals(mus.getUsername())) {
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
                        
                        so.setParam(izmenjeno);
                        so.setOperacija(Operacija.AZURIRANJE_USERNAME);
                        posaljiServerskiOdgovor(so);
                        
                        Kontroler.getInstance().getSf().osveziTabelu();

                        break;

                    default:
                        throw new AssertionError();
                }
            }
        } finally {
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

    public void ugasiNit() {
        Kontroler.getInstance().getListaNiti().remove(this);
        this.ulogovan = null;
        mtn.setKraj(true);
        kraj = true;

    }

    public void serverskiLogoutKupca(Musterija m) {
        boolean odlogovan = Kontroler.getInstance().odlogujMusteriju(m);
        Kontroler.getInstance().getListaNiti().remove(this);
        this.ulogovan = null;
        mtn.setKraj(true);

        ServerskiOdgovor so = new ServerskiOdgovor(odlogovan, Operacija.SERVER_LOGOUT);
        posaljiServerskiOdgovor(so);

        kraj = true;

        Kontroler.getInstance().getSf().osveziTabelu();
    }

}
