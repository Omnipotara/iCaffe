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
    private String[] kolone = {"Username", "Kategorija", "Online", "Preostalo vreme"};

    public ModelTabeleMusterija(List<Musterija> listaMusterija, List<Musterija> listaOnlineMusterija) {
     
//        for (int i = 0; i < listaMusterija.size(); i++) {
//            Object obj = listaMusterija.get(i);
//            System.out.println("Element " + i + ": " + obj.getClass().getSimpleName());
//            if (!(obj instanceof Musterija)) {
//                System.out.println("PRONAŠAO PROBLEM! Element " + i + " nije Musterija!");
//            }
//        }

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
                return m.getKategorijaMusterije().getNaziv();
            case 2:
                if (listaOnlineMusterija.contains(m)) {
                    return "DA";
                } else {
                    return "NE";
                }
            case 3:
                long vreme = m.getPreostaloVreme().getSeconds();
                if (vreme >= 3600) {
                    return "> 1 sat";
                }
                if (vreme < 3600 && vreme >= 1800) {
                    return "< 1 sat";
                }
                if (vreme < 1800 && vreme >= 600) {
                    return "< 30 minuta";
                }
                if (vreme < 600 && vreme >= 1) {
                    return "< 10 minuta";
                }
                if (vreme < 1) {
                    return "ISTEKLO";
                }
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
