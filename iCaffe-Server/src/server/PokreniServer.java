/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omnix
 */
public class PokreniServer extends Thread {

    private final int PORT = 9000;
    private ServerSocket server;
    private boolean kraj = false;

    @Override
    public void run() {
        try {
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

}
