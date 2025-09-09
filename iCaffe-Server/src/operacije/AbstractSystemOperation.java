/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.DBBroker;
import domen.DomainObject;

/**
 *
 * @author Omnix
 */
public abstract class AbstractSystemOperation<T extends DomainObject<T>> {

    protected DBBroker broker;

    public AbstractSystemOperation() {
        broker = new DBBroker();
    }

    public abstract Object execute(T object) throws Exception;
}
