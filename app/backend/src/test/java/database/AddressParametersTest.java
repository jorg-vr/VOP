package database;

import dao.database.ProductionManager;
import dao.database.ProductionProvider;
import dao.interfaces.AddressDAO;
import dao.interfaces.DAOManager;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class AddressParametersTest {

    private static DAOManager daoManager;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoManager = ProductionProvider.getInstance().getDaoManager();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoManager.close();
        ProductionProvider.getInstance().close();
    }

    @Test
    public void townField() throws Exception {
        Address address = null;
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            address = addressDAO.create(new Address("Street", "55", null, "9000", "Country"));
            addressDAO.remove(address.getUuid());
            fail("Address succesfully created with town field null when an exception was expected");
        } catch (DataAccessException d) {
            if (address != null) {
                fail("Address succesfully created with town field null when an exception was expected");
            }
        }
    }

    @Test
    public void countryField() throws Exception {
        Address address = null;
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            address = addressDAO.create(new Address("Street", "55", "Town", "9000", null));
            addressDAO.remove(address.getUuid());
            fail("Address succesfully created with country field null when an exception was expected");
        } catch (DataAccessException d) {
            if (address != null) {
                fail("Address succesfully created with country field null when an exception was expected");
            }
        }
    }

    @Test
    public void streetNumberField() throws Exception {
        Address address = null;
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            address = addressDAO.create(new Address("Street", null, "Town", "9000", "Country"));
            addressDAO.remove(address.getUuid());
            fail("Address succesfully created with streetNumber field null when an exception was expected");
        } catch (DataAccessException d) {
            if (address != null) {
                fail("Address succesfully created with streetNumber field null when an exception was expected");
            }
        }
    }

    @Test
    public void postalCodeField() throws Exception {
        Address address = null;
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            address = addressDAO.create(new Address("Street", "55", "Town", null, "Country"));
            addressDAO.remove(address.getUuid());
            fail("Address succesfully created with postalCode field null when an exception was expected");
        } catch (DataAccessException d) {
            if (address != null) {
                fail("Address succesfully created with postalCode field null when an exception was expected");
            }
        }
    }

    @Test
    public void streetField() throws Exception {
        Address address = null;
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            address = addressDAO.create(new Address(null, "55", "Town", "9000", "Country"));
            addressDAO.remove(address.getUuid());
            fail("Address succesfully created with street field null when an exception was expected");
        } catch (DataAccessException d) {
            if (address != null) {
                fail("Address succesfully created with street field null when an exception was expected");
            }
        }
    }
}
