package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tjupo on 03/04/2017.
 */
public class ProductionCustomerDAOTest {
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
    }


    @Test
    public void createGetRemoveTest() throws Exception {
        Customer cust1 = null;
        Address adr1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a customer can be succesfully added to the database
        adr1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");

        try {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            cust1 = customerDAO.create(new Customer(adr1, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        //If a customer was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            if (cust1 != null) {
                Customer cust2 = customerDAO.get(cust1.getUuid());
                assertEquals("address field not created correctly", cust1.getAddress(), cust2.getAddress());
                assertEquals("phoneNumber field not created correctly", cust1.getPhoneNumber(), cust2.getPhoneNumber());
                assertEquals("name field not created correctly", cust1.getName(), cust2.getName());
                assertEquals("btwNumber field not created correctly", cust1.getBtwNumber(), cust2.getBtwNumber());
                assertEquals("companyType field not created correctly", cust1.getCompanyType(), cust2.getCompanyType());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing customer from the database");
        }
        //If the customer is confirmed to be present in the database, try to remove it
        try {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            if (cust1 != null && present) {
                customerDAO.remove(cust1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a customer from the database");
        }
        //Check if the customer is effectively removed (if create, get and remove tests passed)
        try {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            if (cust1 != null && present && removed) {
                Customer cust2 = customerDAO.get(cust1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Customer is still in database after removal", cust2);
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
        CustomerDAO customerDAO = daoManager.getCustomerDAO();
        Address adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Address adr2 = addressDAO.create(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
        Customer cust1 = customerDAO.create(new Customer(adr1, "911", "customername 1", "btw123"));
        cust1.setAddress(adr2);
        cust1.setPhoneNumber("912");
        cust1.setName("customername 2");
        cust1.setBtwNumber("btw124");
        customerDAO.update(cust1);
        Customer cust3 = customerDAO.get(cust1.getUuid());
        assertEquals("address field not updated correctly", adr2, cust3.getAddress());
        assertEquals("phoneNumber field not updated correctly", "912", cust3.getPhoneNumber());
        assertEquals("name field not updated correctly", "customername 2", cust3.getName());
        assertEquals("btwNumber field not updated correctly", "btw124", cust3.getBtwNumber());

        customerDAO.remove(cust1.getUuid());
        addressDAO.remove(adr1.getUuid());
        addressDAO.remove(adr2.getUuid());

    }
}
