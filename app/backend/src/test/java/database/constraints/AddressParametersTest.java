package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static database.DAOTestUtil.createAddress;
import static database.DAOTestUtil.removeAddress;
import static org.junit.Assert.fail;

@Ignore
public class AddressParametersTest {

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
    public void townField() throws Exception {
        try {
            Address address = createAddress(new Address("Street", "55", null, "9000", "Country"));
            removeAddress(address.getUuid());
            fail("Address succesfully created with town field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Address succesfully created with town field null when an exception was expected");
        }
    }

    @Test
    public void countryField() throws Exception {
        try {
            Address address = createAddress(new Address("Street", "55", "Town", "9000", null));
            removeAddress(address.getUuid());
            fail("Address succesfully created with country field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Address succesfully created with country field null when an exception was expected");
        }
    }

    @Test
    public void streetNumberField() throws Exception {
        try {
            Address address = createAddress(new Address("Street", null, "Town", "9000", "Country"));
            removeAddress(address.getUuid());
            fail("Address succesfully created with streetNumber field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Address succesfully created with streetNumber field null when an exception was expected");
        }
    }

    @Test
    public void postalCodeField() throws Exception {
        try {
            Address address = createAddress(new Address("Street", "55", "Town", null, "Country"));
            removeAddress(address.getUuid());
            fail("Address succesfully created with postalCode field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Address succesfully created with postalCode field null when an exception was expected");
        }
    }

    @Test
    public void streetField() throws Exception {
        try {
            Address address = createAddress(new Address(null, "55", "Town", "9000", "Country"));
            removeAddress(address.getUuid());
            fail("Address succesfully created with street field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Address succesfully created with street field null when an exception was expected");
        }
    }
}
