package database;

import dao.database.ProductionManager;
import dao.database.ProductionProvider;
import dao.interfaces.AddressDAO;
import dao.interfaces.DAOManager;
import dao.interfaces.DataAccessException;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.identity.Address;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

public class FleetParametersTest {

    private static DAOManager daoManager;
    private static Address address;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoManager = ProductionProvider.getInstance().getDaoManager();

        AddressDAO addressDAO = daoManager.getAddressDao();
        address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        AddressDAO addressDAO = daoManager.getAddressDao();
        addressDAO.remove(address.getUuid());
        daoManager.close();
        ProductionProvider.getInstance().close();
    }

    @Test
    public void ownerField() throws Exception {
        Fleet fleet = null;
        try {
            FleetDAO fleetDAO = daoManager.getFleetDAO();
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
