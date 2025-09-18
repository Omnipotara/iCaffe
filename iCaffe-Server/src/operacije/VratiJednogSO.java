/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import domen.DomainObject;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omnix
 */
public class VratiJednogSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public T execute(T object) {
        try {
            T result = broker.select(object);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(VratiJednogSO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
}
