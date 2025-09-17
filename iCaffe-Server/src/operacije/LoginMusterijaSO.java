/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import domen.DomainObject;
import model.Musterija;
import model.UlogovaniMusterija;

/**
 *
 * @author Omnix
 */
public class LoginMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    public Object execute(Musterija m) throws Exception {
        // Uzima musteriju iz baze
        Musterija izBaze = broker.select(m);

        if (izBaze == null) {
            // musterija ne postoji
            izBaze = new Musterija();
            izBaze.setId(-1);
            return izBaze;
        }

        // Provera da li je vec ulogovan
        UlogovaniMusterija ulogovan = new UlogovaniMusterija(izBaze.getId(), izBaze.getUsername());
        
        boolean vecUlogovan = broker.select(ulogovan) != null;

        if (vecUlogovan) {
            izBaze.setId(-2); // vec ulogovan
        } else {
            broker.insert(ulogovan); // zabelezi kao ulogovanog
        }

        return izBaze;
    }
}
