/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package niti;

import java.awt.Label;
import java.time.Duration;
import javax.swing.JOptionPane;
import model.Musterija;
import view.KlijentskaForma;

/**
 *
 * @author Omnix
 */
public class MusterijaTimerNit extends Thread {

    private KlijentskaForma kf;
    private Musterija musterija;
    private Label lblVreme;
    private boolean kraj = false;

    public MusterijaTimerNit() {
    }

    public MusterijaTimerNit(KlijentskaForma kf, Musterija musterija) {
        this.kf = kf;
        this.musterija = musterija;
    }

    @Override
    public void run() {
        long vreme = musterija.getPreostaloVreme().getSeconds();
        while (vreme > 0 && !kraj) {
            try {
                Thread.sleep(1000);
                vreme--;
                Duration preostaloVreme = Duration.ofSeconds(vreme);

                long hours = preostaloVreme.toHours();
                long minutes = preostaloVreme.toMinutes() % 60;
                long seconds = preostaloVreme.getSeconds() % 60;

                kf.setTitle("Preostalo vremena: " + hours + "h " + minutes + "min " + seconds + "s");
                
            } catch (InterruptedException e) {
                break;
            }
        }
        if (vreme <= 0) {
            kf.istekloVreme();
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

    public KlijentskaForma getKf() {
        return kf;
    }

    public void setKf(KlijentskaForma kf) {
        this.kf = kf;
    }

    public Label getLblVreme() {
        return lblVreme;
    }

    public void setLblVreme(Label lblVreme) {
        this.lblVreme = lblVreme;
    }

}
