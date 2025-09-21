package testovi;

import java.util.List;
import model.Musterija;
import org.junit.*;
import static org.junit.Assert.*;
import kontroler.Kontroler;
import model.pomocne.UlogovaniMusterija;

public class LogoutMusterijaSOTest {

    private static Musterija musterija;

    @BeforeClass
    public static void setUpClass() throws Exception {
        musterija = new Musterija();
        musterija.setId(1);
        musterija = (Musterija) Kontroler.getInstance().vratiJednog(musterija);

        // Mora prvo da se uloguje da bi ga test izlogovao
        Kontroler.getInstance().loginMusterija(musterija);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Ovo je da sigurno ukloni iz musterija_ulogovani ako tokom testa nesto ne bude ok
        Kontroler.getInstance().logoutMusterija(musterija);
    }

    @Test
    public void testLogoutUspesan() throws Exception {
        boolean rezultat = Kontroler.getInstance().logoutMusterija(musterija);

        assertTrue("Musterija treba da bude uspešno odlogovana", rezultat);
        
    }
}
