/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package domen;
import java.sql.*;

/**
 *
 * @author Omnix
 * @param <T>
 */
public interface DomainObject<T> {
    
    // Vraća SQL upit za INSERT operaciju
    String getInsertQuery();
    // Popunjava PreparedStatement za INSERT
    void fillInsertStatement(PreparedStatement ps) throws SQLException;
    
    // Vraća SQL upit za UPDATE operaciju
    String getUpdateQuery();
    // Popunjava PreparedStatement za UPDATE
    void fillUpdateStatement(PreparedStatement ps) throws SQLException;
    
    // Vraća SQL upit za DELETE operaciju
    String getDeleteQuery();
    // Popunjava PreparedStatement za DELETE
    void fillDeleteStatement(PreparedStatement ps) throws SQLException;
    
    // Vraća SQL upit za SELECT operaciju (npr. da vrati sve redove)
    String getSelectQuery();
    // Popunjava PreparedStatement za SELECT
    void fillSelectStatement(PreparedStatement ps) throws SQLException;
    
    // Kreira objekat iz ResultSet-a
    T createFromResultSet(ResultSet rs) throws SQLException;
}
