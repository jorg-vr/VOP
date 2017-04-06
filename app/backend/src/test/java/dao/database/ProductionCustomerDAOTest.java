package dao.database;

import dao.interfaces.*;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import model.identity.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created by tjupo on 03/04/2017.
 */
public class ProductionCustomerDAOTest {
    private static DAOProvider daoProvider;
    private static FunctionDAO functionDAO;
    private static CustomerDAO customerDAO;
    private static AddressDAO addressDAO;
    //private static RoleDAO roleDAO;


    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        customerDAO = daoProvider.getCustomerDAO();
        addressDAO = daoProvider.getAddressDao();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoProvider.close();
    }


    @Test
    public void createGetRemoveTest() throws Exception {
        Customer cust1 = null;
        Address adr1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a customer can be succesfully added to the database
        try {
            adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new address");
        }
        try {
            cust1 = customerDAO.create(new Customer(adr1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        //If a customer was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (cust1 != null) {
                Customer cust2 = customerDAO.get(cust1.getUuid());
                assertEquals("address field not created correctly", cust1.getAddress(), cust2.getAddress());
                assertEquals("email field not created correctly", cust1.getEmail(), cust2.getEmail());
                assertEquals("phoneNumber field not created correctly", cust1.getPhoneNumber(), cust2.getPhoneNumber());
                assertEquals("name field not created correctly", cust1.getName(), cust2.getName());
                assertEquals("btwNumber field not created correctly", cust1.getBtwNumber(), cust2.getBtwNumber());
                assertEquals("bankAccountNumber field not created correctly", cust1.getBankAccountNumber(), cust2.getBankAccountNumber());
                assertEquals("companyType field not created correctly", cust1.getCompanyType(), cust2.getCompanyType());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing customer from the database");
        }
        //If the customer is confirmed to be present in the database, try to remove it
        try {
            if (cust1 != null && present) {
                customerDAO.remove(cust1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a customer from the database");
        }
        //Check if the customer is effectively removed (if create, get and remove tests passed)
        try {
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
        //make sure everything is removed from the database again
        if (adr1 != null) {
            addressDAO.remove(adr1.getUuid());
        }
    }


    @Test
    public void update() throws Exception {
        Address adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Address adr2 = addressDAO.create(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
        Customer cust1 = customerDAO.create(new Customer(adr1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));
        cust1.setAddress(adr2);
        Customer cust2 = new Customer(adr2, "Email@address2.com", "912", "customername 2", "btw124", "123456781", CompanyType.TYPE2);
        cust2.setUuid(cust1.getUuid());
        customerDAO.update(cust2);
        Customer cust3 = customerDAO.get(cust2.getUuid());
        assertEquals("address field not updated correctly", adr2, cust3.getAddress());
        assertEquals("email field not updated correctly", "Email@address2.com", cust3.getEmail());
        assertEquals("phoneNumber field not updated correctly", "912", cust3.getPhoneNumber());
        assertEquals("name field not updated correctly", "customername 2", cust3.getName());
        assertEquals("btwNumber field not updated correctly", "btw124", cust3.getBtwNumber());
        assertEquals("bankAccountNumber field not updated correctly", "123456781", cust3.getBankAccountNumber());

        customerDAO.remove(cust1.getUuid());
        addressDAO.remove(adr1.getUuid());
        addressDAO.remove(adr2.getUuid());
    }
}
