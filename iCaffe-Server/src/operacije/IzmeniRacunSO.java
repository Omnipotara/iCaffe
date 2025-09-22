package operacije;

import model.Racun;
import model.StavkaRacuna;
import java.sql.SQLException;
import java.util.List;

public class IzmeniRacunSO extends AbstractSystemOperation<Racun> {

    private List<StavkaRacuna> noveStavke;

    public IzmeniRacunSO(List<StavkaRacuna> noveStavke) {
        this.noveStavke = noveStavke;
    }

    @Override
    protected void validate(Racun racun) throws Exception {
        if (racun == null) {
            throw new IllegalArgumentException("Racun ne sme biti null");
        }
        if (racun.getId() <= 0) {
            throw new IllegalArgumentException("Nevalidan ID racuna");
        }
        if (noveStavke == null || noveStavke.isEmpty()) {
            throw new IllegalArgumentException("Racun mora imati barem jednu stavku");
        }
        for (StavkaRacuna s : noveStavke) {
            if (s.getJedinicnaCena() < 0 || s.getKolicina() <= 0) {
                throw new IllegalArgumentException("Nevalidna stavka racuna");
            }
        }
    }

    @Override
    protected Object executeOperation(Racun racun) throws Exception {

        // 1. Fetchuju se sve stare stavke za prosledjeni racun
        StavkaRacuna primerStavke = new StavkaRacuna();
        primerStavke.setRacun(racun);
        List<StavkaRacuna> stareStavke = broker.selectAll(primerStavke);

        // 2. Brisu se sve stare stavke iz baze
        for (StavkaRacuna s : stareStavke) {
            broker.delete(s);
        }

        // 3. Cena racuna se updateuje
        boolean uspeh = (boolean) broker.update(racun);
        if (!uspeh) {
            throw new SQLException("Racun nije izmenjen");
        }

        // 4. Ubacuju se nove stavke
        int rb = 0;
        for (StavkaRacuna s : noveStavke) {
            rb++;
            s.setRb(rb);
            s.setRacun(racun);
            broker.insert(s);
        }

        return true; // Ako do ovde nsita ne pukne onda vraca true
    }
}
