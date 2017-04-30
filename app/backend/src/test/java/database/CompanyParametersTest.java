package database;

import dao.database.ProductionManager;
import dao.interfaces.AddressDAO;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOManager;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class CompanyParametersTest {

    private static DAOManager daoManager;
    private static Address address;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionManager.initializeProvider("unittest");
        daoManager = ProductionManager.getInstance();
        try (AddressDAO addressDAO = daoManager.getAddressDao()) {
            address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (AddressDAO addressDAO = daoManager.getAddressDao()) {
            addressDAO.remove(address.getUuid());
        }
        daoManager.close();
    }

    @Test
    public void addressField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoManager.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(null, "911", "customername 1", "btw123", CompanyType.CUSTOMER));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with address field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with address field null when an exception was expected");
            }
        }
    }

    @Test
    public void nameField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoManager.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(address, "911", null, "btw123", CompanyType.CUSTOMER));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with name field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with name field null when an exception was expected");
            }
        }
    }

    @Test
    public void phoneNumberField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoManager.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(address, null, "customername 1", "btw123", CompanyType.CUSTOMER));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with phoneNumber field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with phoneNumber field null when an exception was expected");
            }
        }
    }

    @Test
    public void btwNumberField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoManager.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(address, "911", "customername 1", null, CompanyType.CUSTOMER));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with btwNumber field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with btwNumber field null when an exception was expected");
            }
        }
    }

    @Test
    public void typeField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoManager.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(address, "911", "customername 1", "btw123", null));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with type field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with type field null when an exception was expected");
            }
        }
    }
}
