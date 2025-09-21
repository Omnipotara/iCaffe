package testovi;

import java.util.List;
import model.Musterija;
import org.junit.*;
import static org.junit.Assert.*;
import kontroler.Kontroler;
import model.pomocne.UlogovaniMusterija;

public class VratiSveSOTest {

    private static Musterija m1;
    private static Musterija m2;

    @BeforeClass
    public static void setUpClass() throws Exception {
        // Pre samog testa musterije sa id 1 i 2 se fetchuju iz baze
        m1 = new Musterija();
        m1.setId(1);
        m1 = (Musterija) Kontroler.getInstance().vratiJednog(m1);

        m2 = new Musterija();
        m2.setId(2);
        m2 = (Musterija) Kontroler.getInstance().vratiJednog(m2);

        // Musterije se loguju kako bi mogla da se vrati lista svih ulogovanih
        Kontroler.getInstance().loginMusterija(m1);
        Kontroler.getInstance().loginMusterija(m2);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Ovo se poziva na kraju da bi se ocistila tabela musterija_ulogovani
        Kontroler.getInstance().logoutMusterija(m1);
        Kontroler.getInstance().logoutMusterija(m2);
    }

    @Test
    public void testVratiSveUlogovaneMusterije() throws Exception {
        List<Musterija> ulogovani = Kontroler.getInstance().vratiSve(new UlogovaniMusterija());

        assertNotNull("Lista ne sme biti null", ulogovani);
        assertTrue("Lista mora da sadrži m1", ulogovani.contains(m1));
        assertTrue("Lista mora da sadrži m2", ulogovani.contains(m2));
    }
}