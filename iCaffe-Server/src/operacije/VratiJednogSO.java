/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import domen.DomainObject;

/**
 *
 * @author Omnix
 */
public class VratiJednogSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public T execute(T object) throws Exception {
        T result = broker.select(object);
        return result;

    }
}
