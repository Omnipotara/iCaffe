package komunikacija;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;
import model.Musterija;
import operacije.Operacija;
import static operacije.Operacija.VRATI_SVE;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

// Komunikacija klasa za Prodavac - prima poruke od servera
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
                    // Osnovni prodavac operacije
                    case LOGOUT_PRODAVAC:
                        kraj = true;
                        Kontroler.getInstance().getSf().prikaziLogoutPoruku();
                        break;

                    // Real-time update vremena musterija
                    case VREME_UPDATE:
                        if (so.getParam() != null) {
                            Musterija musterija = (Musterija) so.getParam();
                            Kontroler.getInstance().azurirajVremeMusterije(musterija);
                            if (Kontroler.getInstance().getMdf() != null) {
                                Kontroler.getInstance().azurirajDetaljnuFormu(musterija);
                            }
                        }
                        break;

                    // Boolean operacije
                    case DODAJ:
                        if (so.getParam() != null) {
                            boolean uspeh = (boolean) so.getParam();
                            Kontroler.getInstance().obradiBooleanRezultat(uspeh);
                        }
                        break;

                    case IZMENI:
                        if (so.getParam() != null) {
                            boolean uspeh = (boolean) so.getParam();
                            Kontroler.getInstance().obradiBooleanRezultat(uspeh);
                        }
                        break;

                    case OBRISI:
                        if (so.getParam() != null) {
                            boolean uspeh = (boolean) so.getParam();
                            Kontroler.getInstance().obradiBooleanRezultat(uspeh);
                        }
                        break;

                    case IZMENI_RACUN:
                        if (so.getParam() != null) {
                            boolean uspeh = (boolean) so.getParam();
                            Kontroler.getInstance().obradiBooleanRezultat(uspeh);
                        }
                        break;

                    case SERVER_LOGOUT:
                        if (so.getParam() != null) {
                            boolean uspeh = (boolean) so.getParam();
                            Kontroler.getInstance().obradiBooleanRezultat(uspeh);
                        }
                        break;

                    // Lista operacije
                    case VRATI_SVE:
                        if (so.getParam() != null) {
                            java.util.List lista = (java.util.List) so.getParam();
                            Kontroler.getInstance().obradiListaRezultat(lista);
                        } else {
                            Kontroler.getInstance().obradiListaRezultat(new java.util.ArrayList());
                        }
                        break;

                    case VRATI_SVE_MUSTERIJE:
                        List<Musterija> sve = so.getParam() != null ? (List<Musterija>) so.getParam() : new ArrayList<>();
                        Kontroler.getInstance().getSf().postaviSveMusterije(sve);
                        break;

                    case VRATI_ONLINE_MUSTERIJE:
                        List<Musterija> online = so.getParam() != null ? (List<Musterija>) so.getParam() : new ArrayList<>();
                        Kontroler.getInstance().getSf().postaviOnlineMusterije(online);
                        break;
                        
                    // Objekat operacije
                    case VRATI_JEDNOG:
                        System.out.println("PRODAVAC: Primio odgovor: " + so.getParam());
                        if (so.getParam() != null) {
                            System.out.println("PRODAVAC: Tip objekta: " + so.getParam().getClass());
                        }
                        Kontroler.getInstance().obradiObjekatRezultat(so.getParam());
                        break;

                    case LOGIN_PRODAVAC:
                        System.out.println("PRODAVAC: Login odgovor: " + so.getParam());
                        if (so.getParam() != null) {
                            System.out.println("PRODAVAC: Tip objekta: " + so.getParam().getClass());
                        }
                        Kontroler.getInstance().obradiObjekatRezultat(so.getParam());
                        break;

                    // Int operacije
                    case UBACI_RACUN:
                        if (so.getParam() != null) {
                            int racunId = (int) so.getParam();
                            Kontroler.getInstance().obradiIntRezultat(racunId);
                        } else {
                            Kontroler.getInstance().obradiIntRezultat(-1);
                        }
                        break;

                    // Notifikacije
                    case LOGIN_NORTIFIKACIJA:
                        if (so.getParam() != null) {
                            Musterija musterija = (Musterija) so.getParam();
                            System.out.println("LOGIN_NOTIFIKACIJA za musteriju: " + musterija.getUsername());

                            Kontroler.getInstance().vratiSveMusterije();
                            Kontroler.getInstance().vratiOnlineMusterije();

                        }
                        break;

                    default:
                        System.out.println("Nepoznata operacija primljena: " + so.getOperacija());
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Greška u Komunikacija thread-u: " + e.getMessage());
            e.printStackTrace();
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
            System.out.println("Prekinuta konekcija sa serverom.");
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
            System.out.println("Prekinuta konekcija sa serverom.");
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
}
