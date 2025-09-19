/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Racun;

/**
 *
 * @author Omnix
 */
public class ModelTabeleRacuna extends AbstractTableModel {

    private List<Racun> listaRacuna = new ArrayList<>();
    private String[] kolone = {"Prodavac", "Musterija", "Ukupan iznos", "Datum"};

    public ModelTabeleRacuna(List<Racun> listaRacuna) {
        this.listaRacuna = listaRacuna;
    }

    @Override
    public int getRowCount() {
        return listaRacuna.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Racun r = listaRacuna.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return r.getProdavac().getIme();
            case 1:
                return r.getMusterija().getUsername();
            case 2:
                return r.getUkupnaCena() + " DIN";
            case 3:
                LocalDateTime datum = r.getDatum();
                String s = datum.getDayOfMonth() + "."
                        + datum.getMonthValue() + "."
                        + datum.getYear() + " - "
                        + datum.getHour() + ":"
                        + datum.getMinute() + ":"
                        + datum.getSecond();

                return s;
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Racun> getListaRacuna() {
        return listaRacuna;
    }

    public void setListaRacuna(List<Racun> listaRacuna) {
        this.listaRacuna = listaRacuna;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }

}
