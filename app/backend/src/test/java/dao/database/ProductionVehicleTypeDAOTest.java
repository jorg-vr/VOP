package dao.database;

import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.VehicleType;
import model.insurance.SuretyType;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;


public class ProductionVehicleTypeDAOTest {
    private static DAOManager daoManager;
    private static DAOProvider daoProvider;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        daoManager = daoProvider.getDaoManager();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoManager.close();
        daoProvider.close();
    }

    @Test
    public void createGetRemoveTest() throws Exception {
        VehicleType t1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a vehicle can be succesfully added to the database
        try {VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            Map<SuretyType, Double> taxes = new HashMap<>();
            taxes.put(SuretyType.CIVIL_LIABILITY, 1.0);
            taxes.put(SuretyType.OMNIUM_FULL, 2.0);
            taxes.put(SuretyType.OMNIUM_PARTIAL, 3.0);
            taxes.put(SuretyType.LEGAL_AID, 4.0);
            taxes.put(SuretyType.TRAVEL_AID, 5.0);
            taxes.put(SuretyType.SAFETY, 6.0);

            Map<SuretyType, Double> commissions = new HashMap<>();
            commissions.put(SuretyType.CIVIL_LIABILITY, 1.0);
            commissions.put(SuretyType.OMNIUM_FULL, 2.0);
            commissions.put(SuretyType.OMNIUM_PARTIAL, 3.0);
            commissions.put(SuretyType.LEGAL_AID, 4.0);
            commissions.put(SuretyType.TRAVEL_AID, 5.0);
            commissions.put(SuretyType.SAFETY, 6.0);

            t1 = vehicleTypeDAO.create(new VehicleType("type 14", taxes, commissions));
        } catch (Exception e) {
            fail("Failed trying to create a new vehicleType");
        }
        //If a vehicleType was succesfully added, test if it can be retrieved succesfully
        try {VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            if (t1 != null) {
                VehicleType t2 = vehicleTypeDAO.get(t1.getUuid());
                assertEquals("type field not equal", t1.getType(), t2.getType());
                assertEquals("taxes field not equal", t1.getTaxes(), t2.getTaxes());
                assertEquals("commissions field not equal", t1.getCommissions(), t2.getCommissions());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing vehicleType from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            if (t1 != null && present) {
                vehicleTypeDAO.remove(t1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a vehicleType from the database");
        }
        //Check if the vehicleType is effectively removed (if create, get and remove tests passed)
        try {VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
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
        VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            VehicleType t1 = vehicleTypeDAO.create(new VehicleType("type 14"));
            t1.setType("type 15");

            Map<SuretyType, Double> taxes = new HashMap<>();
            taxes.put(SuretyType.CIVIL_LIABILITY, 1.0);
            taxes.put(SuretyType.OMNIUM_FULL, 2.0);
            taxes.put(SuretyType.OMNIUM_PARTIAL, 3.0);
            taxes.put(SuretyType.LEGAL_AID, 4.0);
            taxes.put(SuretyType.TRAVEL_AID, 5.0);
            taxes.put(SuretyType.SAFETY, 6.0);
            t1.setTaxes(taxes);

            Map<SuretyType, Double> commissions = new HashMap<>();
            commissions.put(SuretyType.CIVIL_LIABILITY, 1.0);
            commissions.put(SuretyType.OMNIUM_FULL, 2.0);
            commissions.put(SuretyType.OMNIUM_PARTIAL, 3.0);
            commissions.put(SuretyType.LEGAL_AID, 4.0);
            commissions.put(SuretyType.TRAVEL_AID, 5.0);
            commissions.put(SuretyType.SAFETY, 6.0);
            t1.setCommissions(commissions);

            vehicleTypeDAO.update(t1);
            VehicleType t3 = vehicleTypeDAO.get(t1.getUuid());
            assertEquals("returned vehicleType object is not updated correctly", t1.getType(), t3.getType());
            assertEquals("taxes field not updated correctly", t1.getTaxes(), t3.getTaxes());
            assertEquals("commissions field not updated correctly", t1.getCommissions(), t3.getCommissions());

            vehicleTypeDAO.remove(t1.getUuid());
    }
}