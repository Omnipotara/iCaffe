/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import domen.DomainObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Omnix
 */
public class VratiSveSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public List<T> execute(T object) throws Exception {
        List<T> result = broker.selectAll(object);
        return result != null ? result : new ArrayList<>();

    }
}
