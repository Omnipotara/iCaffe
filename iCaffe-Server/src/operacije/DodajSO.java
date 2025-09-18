/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

import baza.Konekcija;
import domen.DomainObject;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLException;

/**
 *
 * @author Omnix
 */
public class DodajSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    public Boolean execute(T object) {
        try {
            boolean uspeh = (boolean) broker.insert(object);
            Konekcija.getInstance().getKonekcija().commit();
            return uspeh;
            
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Greska: Duplikat primarnog kljuca!");
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException ex) {
            }
            return false;
            
        } catch (SQLException e) {
            System.out.println("SQL greska: " + e.getMessage());
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException ex) {
            }
            return false;
            
        } catch (Exception e) {
            e.printStackTrace();
            try {
                Konekcija.getInstance().getKonekcija().rollback();
            } catch (SQLException ex) {
            }
            return false;
            
        }
    }
}

