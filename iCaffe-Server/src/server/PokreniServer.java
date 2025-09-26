/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import baza.Konekcija;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import kontroler.Kontroler;

/**
 *
 * @author Omnix
 */
public class PokreniServer extends Thread {

    private int PORT;
    private ServerSocket server;
    private boolean kraj = false;

    @Override
    public void run() {
        try {

            Properties props = new Properties();

            // direktna putanja u stringu
            String linuxPath = "/home/omnix/NetBeansProjects/iCaffe/iCaffe-Server/configuration/config.properties";
            String windowsPath = "C:\\Users\\Omnix\\Documents\\NetBeansProjects\\SeminarskiRad\\iCaffe-Server\\configuration\\config.properties";

            // ucitavanje propertija
            try (FileInputStream fis = new FileInputStream(windowsPath)) {
                props.load(fis);
            } catch (FileNotFoundException ex) {
                System.getLogger(Konekcija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            } catch (IOException ex) {
                System.getLogger(Konekcija.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }

            //drugi unos su default vrednosti ako ne uspe da nadje konfiguraciju, imam i Try Catch ako se slucajno u config unese nesto pogresno.
            String portString = props.getProperty("server.port", "9000");
            try {
                PORT = Integer.parseInt(portString);
            } catch (NumberFormatException e) {
                Logger.getLogger(PokreniServer.class.getName()).log(Level.WARNING, "Nevalidan port u config fajlu, koristi se default 9000", e);
                PORT = 9000;
            }

            server = new ServerSocket(PORT);
            System.out.println("Server je pokrenut");

            while (!kraj) {
                Socket klijent = server.accept();
                System.out.println("Klijent je pokrenut");

                ObradiKlijentskiZahtev nit = new ObradiKlijentskiZahtev(klijent);
                kontroler.Kontroler.getInstance().getSveKonekcije().add(nit);
                nit.start();

            }
        } catch (SocketException ex) {
            System.out.println("Socket je zatvoren.");
        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ugasiServer() {
        try {
            // Zaustavlja se petlja da ne docekuje vise klijente
            kraj = true;

            // Odjavljuje sve prijavljene
            Kontroler.getInstance().odjaviSvePrijavljene();

            // Zatvori se server socket
            if (server != null && !server.isClosed()) {
                server.close();
            }

            // Liste konekcija se obrisu
            kontroler.Kontroler.getInstance().ocistiKonekcije();

            System.out.println("Server je uspesno ugasen.");

        } catch (IOException ex) {
            Logger.getLogger(PokreniServer.class.getName()).log(Level.SEVERE, "Greska pri gasenju servera", ex);
        }
    }

}
