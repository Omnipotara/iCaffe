package testovi;

import model.Musterija;
import operacije.LoginMusterijaSO;
import org.junit.*;
import static org.junit.Assert.*;
import kontroler.Kontroler;

public class LoginMusterijaSOTest {

    private static LoginMusterijaSO loginSO;
    private Musterija rezultat;

    @BeforeClass
    public static void setUpClass() {
        // Ovo se pokreće jednom pre svih testova
        loginSO = new LoginMusterijaSO();
    }

    @AfterClass
    public static void tearDownClass() {
        // Oslobađanje resursa nakon svih testova, ako je potrebno
        loginSO = null;
    }

    @After
    public void ocistiBazu() throws Exception {
        if (rezultat != null) {
            Kontroler.getInstance().logoutMusterija(rezultat);
        }
    }

    @Test
    public void testLoginNePostojiMusterija() throws Exception {

        Musterija nepostojeci = new Musterija();
        nepostojeci.setEmail("fake@email.com");
        nepostojeci.setPassword("pogresno");

        rezultat = (Musterija) loginSO.execute(nepostojeci);

        assertNotNull("Rezultat ne sme biti null", rezultat);
        assertEquals("Ako musterija ne postoji, ID treba da bude -1", -1, rezultat.getId());
    }

    @Test
    public void testLoginUspesan() throws Exception {
        Musterija m = new Musterija();
        m.setEmail("test@test.com"); // mora postojati u bazi
        m.setPassword("123456");     // odgovara password-u

        rezultat = (Musterija) loginSO.execute(m);

        assertNotNull("Rezultat ne sme biti null", rezultat);
        assertTrue("Ako login uspe, ID mora biti pozitivan", rezultat.getId() > 0);

    }

    @Test
    public void testLoginVecUlogovan() throws Exception {
        Musterija m = new Musterija();
        m.setEmail("test@test.com"); // ista musterija kao u prethodnom testu
        m.setPassword("123456");

        // Prvi login - uspešan
        rezultat = (Musterija) loginSO.execute(m);
        // Drugi login - već ulogovan
        Musterija drugiRezultat = (Musterija) loginSO.execute(m);

        assertEquals("Ako se isti korisnik ponovo loginuje, ID treba da bude -2", -2, drugiRezultat.getId());

    }
}
