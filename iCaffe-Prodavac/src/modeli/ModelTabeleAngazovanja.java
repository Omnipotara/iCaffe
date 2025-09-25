/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Angazovanje;

/**
 *
 * @author Omnix
 */
public class ModelTabeleAngazovanja extends AbstractTableModel {

    private List<Angazovanje> listaAngazovanja = new ArrayList<>();
    private String[] kolone = {"Ime", "Prezime", "Smena", "Datum"};

    public ModelTabeleAngazovanja(List<Angazovanje> listaAngazovanja) {
        this.listaAngazovanja = listaAngazovanja;
    }

    @Override
    public int getRowCount() {
        return listaAngazovanja.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Angazovanje a = listaAngazovanja.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return a.getProdavac().getIme();
            case 1:
                return a.getProdavac().getPrezime();
            case 2:
                return a.getTermin().getSmena().toString();
            case 3:
                return a.getDatum();
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Angazovanje> getListaAngazovanja() {
        return listaAngazovanja;
    }

    public void setListaAngazovanja(List<Angazovanje> listaAngazovanja) {
        this.listaAngazovanja = listaAngazovanja;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }

}
