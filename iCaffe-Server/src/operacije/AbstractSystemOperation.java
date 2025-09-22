package operacije;

import baza.Konekcija;
import baza.DBBroker;
import domen.DomainObject;
import java.sql.*;

public abstract class AbstractSystemOperation<T extends DomainObject<T>> {

    protected DBBroker broker;

    public AbstractSystemOperation() {
        broker = new DBBroker();
    }

    public final Object execute(T object) throws Exception {
        try {
            validate(object);
            Object result = executeOperation(object);
            Konekcija.getInstance().getKonekcija().commit();
            return result;

        } catch (SQLIntegrityConstraintViolationException e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e; // duplicate key

        } catch (IllegalArgumentException e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e; // validacija objekta

        } catch (SQLException e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e; // sve ostale SQL greške

        } catch (Exception e) {
            Konekcija.getInstance().getKonekcija().rollback();
            throw e; // fallback za sve ostalo
        }
        // Svaka SO mora ovo da implementira
    }

    protected abstract void validate(T object) throws Exception;

    protected abstract Object executeOperation(T object) throws Exception;
}
