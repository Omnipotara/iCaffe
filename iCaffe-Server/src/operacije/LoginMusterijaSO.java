/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.Konekcija;
import domen.DomainObject;
import model.Musterija;
import model.pomocne.UlogovaniMusterija;

/**
 *
 * @author Omnix
 */
public class LoginMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    public Object execute(Musterija m) throws Exception {
        try {

            Musterija izBaze = broker.select(m);
            if (izBaze == null) {
                izBaze = new Musterija();
                izBaze.setId(-1);
            } else {
                UlogovaniMusterija ulogovan = new UlogovaniMusterija(izBaze.getId(), izBaze.getUsername());
                boolean vecUlogovan = broker.select(ulogovan) != null;
                if (vecUlogovan) {
                    izBaze.setId(-2);
                } else {
                    broker.insert(ulogovan);
                }
            }

            Konekcija.getInstance().getKonekcija().commit();
            return izBaze;
            
        } catch (Exception e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e;
        }
    }
}
