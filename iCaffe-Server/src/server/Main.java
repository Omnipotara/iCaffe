/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

/**
 *
 * @author Omnix
 */
public class Main {

    public static void main(String[] args) {
        PokreniServer server = new PokreniServer();
        server.start();

        System.out.println("Server se pokusava pokrenuti na portu 9000...");
    }
}
