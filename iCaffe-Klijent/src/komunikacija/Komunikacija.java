/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package komunikacija;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import kontroler.Kontroler;
import operacije.Operacija;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Omnix
 */
public class Komunikacija extends Thread {

    private static Komunikacija instance;
    private Socket s;
    private boolean kraj = false;

    public static Komunikacija getInstance() throws IOException {
        if (instance == null) {
            instance = new Komunikacija();
        }
        return instance;
    }

    private Komunikacija() throws IOException {
        s = new Socket("localhost", 9000);
    }

    @Override
    public void run() {
        try {
            while (!kraj) {
                ServerskiOdgovor so = primiOdgovor();
                if (so == null) {
                    return;
                }

                switch (so.getOperacija()) {
                    case Operacija.LOGOUT:
                        kraj = true;
                        Kontroler.getInstance().getKf().prikaziLogoutPoruku(false);
                        break;

                    case Operacija.SERVER_LOGOUT:
                        kraj = true;
                        Kontroler.getInstance().getKf().prikaziLogoutPoruku(true);
                        break;

                    case Operacija.AZURIRANJE_PASSWORD:
                        if (so.getParam() != null){
                            boolean izmenjeno = (boolean) so.getParam();
                            Kontroler.getInstance().getKf().getIkf().obradiPromenu(izmenjeno);
                        }
                        break;

                    case Operacija.AZURIRANJE_USERNAME:
                        if (so.getParam() != null){
                            boolean izmenjeno = (boolean) so.getParam();
                            Kontroler.getInstance().getKf().getIkf().obradiPromenu(izmenjeno);
                        }
                        break;
                        
                    default:
                        throw new AssertionError();
                }
            }
        } finally {
            if (s != null && !s.isClosed()) {
                try {
                    s.close();
                } catch (IOException ex) {
                    Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public ServerskiOdgovor primiOdgovor() {
        try {
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            ServerskiOdgovor so = (ServerskiOdgovor) ois.readObject();
            return so;
        } catch (EOFException | SocketException ex) {
            System.out.println("Prekinuta konekcija.");

        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void posaljiZahtev(KlijentskiZahtev kz) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject(kz);
            oos.flush();
        } catch (SocketException ex) {
            System.out.println("Prekinuta konekcija.");
        } catch (IOException ex) {
            Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void odjava() {
        if (s != null && !s.isClosed()) {
            try {
                s.close();
            } catch (IOException ex) {
                Logger.getLogger(Komunikacija.class.getName()).log(Level.SEVERE, null, ex);
            }
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

}
