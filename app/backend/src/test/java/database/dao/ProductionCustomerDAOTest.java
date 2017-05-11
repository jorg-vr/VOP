package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import database.DAOTestUtil;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by tjupo on 03/04/2017.
 */
public class ProductionCustomerDAOTest {


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
        Customer cust1 = null;
        Address adr1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");

        //test if a customer can be succesfully added to the database
        try {
            cust1 = DAOTestUtil.createCustomer(new Customer(adr1, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new customer");
        }
        //If a customer was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            Customer cust2 = DAOTestUtil.getCustomer(cust1.getUuid());
            assertEquals("address field not created correctly", cust1.getAddress(), cust2.getAddress());
            assertEquals("phoneNumber field not created correctly", cust1.getPhoneNumber(), cust2.getPhoneNumber());
            assertEquals("name field not created correctly", cust1.getName(), cust2.getName());
            assertEquals("btwNumber field not created correctly", cust1.getBtwNumber(), cust2.getBtwNumber());
            assertEquals("companyType field not created correctly", cust1.getCompanyType(), cust2.getCompanyType());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing customer from the database");
        }
        //If the customer is confirmed to be present in the database, try to remove it
        try {
            DAOTestUtil.removeCustomer(cust1.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove a customer from the database");
        }
        //Check if the customer is effectively removed (if create, get and remove tests passed)
        try {
            DAOTestUtil.getCustomer(cust1.getUuid());
            //If get was successfull the test fails
            fail("Customer is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }
    }


    @Test
    public void update() throws Exception {
        Address adr1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Address adr2 = DAOTestUtil.createAddress(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
        Customer cust1 = DAOTestUtil.createCustomer(new Customer(adr1, "911", "customername 1", "btw123"));
        cust1.setAddress(adr2);
        cust1.setPhoneNumber("912");
        cust1.setName("customername 2");
        cust1.setBtwNumber("btw124");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCustomerDAO().update(cust1);
        }

        Customer cust3 = DAOTestUtil.getCustomer(cust1.getUuid());
        assertEquals("address field not updated correctly", cust1.getAddress(), cust3.getAddress());
        assertEquals("phoneNumber field not updated correctly", "912", cust3.getPhoneNumber());
        assertEquals("name field not updated correctly", "customername 2", cust3.getName());
        assertEquals("btwNumber field not updated correctly", "btw124", cust3.getBtwNumber());

        DAOTestUtil.removeCustomer(cust1.getUuid());
    }
}
