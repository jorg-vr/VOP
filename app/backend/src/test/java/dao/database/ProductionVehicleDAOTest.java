package dao.database;

import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.junit.*;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * Created by tjupo on 13/03/2017.
 */
public class ProductionVehicleDAOTest {

    private static ProductionProvider daoProvider;
    private ProductionVehicleDAO vehicleDao;
    private ProductionVehicleTypeDAO vehicleTypeDAO;
    private VehicleType t1;

    @BeforeClass
    public static void initProvider() {
        ProductionProvider.initializeProvider(false);
        daoProvider = (ProductionProvider) ProductionProvider.getInstance();
    }

    @AfterClass
    public static void closeProvider() {
        daoProvider.close();
    }

    @Before
    public void setUp() throws Exception {
        vehicleDao = (ProductionVehicleDAO) daoProvider.getVehicleDAO();
        vehicleTypeDAO = (ProductionVehicleTypeDAO) daoProvider.getVehicleTypeDAO();
        t1 = vehicleTypeDAO.create("type 1", 2.5);
    }

    @After
    public void tearDown() throws Exception {
        vehicleTypeDAO.remove(t1.getUuid());
    }


    @Test
    public void createGetRemoveTest() throws Exception {
        //TODO: change it so new vehicletypes are created with the vehicletype DAO instead of through the vehicletype constructor
        //VehicleType type = new VehicleType(null, "type 1", 2.5);
        Vehicle vehicle1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a vehicle can be succesfully added to the database
        try {
            vehicle1 = vehicleDao.create("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, t1, LocalDate.now());
        } catch (Exception e) {
            fail("Failed trying to create a new vehicle");
        }
        //If a vehicle was succesfully added, test if it can be retrieved succesfully
        try {
            if (vehicle1 != null) {
                Vehicle vehicle2 = vehicleDao.get(vehicle1.getUuid());
                assertEquals("type field not equal", vehicle1.getType(), vehicle2.getType());
                assertEquals("brand field not equal", vehicle1.getBrand(), vehicle2.getBrand());
                assertEquals("model field not equal", vehicle1.getModel(), vehicle2.getModel());
                assertEquals("chassisNumber field not equal", vehicle1.getChassisNumber(), vehicle2.getChassisNumber());
                assertEquals("licensePlate field not equal", vehicle1.getLicensePlate(), vehicle2.getLicensePlate());
                assertEquals("value field not equal", vehicle1.getValue(), vehicle2.getValue());
                assertEquals("mileage field not equal", vehicle1.getMileage(), vehicle2.getMileage());
                assertEquals("productionDate field not equal", vehicle1.getProductionDate(), vehicle2.getProductionDate());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing vehicle from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {
            if (vehicle1 != null && present) {
                vehicleDao.remove(vehicle1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a vehicle from the database");
        }
        //Check if the vehicle is effectively removed (if create, get and remove tests passed)
        try {
            if (vehicle1 != null && present && removed) {
                Vehicle vehicle3 = vehicleDao.get(vehicle1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Vehicle is still in database after removal", vehicle3);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
    }

    @Ignore
    @Test
    public void update() throws Exception {
        //add new vehicle to the database
        Vehicle v1 = vehicleDao.create("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, LocalDate.now());
        //try to update the vehicle's brand field in the database
        Vehicle v2 = vehicleDao.update(v1.getUuid(), "brand 3", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, v1.getProductionDate());
        assertEquals("returned vehicle object is not updated", "brand 3", v2.getBrand());
        Vehicle v3 = vehicleDao.get(v1.getUuid());
        assertEquals("vehicle in the database is not updated", "brand 3", v3.getBrand());
        //clean up database for new other tests
        vehicleDao.remove(v1.getUuid());
    }

}