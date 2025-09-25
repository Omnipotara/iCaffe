package model.pomocne;

import java.io.Serializable;
import java.util.List;
import model.Racun;
import model.StavkaRacuna;

//Wrapper klasa za slanje računa sa stavkama preko mreže Koristi se jer metode
//ubaciRacun i izmeniRacun primaju 2 parametra
public class RacunWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private Racun racun;
    private List<StavkaRacuna> stavke;

    public RacunWrapper() {
    }

    public RacunWrapper(Racun racun, List<StavkaRacuna> stavke) {
        this.racun = racun;
        this.stavke = stavke;
    }

    public Racun getRacun() {
        return racun;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }

    public List<StavkaRacuna> getStavke() {
        return stavke;
    }

    public void setStavke(List<StavkaRacuna> stavke) {
        this.stavke = stavke;
    }

    @Override
    public String toString() {
        return "RacunWrapper{"
                + "racun=" + racun
                + ", stavke=" + (stavke != null ? stavke.size() + " stavki" : "null")
                + '}';
    }
}
