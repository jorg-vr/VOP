package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import model.fleet.Fleet;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static database.DAOTestUtil.createFleet;
import static database.DAOTestUtil.removeFleet;
import static org.junit.Assert.fail;

public class FleetParametersTest {

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
    public void ownerField() throws Exception {
        try {
            Address address = new Address("Street", "55", null, "9000", "Country");
            Fleet fleet = createFleet(new Fleet("Name", null, address));
            removeFleet(fleet.getUuid());
            fail("Fleet succesfully created with owner field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
