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

        // 2. Cena racuna se updateuje
        boolean uspeh = (boolean) broker.update(racun);
        if (!uspeh) {
            throw new SQLException("Racun nije izmenjen");
        }

        // 3. Obrada stavki
        // 3.1 Brisu se one koje vise ne postoje
        for (StavkaRacuna stara : stareStavke) {
            boolean postoji = false;
            for (StavkaRacuna nova : noveStavke) {
                if (stara.getUsluga().getId() == nova.getUsluga().getId()) {
                    postoji = true;
                    break;
                }
            }
            if (!postoji) {
                broker.delete(stara);
            }
        }

        // 3.2 Dodavanje novih i update postojećih
        int rb = 0;

        if (!stareStavke.isEmpty()) {
            StavkaRacuna poslednja = stareStavke.get(stareStavke.size() - 1);
            rb = poslednja.getRb();
        }

        for (StavkaRacuna nova : noveStavke) {
            
            nova.setRacun(racun);

            StavkaRacuna postojeca = null;
            for (StavkaRacuna stara : stareStavke) {
                if (stara.getUsluga().getId() == nova.getUsluga().getId()) {
                    postojeca = stara;
                    break;
                }
            }

            if (postojeca == null) {
                // nema je u starim (NOVA STAVKA)
                rb++;
                nova.setRb(rb);
                broker.insert(nova);
            } else if (postojeca.isDifferent(nova)) {
                // ima je u starim (STARA STAVKA)
                broker.update(nova);
            }
        }

        return true;
    }
}
