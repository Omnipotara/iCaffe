///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package niti;
//
//import java.time.Duration;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.swing.JTextField;
//
///**
// *
// * @author Omnix
// */
////Ova nit je napravljena kako bi u "detaljno" formi Musterije preostalo vreme moglo da se update konstantno
//public class MusterijaDetaljnoNit extends Thread {
//
//    private JTextField txtVreme;
//    private MusterijaTimerNit mtn;
//    private boolean kraj = false;
//
//    public MusterijaDetaljnoNit() {
//    }
//
//    public MusterijaDetaljnoNit(JTextField txtVreme, MusterijaTimerNit mtn) {
//        this.txtVreme = txtVreme;
//        this.mtn = mtn;
//    }
//
//    @Override
//    public void run() {
//        while (!kraj) {            
//            Duration preostaloVreme = mtn.getPreostalo();
//            long sati = preostaloVreme.toHours();
//            long minute = preostaloVreme.toMinutes() % 60;
//            long sekunde = preostaloVreme.toSeconds() % 60;
//            String stringVreme = sati + "h " + minute + "min " + sekunde + "s";
//            txtVreme.setText(stringVreme);
//            try {
//                sleep(1000);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(MusterijaDetaljnoNit.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
//
//    public JTextField getTxtVreme() {
//        return txtVreme;
//    }
//
//    public void setTxtVreme(JTextField txtVreme) {
//        this.txtVreme = txtVreme;
//    }
//
//    public MusterijaTimerNit getMtn() {
//        return mtn;
//    }
//
//    public void setMtn(MusterijaTimerNit mtn) {
//        this.mtn = mtn;
//    }
//
//    public boolean isKraj() {
//        return kraj;
//    }
//
//    public void setKraj(boolean kraj) {
//        this.kraj = kraj;
//    }
//
//}
