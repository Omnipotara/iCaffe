/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.Konekcija;
import domen.DomainObject;
import java.sql.SQLException;

/**
 *
 * @author Omnix
 */
public class ObrisiSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public Boolean execute(T object) throws Exception {
        try {
            boolean uspeh = (boolean) broker.delete(object);
            Konekcija.getInstance().getKonekcija().commit();
            return uspeh;
        } catch (SQLException e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e;
        }
    }
}
