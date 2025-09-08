/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Angazovanje;
import model.Musterija;

/**
 *
 * @author Omnix
 */
public class ModelTabeleMusterija extends AbstractTableModel {

    private List<Musterija> listaMusterija = new ArrayList<>();
    private String[] kolone = {"Username", "Email", "Kategorija", "Popust"};

    public ModelTabeleMusterija(List<Musterija> listaMusterija) {
        this.listaMusterija = listaMusterija;
    }

    @Override
    public int getRowCount() {
        return listaMusterija.size();
    }

    @Override
    public int getColumnCount() {
        return kolone.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Musterija m = listaMusterija.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return m.getUsername();
            case 1:
                return m.getEmail();
            case 2:
                return m.getKategorijaMusterije().getNaziv();
            case 3:
                return "-" + m.getKategorijaMusterije().getPopust() + "%";
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Musterija> getListaAngazovanja() {
        return listaMusterija;
    }

    public void setListaAngazovanja(List<Musterija> listaMusterija) {
        this.listaMusterija = listaMusterija;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }
}
