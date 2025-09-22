package operacije;

import domen.DomainObject;
import model.Musterija;
import model.pomocne.UlogovaniMusterija;

public class LogoutMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    protected void validate(Musterija m) {
        if (m == null || m.getId() <= 0) {
            throw new IllegalArgumentException("Musterija mora imati validan ID");
        }
    }

    @Override
    protected Object executeOperation(Musterija m) throws Exception {
        UlogovaniMusterija ulogovan = new UlogovaniMusterija(m.getId(), m.getUsername());
        boolean obrisan = (boolean) broker.delete(ulogovan);
        return obrisan;
    }
}
