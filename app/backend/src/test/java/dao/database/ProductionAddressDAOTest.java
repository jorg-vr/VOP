package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


public class ProductionAddressDAOTest {
    private static DAOManager daoManager;
    private static DAOProvider daoProvider;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        daoManager = daoProvider.getDaoManager();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoManager.close();
        daoProvider.close();
    }

    @Test
    public void createGetRemoveTest() throws Exception {
        Address a1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a address can be succesfully added to the database
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new address");
        }
        //If a address was succesfully added, test if it can be retrieved succesfully
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            if (a1 != null) {
                Address a2 = addressDAO.get(a1.getUuid());
                assertEquals("street field not created correctly", a1.getStreet(), a2.getStreet());
                assertEquals("streetNumber field not created correctly", a1.getStreetNumber(), a2.getStreetNumber());
                assertEquals("town field not created correctly", a1.getTown(), a2.getTown());
                assertEquals("postalCode field not created correctly", a1.getPostalCode(), a2.getPostalCode());
                assertEquals("country field not created correctly", a1.getCountry(), a2.getCountry());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing address from the database");
        }
        //If the address is confirmed to be present in the database, try to remove it
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            if (a1 != null && present) {
                addressDAO.remove(a1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an address from the database");
        }
        //Check if the address is effectively removed (if create, get and remove tests passed)
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            if (a1 != null && present && removed) {
                Address t2 = addressDAO.get(a1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Address is still in database after removal", t2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
    }


    @Test
    public void update() throws Exception {
        AddressDAO addressDAO = daoManager.getAddressDao();
        Address a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Address a2 = new Address("streettest n2", "60", "town 2", "99999", "country 2");
        a1.setStreet("streettest n2");
        a1.setStreet("60");
        a1.setStreet("town 2");
        a1.setStreet("99999");
        a1.setStreet("country 2");
        a2.setUuid(a1.getUuid());
        addressDAO.update(a2);
        Address a3 = addressDAO.get(a1.getUuid());
        assertEquals("street field not updated correctly", "streettest n2", a3.getStreet());
        assertEquals("streetNumber field not updated correctly", "60", a3.getStreetNumber());
        assertEquals("town field not updated correctly", "town 2", a3.getTown());
        assertEquals("postalCode field not updated correctly", "99999", a3.getPostalCode());
        assertEquals("country field not updated correctly", "country 2", a3.getCountry());

        addressDAO.remove(a1.getUuid());

    }
}
