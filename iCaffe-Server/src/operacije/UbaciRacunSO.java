package operacije;

import domen.DomainObject;
import baza.Konekcija;
import model.Racun;
import model.StavkaRacuna;
import java.sql.SQLException;
import java.util.List;

public class UbaciRacunSO extends AbstractSystemOperation<Racun> {

    private List<StavkaRacuna> stavke;

    public UbaciRacunSO(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    @Override
    protected void validate(Racun racun) throws Exception {
        if (racun == null) {
            throw new IllegalArgumentException("Racun ne sme biti null");
        }
        if (stavke == null || stavke.isEmpty()) {
            throw new IllegalArgumentException("Racun mora imati barem jednu stavku");
        }
        for (StavkaRacuna s : stavke) {
            if (s.getJedinicnaCena() < 0 || s.getKolicina() <= 0) {
                throw new IllegalArgumentException("Nevalidna stavka racuna");
            }
        }
    }

    @Override
    protected Object executeOperation(Racun racun) throws Exception {

        int rb = 0;
        
        // Ubaci racun
        int racunId = (int) broker.insert(racun);
        racun.setId(racunId);

        // Ubaci stavke
        for (StavkaRacuna s : stavke) {
            rb++;
            s.setRb(rb);
            s.setRacun(racun);
            broker.insert(s);
        }

        return racunId;
    }
}
