package operacije;

import domen.DomainObject;

public class ObrisiSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {

    @Override
    protected void validate(T object) throws Exception {
        if (object == null) {
            throw new IllegalArgumentException("Objekat za brisanje ne sme biti null");
        }
    }

    @Override
    protected Object executeOperation(T object) throws Exception {
        // Vrati true/false da li je uspešno obrisan
        return (boolean) broker.delete(object);
    }
}
