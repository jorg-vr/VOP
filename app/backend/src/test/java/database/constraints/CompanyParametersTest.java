package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static database.DAOTestUtil.createCustomer;
import static database.DAOTestUtil.removeCustomer;
import static org.junit.Assert.fail;

@Ignore
public class CompanyParametersTest {

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
    public void addressField() throws Exception {
        try {
            Customer customer = createCustomer(new Customer(null, "911", "customername 1", "btw123", CompanyType.CUSTOMER));
            removeCustomer(customer.getUuid());
            fail("Customer succesfully created with address field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void nameField() throws Exception {
        try {
            Address address = new Address("Street", "55", null, "9000", "Country");
            Customer customer = createCustomer(new Customer(address, "911", null, "btw123", CompanyType.CUSTOMER));
            removeCustomer(customer.getUuid());
            fail("Customer succesfully created with name field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void phoneNumberField() throws Exception {
        try {
            Address address = new Address("Street", "55", null, "9000", "Country");
            Customer customer = createCustomer(new Customer(address, null, "customername 1", "btw123", CompanyType.CUSTOMER));
            removeCustomer(customer.getUuid());
            fail("Customer succesfully created with phoneNumber field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void btwNumberField() throws Exception {
        try {
            Address address = new Address("Street", "55", null, "9000", "Country");
            Customer customer = createCustomer(new Customer(address, "911", "customername 1", null, CompanyType.CUSTOMER));
            removeCustomer(customer.getUuid());
            fail("Customer succesfully created with btwNumber field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void typeField() throws Exception {
        try {
            Address address = new Address("Street", "55", null, "9000", "Country");
            Customer customer = createCustomer(new Customer(address, "911", "customername 1", "btw123", null));
            removeCustomer(customer.getUuid());
            fail("Customer succesfully created with type field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
