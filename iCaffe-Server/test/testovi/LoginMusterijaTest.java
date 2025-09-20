package testovi;

import model.Musterija;
import operacije.LoginMusterijaSO;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class LoginMusterijaTest {

    private static LoginMusterijaSO loginSO;

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

    @Before
    public void setUp() {
        // Ovo se pokreće pre svakog testa, može da resetuje stanje
    }

    @After
    public void tearDown() {
        // Ovo se pokreće posle svakog testa, može da briše podatke ako treba
    }

    @Test
    public void testLoginNePostojiMusterija() throws Exception {
        Musterija nepostojeci = new Musterija();
        nepostojeci.setEmail("fake@email.com");
        nepostojeci.setPassword("pogresno");

        Musterija rezultat = (Musterija) loginSO.execute(nepostojeci);

        assertNotNull("Rezultat ne sme biti null", rezultat);
        assertEquals("Ako musterija ne postoji, ID treba da bude -1", -1, rezultat.getId());
    }

    @Test
    public void testLoginUspesan() throws Exception {
        Musterija m = new Musterija();
        m.setEmail("test@test.com"); // mora postojati u bazi
        m.setPassword("123456");     // odgovara password-u

        Musterija rezultat = (Musterija) loginSO.execute(m);

        assertNotNull("Rezultat ne sme biti null", rezultat);
        assertTrue("Ako login uspe, ID mora biti pozitivan", rezultat.getId() > 0);
    }

    @Test
    public void testLoginVecUlogovan() throws Exception {
        Musterija m = new Musterija();
        m.setEmail("test@test.com"); // ista musterija kao u prethodnom testu
        m.setPassword("123456");

        // Prvi login - uspešan
        Musterija prvi = (Musterija) loginSO.execute(m);
        // Drugi login - već ulogovan
        Musterija drugi = (Musterija) loginSO.execute(m);

        assertEquals("Ako se isti korisnik ponovo loginuje, ID treba da bude -2", -2, drugi.getId());
    }
}
