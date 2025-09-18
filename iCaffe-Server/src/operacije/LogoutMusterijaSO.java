/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.Konekcija;
import model.Musterija;
import model.pomocne.UlogovaniMusterija;

/**
 *
 * @author Omnix
 */
public class LogoutMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    public Object execute(Musterija m) throws Exception {
        try {

            UlogovaniMusterija ulogovan = new UlogovaniMusterija(m.getId(), m.getUsername());
            boolean obrisan = (boolean) broker.delete(ulogovan);

            Konekcija.getInstance().getKonekcija().commit();
            return obrisan;
        } catch (Exception e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e;
        }
    }

}
