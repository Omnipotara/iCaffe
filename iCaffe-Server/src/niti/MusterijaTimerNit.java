/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.time.Duration;
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
        while (!kraj) {
            try {
                Duration preostalo = musterija.getPreostaloVreme();

                if (preostalo.getSeconds() > 0) {
                    Thread.sleep(1000); // 1 sekunda

                    // smanji lokalno vreme u objektu
                    musterija.setPreostaloVreme(preostalo.minusSeconds(1));

                    // azurira vrednost u bazi
                    Kontroler.getInstance().smanjiVreme(musterija.getId());

                    //obrisacu posle testiranja
                    System.out.println("Musteriji " + musterija.getUsername()
                            + " ostalo: " + musterija.getPreostaloVreme().getSeconds());
                }

                if (musterija.getPreostaloVreme().isZero() || musterija.getPreostaloVreme().isNegative()) {
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
