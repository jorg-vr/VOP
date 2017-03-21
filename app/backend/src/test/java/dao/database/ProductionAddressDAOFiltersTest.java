package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.DAOProvider;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 14/03/2017.
 */
public class ProductionAddressDAOFiltersTest {
    private static DAOProvider daoProvider;
    private static AddressDAO addressDAO;
    private static Address a1, a2, a3;

    //TODO: production to false, when local
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("test");
        daoProvider = ProductionProvider.getInstance();
        addressDAO = daoProvider.getAddressDao();

        a1 = addressDAO.create("streettest n1", "59", "town 1", "9999", "country 1");
        a2 = addressDAO.create("streettest n2", "59", "town 2", "9999", "country 2");
        a3 = addressDAO.create("streettest n2", "60", "town 2", "99999", "country 2");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        addressDAO.remove(a1.getUuid());
        addressDAO.remove(a2.getUuid());
        addressDAO.remove(a3.getUuid());
    }

    @Test
    public void byStreet() throws Exception {
        Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byStreet("streettest n1"));
        Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byStreet("streettest n2"));
        assertTrue("byStreet filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
        assertTrue("byStreet filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
    }

    @Test
    public void byStreetNumber() throws Exception {
        Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byStreetNumber("59"));
        Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byStreetNumber("60"));
        assertTrue("byStreet filter doesn't work", c1.contains(a1) && c1.contains(a2) && !c1.contains(a3));
        assertTrue("byStreet filter doesn't work", !c2.contains(a1) && !c2.contains(a2) && c2.contains(a3));
    }

    @Test
    public void byTown() throws Exception {
        Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byTown("town 1"));
        Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byTown("town 2"));
        assertTrue("byStreet filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
        assertTrue("byStreet filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
    }

    @Test
    public void byPostalCode() throws Exception {
        Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byPostalCode("9999"));
        Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byPostalCode("99999"));
        assertTrue("byStreet filter doesn't work", c1.contains(a1) && c1.contains(a2) && !c1.contains(a3));
        assertTrue("byStreet filter doesn't work", !c2.contains(a1) && !c2.contains(a2) && c2.contains(a3));
    }

    @Test
    public void byCountry() throws Exception {
        Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byCountry("country 1"));
        Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byCountry("country 2"));
        assertTrue("byStreet filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
        assertTrue("byStreet filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
    }

    //TODO: test multiple filters in 1 request
}
