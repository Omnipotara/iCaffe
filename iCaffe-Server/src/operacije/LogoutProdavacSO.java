/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import model.Musterija;
import model.Prodavac;
import model.pomocne.UlogovaniMusterija;

/**
 *
 * @author Omnix
 */
public class LogoutProdavacSO extends AbstractSystemOperation<Prodavac>{

    @Override
    protected void validate(Prodavac p) {
        if (p == null || p.getId() <= 0) {
            throw new IllegalArgumentException("Prodavac mora imati validan ID");
        }
    }

    @Override
    protected Object executeOperation(Prodavac p) throws Exception {
        //UlogovaniMusterija ulogovan = new UlogovaniMusterija(m.getId(), m.getUsername());
        //boolean obrisan = (boolean) broker.delete(ulogovan);
        return true;
    }
    
}
