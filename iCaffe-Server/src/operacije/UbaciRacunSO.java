/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.Konekcija;
import java.sql.SQLException;
import java.util.List;
import model.Racun;
import model.StavkaRacuna;

/**
 *
 * @author Omnix
 */
public class UbaciRacunSO extends AbstractSystemOperation<Racun> {

    private List<StavkaRacuna> stavke;

    public UbaciRacunSO(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    @Override
    public Object execute(Racun racun) {
        try {

            // Ubaci racun
            int racunId = (int) broker.insert(racun);
            racun.setId(racunId);

            // Ubaci stavke
            for (StavkaRacuna s : stavke) {
                s.setRacun(racun);
                broker.insert(s);
            }

            Konekcija.getInstance().getKonekcija().commit();
            return racunId;

        } catch (Exception ex) {
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException e) {
            }
            ex.printStackTrace();
            return -1;
        }
    }
}
