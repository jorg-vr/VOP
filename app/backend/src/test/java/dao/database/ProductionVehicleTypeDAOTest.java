package dao.database;

import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.VehicleType;
import model.insurance.SuretyType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static dao.database.DAOTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;


public class ProductionVehicleTypeDAOTest {

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
    public void createGetRemoveTest() throws Exception {
        VehicleType vehicleType = null;

        //test if a vehicle can be succesfully added to the database
        try {
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

            vehicleType = createVehicleType(new VehicleType("type 14", taxes, commissions));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new vehicleType");
        }
        //If a vehicleType was succesfully added, test if it can be retrieved succesfully
        try {
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                VehicleType vehicleType1 = manager.getVehicleTypeDAO().get(vehicleType.getUuid());
                assertEquals("type field not equal", vehicleType.getType(), vehicleType1.getType());
                assertEquals("taxes field not equal", vehicleType.getTaxes(), vehicleType1.getTaxes());
                assertEquals("commissions field not equal", vehicleType.getCommissions(), vehicleType1.getCommissions());
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing vehicleType from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {
            removeVehicleType(vehicleType.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove a vehicleType from the database");
        }
        //Check if the vehicleType is effectively removed (if create, get and remove tests passed)
        try {
            getVehicleType(vehicleType.getUuid());
            //If get was successfull the test fails
            fail("VehicleType is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }
    }


    @Test
    public void update() throws Exception {

        VehicleType vehicleType = createVehicleType(new VehicleType("type 14"));
        vehicleType.setType("type 15");

        Map<SuretyType, Double> taxes = new HashMap<>();
        taxes.put(SuretyType.CIVIL_LIABILITY, 1.0);
        taxes.put(SuretyType.OMNIUM_FULL, 2.0);
        taxes.put(SuretyType.OMNIUM_PARTIAL, 3.0);
        taxes.put(SuretyType.LEGAL_AID, 4.0);
        taxes.put(SuretyType.TRAVEL_AID, 5.0);
        taxes.put(SuretyType.SAFETY, 6.0);
        vehicleType.setTaxes(taxes);

        Map<SuretyType, Double> commissions = new HashMap<>();
        commissions.put(SuretyType.CIVIL_LIABILITY, 1.0);
        commissions.put(SuretyType.OMNIUM_FULL, 2.0);
        commissions.put(SuretyType.OMNIUM_PARTIAL, 3.0);
        commissions.put(SuretyType.LEGAL_AID, 4.0);
        commissions.put(SuretyType.TRAVEL_AID, 5.0);
        commissions.put(SuretyType.SAFETY, 6.0);
        vehicleType.setCommissions(commissions);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleTypeDAO().update(vehicleType);
        }

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            VehicleType vehicleType1 = manager.getVehicleTypeDAO().get(vehicleType.getUuid());
            assertEquals("returned vehicleType object is not updated correctly", vehicleType.getType(), vehicleType1.getType());
            assertEquals("taxes field not updated correctly", vehicleType.getTaxes(), vehicleType1.getTaxes());
            assertEquals("commissions field not updated correctly", vehicleType.getCommissions(), vehicleType1.getCommissions());
        }

        removeVehicleType(vehicleType.getUuid());
    }
}