package database.dao;
//ProductionManager.initializeProvider("test");

import dao.database.ProductionProvider;
import dao.interfaces.AddressDAO;
import dao.interfaces.DAOManager;
import database.DAOTestUtil;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertTrue;


public class ProductionAddressDAOFiltersTest {

    private static Address a1, a2, a3;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {

        ProductionProvider.initializeProvider("unittest");

        a1 = DAOTestUtil.createAddress(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        a2 = DAOTestUtil.createAddress(new Address("streettest n2", "59", "town 2", "9999", "country 2"));
        a3 = DAOTestUtil.createAddress(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {

        DAOTestUtil.removeAddress(a1.getUuid());
        DAOTestUtil.removeAddress(a2.getUuid());
        DAOTestUtil.removeAddress(a3.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void byStreet() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            AddressDAO addressDAO = daoManager.getAddressDao();
            Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byStreet("streettest n1"));
            Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byStreet("streettest n2"));
            assertTrue("byStreet filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
            assertTrue("byStreet filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
        }
    }

    @Test
    public void byStreetNumber() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            AddressDAO addressDAO = daoManager.getAddressDao();
            Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byStreetNumber("59"));
            Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byStreetNumber("60"));
            assertTrue("byStreetNumber filter doesn't work", c1.contains(a1) && c1.contains(a2) && !c1.contains(a3));
            assertTrue("byStreetNumber filter doesn't work", !c2.contains(a1) && !c2.contains(a2) && c2.contains(a3));
        }
    }

    @Test
    public void byTown() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            AddressDAO addressDAO = daoManager.getAddressDao();
            Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byTown("town 1"));
            Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byTown("town 2"));
            assertTrue("byTown filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
            assertTrue("byTown filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
        }
    }

    @Test
    public void byPostalCode() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            AddressDAO addressDAO = daoManager.getAddressDao();
            Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byPostalCode("9999"));
            Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byPostalCode("99999"));
            assertTrue("byPostalCode filter doesn't work", c1.contains(a1) && c1.contains(a2) && c1.contains(a3));
            assertTrue("byPostalCode filter doesn't work", !c2.contains(a1) && !c2.contains(a2) && c2.contains(a3));
        }
    }

    @Test
    public void byCountry() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            AddressDAO addressDAO = daoManager.getAddressDao();
            Collection<Address> c1 = addressDAO.listFiltered(addressDAO.byCountry("country 1"));
            Collection<Address> c2 = addressDAO.listFiltered(addressDAO.byCountry("country 2"));
            assertTrue("byCountry filter doesn't work", c1.contains(a1) && !c1.contains(a2) && !c1.contains(a3));
            assertTrue("byCountry filter doesn't work", !c2.contains(a1) && c2.contains(a2) && c2.contains(a3));
        }
    }

    //TODO: test multiple filters in 1 request
}
