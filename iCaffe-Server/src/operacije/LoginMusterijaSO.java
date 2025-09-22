package operacije;

import domen.DomainObject;
import java.time.Duration;
import model.Musterija;
import model.pomocne.UlogovaniMusterija;

public class LoginMusterijaSO extends AbstractSystemOperation<Musterija> {

    @Override
    protected void validate(Musterija m) {
        if (m == null || m.getEmail() == null || m.getPassword() == null) {
            throw new IllegalArgumentException("Email i password ne smeju biti null");
        }
    }

    @Override
    protected Object executeOperation(Musterija m) throws Exception {
        Musterija izBaze = broker.select(m);

        if (izBaze == null) {
            izBaze = new Musterija();
            izBaze.setId(-1);
            izBaze.setPreostaloVreme(Duration.ZERO);
        } else {
            // Provera da li je vec ulogovan
            UlogovaniMusterija ulogovan = new UlogovaniMusterija(izBaze.getId(), izBaze.getUsername());
            boolean vecUlogovan = broker.select(ulogovan) != null;

            if (vecUlogovan) {
                izBaze.setId(-2);
            } else {
                broker.insert(ulogovan);
            }
        }

        return izBaze;
    }
}
