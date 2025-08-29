package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;
import model.Musterija;
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

    public ObradiKlijentskiZahtev(Socket s) {
        this.s = s;
    }

    @Override
    public void run() {
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
                    
                    if(ulogovan != null){
                        this.ulogovan = ulogovan;
                    }
                    
                    so.setParam(ulogovan);
                    posaljiServerskiOdgovor(so);
                    break;
                default:
                    throw new AssertionError();
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

}
