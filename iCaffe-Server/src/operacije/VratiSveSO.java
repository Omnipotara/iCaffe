/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import domen.DomainObject;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Omnix
 */
public class VratiSveSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public List<T> execute(T object) {
        try {
            List<T> result = broker.selectAll(object);
            return result != null ? result : new ArrayList<>();
        } catch (SQLException ex) {
            Logger.getLogger(VratiSveSO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();

    }
}
