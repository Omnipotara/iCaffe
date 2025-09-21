package testovi;

import model.*;
import operacije.UbaciRacunSO;
import org.junit.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import kontroler.Kontroler;
import org.junit.After;
import static org.junit.Assert.*;

public class UbaciRacunSOTest {

    private List<StavkaRacuna> stavke = new ArrayList<>();
    private Racun racun = new Racun();

    @After
    public void ocistiBazu() throws Exception {
        if (stavke != null) {
            for (StavkaRacuna s : stavke) {
                Kontroler.getInstance().obrisi(s);
            }
        }
        if (racun != null) {
            Kontroler.getInstance().obrisi(racun);
        }
    }

    @Test
    public void testUbaciRacun() throws Exception {

        // Priprema prodavca i musterije
        Prodavac prodavac = new Prodavac();
        prodavac.setId(1);
        prodavac = Kontroler.getInstance().vratiJednog(prodavac);

        Musterija musterija = new Musterija();
        musterija.setId(1);
        musterija = Kontroler.getInstance().vratiJednog(musterija);

        // Stavke racuna
        Usluga usluga1 = new Usluga(1, null, 0.0);
        Usluga usluga2 = new Usluga(2, null, 0.0);

        usluga1 = Kontroler.getInstance().vratiJednog(usluga1);
        usluga2 = Kontroler.getInstance().vratiJednog(usluga2);

        StavkaRacuna s1 = new StavkaRacuna(1, null, 2, 0, 20, usluga1); // 2 x 20
        StavkaRacuna s2 = new StavkaRacuna(2, null, 1, 0, 90, usluga2);  // 1 x 90

        stavke.add(s1);
        stavke.add(s2);

        // Racun
        racun.setDatum(LocalDateTime.now());
        racun.setProdavac(prodavac);
        racun.setMusterija(musterija);

        // Izračunavanje ukupne cene
        double suma = 0;
        for (StavkaRacuna s : stavke) {
            suma += s.getJedinicnaCena() * s.getKolicina();
        }
        double ukupnaCena = suma * (1 - musterija.getKategorijaMusterije().getPopust() / 100.0);
        racun.setUkupnaCena(ukupnaCena);

        // Povezivanje stavki sa racunom
        for (StavkaRacuna s : stavke) {
            s.setRacun(racun);
            s.setCenaStavke(s.getJedinicnaCena() * s.getKolicina());
        }

        // Poziv SO
        UbaciRacunSO so = new UbaciRacunSO(stavke);
        Object rezultat = so.execute(racun);

        assertTrue(rezultat instanceof Integer);
        int racunId = (Integer) rezultat;
        assertTrue(racunId > 0);

        // Provera ukupne cene
        assertEquals(91.0, racun.getUkupnaCena(), 0.001); // 2*20 + 1*90 = 130, minus 30% = 91 i ovo trece 0.001 je tolerancija
    }
}
