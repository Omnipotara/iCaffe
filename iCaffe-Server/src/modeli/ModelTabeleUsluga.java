/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.KategorijaMusterije;
import model.Usluga;

/**
 *
 * @author Omnix
 */
public class ModelTabeleUsluga extends AbstractTableModel {

    private List<Usluga> listaUsluga = new ArrayList<>();
    private String[] kolone = {"Naziv usluge", "Cena"};

    public ModelTabeleUsluga(List<Usluga> listaUsluga) {
        this.listaUsluga = listaUsluga;
    }

    @Override
    public int getRowCount() {
        return listaUsluga.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Usluga u = listaUsluga.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return u.getNaziv();
            case 1:
                return u.getCena() + " DIN";
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Usluga> getListaUsluga() {
        return listaUsluga;
    }

    public void setListaUsluga(List<Usluga> listaUsluga) {
        this.listaUsluga = listaUsluga;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }
}
