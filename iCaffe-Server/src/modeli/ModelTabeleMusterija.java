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
    private List<Musterija> listaOnlineMusterija = new ArrayList<>();
    private String[] kolone = {"Username", "Email", "Kategorija", "Popust", "Online", "Preostalo vreme"};

    public ModelTabeleMusterija(List<Musterija> listaMusterija, List<Musterija> listaOnlineMusterija) {
        this.listaMusterija = listaMusterija;
        this.listaOnlineMusterija = listaOnlineMusterija;
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
                return m.getKategorijaMusterije().getPopust() + "%";
            case 4:
                if (listaOnlineMusterija.contains(m)){
                    return "DA";
                } else {
                    return "NE";
                }
            case 5:
                long vreme = m.getPreostaloVreme().getSeconds();
                if (vreme >= 3600 ) return "> 1 sat";
                if (vreme < 3600 && vreme >= 1800) return "< 1 sat";
                if (vreme < 1800 && vreme >= 600) return "< 30 minuta";
                if (vreme < 600) return "< 10 minuta";
            default:
                return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        return kolone[column];
    }

    public List<Musterija> getListaMusterija() {
        return listaMusterija;
    }

    public List<Musterija> getListaOnlineMusterija() {
        return listaOnlineMusterija;
    }

    public void setListaOnlineMusterija(List<Musterija> listaOnlineMusterija) {
        this.listaOnlineMusterija = listaOnlineMusterija;
    }

    public void setListaMusterija(List<Musterija> listaMusterija) {
        this.listaMusterija = listaMusterija;
    }

    public String[] getKolone() {
        return kolone;
    }

    public void setKolone(String[] kolone) {
        this.kolone = kolone;
    }
}
