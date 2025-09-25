/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontroler;

import java.util.List;
import view.KlijentskaForma;
import view.LoginForma;

/**
 *
 * @author Omnix
 */
public class Kontroler {
    KlijentskaForma kf;
    LoginForma lf;
    private static Kontroler instance;
    public static Kontroler getInstance(){
        if(instance == null){
            instance = new Kontroler();
        }
        return instance;
    }

    private Kontroler() {
    }

    public KlijentskaForma getKf() {
        return kf;
    }

    public void setKf(KlijentskaForma kf) {
        this.kf = kf;
    }

    public LoginForma getLf() {
        return lf;
    }

    public void setLf(LoginForma lf) {
        this.lf = lf;
    }
    
    
}
