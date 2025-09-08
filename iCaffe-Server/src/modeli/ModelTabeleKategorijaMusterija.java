/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.KategorijaMusterije;

/**
 *
 * @author Omnix
 */
public class ModelTabeleKategorijaMusterija extends AbstractTableModel {

    private List<KategorijaMusterije> listaKategorija = new ArrayList<>();
    private String[] kolone = {"Naziv kategorije", "Popust"};

    public ModelTabeleKategorijaMusterija(List<KategorijaMusterije> listaKategorija) {
        this.listaKategorija = listaKategorija;
    }

    @Override
    public int getRowCount() {
        return listaKategorija.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        KategorijaMusterije km = listaKategorija.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return km.getNaziv();
            case 1:
                return km.getPopust() + "%";
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<KategorijaMusterije> getListaKategorija() {
        return listaKategorija;
    }

    public void setListaKategorija(List<KategorijaMusterije> listaKategorija) {
        this.listaKategorija = listaKategorija;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }

}
