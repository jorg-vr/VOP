package database;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.VehicleDAO;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.fail;

@Ignore
public class VehicleParametersTest {

    private static DAOManager daoManager;
    private static VehicleType vehicleType;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoManager = ProductionProvider.getInstance().getDaoManager();
        VehicleTypeDAO vehicleTypeDao = daoManager.getVehicleTypeDAO();
        vehicleType = vehicleTypeDao.create(new VehicleType("persoonswagen"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        VehicleTypeDAO vehicleTypeDao = daoManager.getVehicleTypeDAO();
        vehicleTypeDao.remove(vehicleType.getUuid());

        daoManager.close();
        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            vehicleDAO.remove(vehicle.getUuid());
        } catch (DataAccessException d) {
            if (vehicle == null) {
                fail("Failed to create a vehicle despite all required fields being filled in");
            }
        }
    }

    @Test
    public void brandField() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle(null, "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            vehicleDAO.remove(vehicle.getUuid());
            fail("Vehicle succesfully created with brand field null when an exception was expected");
        } catch (DataAccessException d) {
            if (vehicle != null) {
                fail("Vehicle succesfully created with brand field null when an exception was expected");
            }
        }
    }

    @Test
    public void modelField() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle("Volkswagen", null, "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            vehicleDAO.remove(vehicle.getUuid());
            fail("Vehicle succesfully created with model field null when an exception was expected");
        } catch (DataAccessException d) {
            if (vehicle != null) {
                fail("Vehicle succesfully created with model field null when an exception was expected");
            }
        }
    }

    @Test
    public void chassisNumberField() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle("Volkswagen", "Golf 7", null, null, 5000, 300, vehicleType, LocalDate.of(2015, 6, 17), null));
            vehicleDAO.remove(vehicle.getUuid());
            fail("Vehicle succesfully created with chassisNumber field null when an exception was expected");
        } catch (DataAccessException d) {
            if (vehicle != null) {
                fail("Vehicle succesfully created with chassisNumber field null when an exception was expected");
            }
        }
    }

    @Test
    public void typeField() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, null, LocalDate.of(2015, 6, 17), null));
            vehicleDAO.remove(vehicle.getUuid());
            fail("Vehicle succesfully created with type field null when an exception was expected");
        } catch (DataAccessException d) {
            if (vehicle != null) {
                fail("Vehicle succesfully created with type field null when an exception was expected");
            }
        }
    }

    @Test
    public void productionDateField() throws Exception {
        Vehicle vehicle = null;
        try {
            VehicleDAO vehicleDAO = daoManager.getVehicleDAO();
            vehicle = vehicleDAO.create(new Vehicle("Volkswagen", "Golf 7", "AAAAAAAAAAAAAAAAA", null, 5000, 300, vehicleType, null, null));
            vehicleDAO.remove(vehicle.getUuid());
            fail("Vehicle succesfully created with productionDate field null when an exception was expected");
        } catch (DataAccessException d) {
            if (vehicle != null) {
                fail("Vehicle succesfully created with productionDate field null when an exception was expected");
            }
        }
    }
}
