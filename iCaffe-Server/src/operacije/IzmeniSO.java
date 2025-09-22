package operacije;

import domen.DomainObject;

public class IzmeniSO<T extends DomainObject<T>> extends AbstractSystemOperation<T> {


    @Override
    protected void validate(T object) throws Exception {
        if (object == null) {
            throw new Exception("Objekat za izmenu ne sme biti null.");
        }
    }

    @Override
    protected Object executeOperation(T object) throws Exception {
        return broker.update(object);
    }

   
}
