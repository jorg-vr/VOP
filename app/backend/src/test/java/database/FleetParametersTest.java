package database;

import dao.database.ProductionProvider;
import dao.interfaces.AddressDAO;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Ponti on 10/04/2017.
 */
public class FleetParametersTest {

    private static DAOProvider daoProvider;
    private static Address address;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        try (AddressDAO addressDAO = daoProvider.getAddressDao()) {
            address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (AddressDAO addressDAO = daoProvider.getAddressDao()) {
            addressDAO.remove(address.getUuid());
        }
        daoProvider.close();
    }

    @Test
    public void addressField() throws Exception {
        Customer customer = null;
        try (CustomerDAO customerDAO = daoProvider.getCustomerDAO()) {
            customer = customerDAO.create(new Customer(null, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.CUSTOMER));
            customerDAO.remove(customer.getUuid());
            fail("Customer succesfully created with address field null when an exception was expected");
        } catch (DataAccessException d) {
            if (customer != null) {
                fail("Customer succesfully created with address field null when an exception was expected");
            }
        }
    }
}
