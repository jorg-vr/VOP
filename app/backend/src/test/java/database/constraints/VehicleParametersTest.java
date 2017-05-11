package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static database.DAOTestUtil.*;
import static org.junit.Assert.fail;

@Ignore
public class VehicleParametersTest {

    private static VehicleType vehicleType;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        vehicleType = createVehicleType(new VehicleType("persoonswagen"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeVehicleType(vehicleType.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            removeVehicle(vehicle.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
            fail("Failed to create a vehicle despite all required fields being filled in");
        }
    }

    @Test
    public void brandField() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle(null, "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            removeVehicle(vehicle.getUuid());
            fail("Vehicle succesfully created with brand field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Vehicle succesfully created with brand field null when an exception was expected");
        }
    }

    @Test
    public void modelField() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle("Volkswagen", null, "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            removeVehicle(vehicle.getUuid());
            fail("Vehicle succesfully created with model field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Vehicle succesfully created with model field null when an exception was expected");
        }
    }

    @Test
    public void chassisNumberField() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle("Volkswagen", "Golf 7", null, null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            removeVehicle(vehicle.getUuid());
            fail("Vehicle succesfully created with chassisNumber field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Vehicle succesfully created with chassisNumber field null when an exception was expected");
        }
    }

    @Test
    public void typeField() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, null, LocalDate.of(2015, 6, 17), null));
            removeVehicle(vehicle.getUuid());
            fail("Vehicle succesfully created with type field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Vehicle succesfully created with type field null when an exception was expected");
        }
    }

    @Test
    public void productionDateField() throws Exception {
        try {
            Vehicle vehicle = createVehicle(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, null, null));
            removeVehicle(vehicle.getUuid());
            fail("Vehicle succesfully created with productionDate field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Vehicle succesfully created with productionDate field null when an exception was expected");
        }
    }
}
