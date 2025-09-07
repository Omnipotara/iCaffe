/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import kontroler.Kontroler;
import model.Musterija;

/**
 *
 * @author Omnix
 */
public class MusterijaTimerNit extends Thread {

    private Musterija musterija;
    private boolean kraj = false;

    public MusterijaTimerNit() {
    }

    public MusterijaTimerNit(Musterija musterija) {
        this.musterija = musterija;
    }

    @Override
    public void run() {
        long preostalo = musterija.getPreostaloVreme().getSeconds();
        while (!kraj && preostalo >= 0) {
            try {
                if (preostalo > 0) {
                    Thread.sleep(1000); // 1 sekunda
                    preostalo--;

                    // azurira se vrednost u bazi
                    Kontroler.getInstance().smanjiVreme(musterija.getId());

                    System.out.println("Musteriji " + musterija.getUsername() + " ostalo: " + preostalo);
                }

                if (preostalo <= 0) {
                    // automatski logout
                    Kontroler.getInstance().istekloVreme(musterija);
                    kraj = true;
                }
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public Musterija getMusterija() {
        return musterija;
    }

    public void setMusterija(Musterija musterija) {
        this.musterija = musterija;
    }

    public boolean isKraj() {
        return kraj;
    }

    public void setKraj(boolean kraj) {
        this.kraj = kraj;
    }

}
