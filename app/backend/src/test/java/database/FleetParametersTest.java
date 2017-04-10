package database;

import dao.database.ProductionProvider;
import dao.interfaces.AddressDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.identity.Address;
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
        Fleet fleet = null;
        try (FleetDAO fleetDAO = daoProvider.getFleetDAO()) {
            fleet = fleetDAO.create(new Fleet("Name", null, address));
            fleetDAO.remove(fleet.getUuid());
            fail("Fleet succesfully created with owner field null when an exception was expected");
        } catch (DataAccessException d) {
            if (fleet != null) {
                fail("Fleet succesfully created with owner field null when an exception was expected");
            }
        }
    }
}
