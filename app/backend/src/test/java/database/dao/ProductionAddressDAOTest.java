package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import database.DAOTestUtil;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ProductionAddressDAOTest {

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        ProductionProvider.getInstance().close();
    }

    @Test
    public void createGetRemoveTest() throws Exception {
        Address a1 = null;
        //test if a address can be succesfully added to the database
        try {
            a1 = DAOTestUtil.createAddress(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new address");
        }
        //If a address was succesfully added, test if it can be retrieved succesfully
        try {
            Address a2 = DAOTestUtil.getAddress(a1.getUuid());
            assertEquals("street field not created correctly", a1.getStreet(), a2.getStreet());
            assertEquals("streetNumber field not created correctly", a1.getStreetNumber(), a2.getStreetNumber());
            assertEquals("town field not created correctly", a1.getTown(), a2.getTown());
            assertEquals("postalCode field not created correctly", a1.getPostalCode(), a2.getPostalCode());
            assertEquals("country field not created correctly", a1.getCountry(), a2.getCountry());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing address from the database");
        }
        //If the address is confirmed to be present in the database, try to remove it
        try {
            DAOTestUtil.removeAddress(a1.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove an address from the database");
        }
        //Check if the address is effectively removed (if create, get and remove tests passed)
        try {
            DAOTestUtil.getAddress(a1.getUuid());
            //If get was successfull the test fails
            fail("Address is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }
    }


    @Test
    public void update() throws Exception {
        Address a1 = DAOTestUtil.createAddress(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        a1.setStreet("streettest n2");
        a1.setStreetNumber("60");
        a1.setTown("town 2");
        a1.setPostalCode("99999");
        a1.setCountry("country 2");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getAddressDao().update(a1);
        }
        Address a3 = DAOTestUtil.getAddress(a1.getUuid());
        assertEquals("street field not updated correctly", "streettest n2", a3.getStreet());
        assertEquals("streetNumber field not updated correctly", "60", a3.getStreetNumber());
        assertEquals("town field not updated correctly", "town 2", a3.getTown());
        assertEquals("postalCode field not updated correctly", "99999", a3.getPostalCode());
        assertEquals("country field not updated correctly", "country 2", a3.getCountry());

        DAOTestUtil.removeAddress(a1.getUuid());
    }
}
