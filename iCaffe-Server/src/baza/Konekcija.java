/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omnix
 */
public class Konekcija {

    private static Konekcija instance;
    private static Connection konekcija;

    public static Konekcija getInstance() {
        if (instance == null) {
            instance = new Konekcija();
        }
        return instance;
    }

    private Konekcija() {
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

            //drugi unos su default vrednosti ako ne uspe da nadje konfiguraciju
            String url = props.getProperty("db.url", "jdbc:mysql://localhost:3306/ecaffe_db?useSSL=false&serverTimezone=UTC");
            String user = props.getProperty("db.user", "root");
            String pass = props.getProperty("db.pass", "ognjen");

            konekcija = DriverManager.getConnection(url, user, pass);
            konekcija.setAutoCommit(false);

        } catch (SQLException ex) {
            Logger.getLogger(Konekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getKonekcija() {
        return konekcija;
    }

    public void setKonekcija(Connection konekcija) {
        Konekcija.konekcija = konekcija;
    }

}
