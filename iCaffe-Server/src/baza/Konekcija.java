/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Omnix
 */
public class Konekcija {
    private static Konekcija instance;
    private static Connection konekcija;
    
    public static Konekcija getInstance(){
        if (instance == null){
            instance = new Konekcija();
        }
        return instance;
    }

    private Konekcija() {
        try {
            String url = "jdbc:mysql://localhost:3306/ecaffe_db";
            konekcija = DriverManager.getConnection(url, "root", "ognjen");
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Konekcija.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public  Connection getKonekcija() {
        return konekcija;
    }

    public  void setKonekcija(Connection konekcija) {
        Konekcija.konekcija = konekcija;
    }
    
    
    
    
    
}
