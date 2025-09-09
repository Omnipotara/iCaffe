/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije;

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
            return broker.insert(object);
        } catch (SQLIntegrityConstraintViolationException e) {
            // greska: duplikat primarnog kljuca
            System.out.println("Greska: Duplikat primarnog kljuca!");
            return false;
        } catch (SQLException e) {
            // ostale SQL greske
            System.out.println("SQL greska: " + e.getMessage());
            return false;
        } catch (Exception e) {
            // sve ostalo
            e.printStackTrace();
            return false;
        }
    }
}
