package dao.database;

import dao.interfaces.DAOProvider;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.VehicleType;
import org.junit.*;

import static org.junit.Assert.*;

@Ignore
public class ProductionVehicleTypeDAOTest {
    private static DAOProvider daoProvider;
    private static VehicleTypeDAO vehicleTypeDAO;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        vehicleTypeDAO.close();
        daoProvider.close();
    }

    @Test
    public void createGetRemoveTest() throws Exception {
        VehicleType t1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a vehicle can be succesfully added to the database
        try {
            t1 = vehicleTypeDAO.create(new VehicleType("type 14", 6.7));
        } catch (Exception e) {
            fail("Failed trying to create a new vehicleType");
        }
        //If a vehicleType was succesfully added, test if it can be retrieved succesfully
        try {
            if (t1 != null) {
                VehicleType t2 = vehicleTypeDAO.get(t1.getUuid());
                assertEquals("type field not equal", t1.getType(), t2.getType());
                assertTrue("tax field not equal", t1.getTax() == t2.getTax());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing vehicleType from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {
            if (t1 != null && present) {
                vehicleTypeDAO.remove(t1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a vehicleType from the database");
        }
        //Check if the vehicleType is effectively removed (if create, get and remove tests passed)
        try {
            if (t1 != null && present && removed) {
                VehicleType t2 = vehicleTypeDAO.get(t1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("VehicleType is still in database after removal", t2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
    }


    @Test
    public void update() throws Exception {
        VehicleType t1 = vehicleTypeDAO.create(new VehicleType("type 14", 6.7));
        VehicleType t2 = vehicleTypeDAO.update(new VehicleType(t1.getUuid(), "type 15", 6.8));
        VehicleType t3 = vehicleTypeDAO.get(t1.getUuid());
        assertEquals("returned vehicleType object is not updated correctly", "type 15", t3.getType());
        assertTrue("returned vehicleType object is not updated correctly", 6.8 == t3.getTax());

        vehicleTypeDAO.remove(t1.getUuid());
    }

}