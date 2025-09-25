/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import model.Prodavac;


/**
 *
 * @author Omnix
 */
public class LoginProdavacSO extends AbstractSystemOperation<Prodavac>{

    @Override
    protected void validate(Prodavac p) {
        if (p == null || p.getUsername() == null || p.getPassword() == null) {
            throw new IllegalArgumentException("Username i password ne smeju biti null");
        }
    }

    @Override
    protected Object executeOperation(Prodavac p) throws Exception {
        Prodavac izBaze = broker.select(p);

        if (izBaze == null) {
            izBaze = new Prodavac();
            izBaze.setId(-1);
        }
//        else {
//            // Provera da li je vec ulogovan
//            UlogovaniMusterija ulogovan = new UlogovaniMusterija(izBaze.getId(), izBaze.getUsername());
//            boolean vecUlogovan = broker.select(ulogovan) != null;
//
//            if (vecUlogovan) {
//                izBaze.setId(-2);
//            } else {
//                broker.insert(ulogovan);
//            }
//        }

        return izBaze;
    }
    
}
