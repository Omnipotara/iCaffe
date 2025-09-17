/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import model.Musterija;
import model.UlogovaniMusterija;

/**
 *
 * @author Omnix
 */
public class LogoutMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    public Object execute(Musterija m) throws Exception {
        // Kreiranej objekat ulogovanog musterije
        UlogovaniMusterija ulogovan = new UlogovaniMusterija(m.getId(), m.getUsername());

        // Brisanje iz baze
        boolean obrisan = broker.delete(ulogovan);

        return obrisan;
    }

}
