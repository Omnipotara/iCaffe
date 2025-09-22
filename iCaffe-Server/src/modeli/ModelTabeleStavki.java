/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.StavkaRacuna;

/**
 *
 * @author Omnix
 */
public class ModelTabeleStavki extends AbstractTableModel {

    private List<StavkaRacuna> listaStavki = new ArrayList<>();
    private String[] kolone = {"Naziv", "Kolicina", "Jedinicna cena", "Ukupna cena"};
    
    public ModelTabeleStavki(List<StavkaRacuna> listaStavki){
        this.listaStavki = listaStavki;
    }

    @Override
    public int getRowCount() {
        return listaStavki.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        StavkaRacuna s = listaStavki.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return s.getUsluga().getNaziv();
            case 1:
                return s.getKolicina();
            case 2:
                return s.getUsluga().getCena();
            case 3:
                return s.getCenaStavke();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<StavkaRacuna> getListaStavki() {
        return listaStavki;
    }

    public void setListaStavki(List<StavkaRacuna> listaStavki) {
        this.listaStavki = listaStavki;
    }
    
    

}
