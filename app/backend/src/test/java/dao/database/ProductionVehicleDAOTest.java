package dao.database;

import dao.interfaces.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;


public class ProductionVehicleDAOTest {

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
        Address a1 = null;
        Customer cust1 = null;
        Fleet fleet1 = null;
        VehicleType t1 = null;
        Vehicle vehicle1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a vehicle can be succesfully added to the database
        try {
            AddressDAO addressDAO = daoManager.getAddressDao();
            a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new address");
        }
        try {
            CustomerDAO customerDAO = daoManager.getCustomerDAO();
            cust1 = customerDAO.create(new Customer(a1, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        try {
            FleetDAO fleetDAO = daoManager.getFleetDAO();
            fleet1 = fleetDAO.create(new Fleet("fleet 1", cust1, a1));
        } catch (Exception e) {
            fail("Failed trying to create a new fleet");
        }
        try {
            VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            t1 = vehicleTypeDAO.create(new VehicleType("type 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new vehicleType");
        }
        try {
            VehicleDAO vehicleDao = daoManager.getVehicleDAO();
            vehicle1 = vehicleDao.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, t1, LocalDate.now(), fleet1, null));
        } catch (Exception e) {
            fail("Failed trying to create a new vehicle");
        }
        //If a vehicle was succesfully added, test if it can be retrieved succesfully
        try {
            VehicleDAO vehicleDao = daoManager.getVehicleDAO();
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
                assertEquals("fleet field not equal", vehicle1.getFleet(), vehicle2.getFleet());
                present = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing vehicle from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {
            VehicleDAO vehicleDao = daoManager.getVehicleDAO();
            if (vehicle1 != null && present) {
                vehicleDao.remove(vehicle1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a vehicle from the database");
        }
        //Check if the vehicle is effectively removed (if create, get and remove tests passed)
        try {
            VehicleDAO vehicleDao = daoManager.getVehicleDAO();
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
        AddressDAO addressDAO = daoManager.getAddressDao();
        CustomerDAO customerDAO = daoManager.getCustomerDAO();
        FleetDAO fleetDAO = daoManager.getFleetDAO();
        VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();

        vehicleTypeDAO.remove(t1.getUuid());
        fleetDAO.remove(fleet1.getUuid());
        customerDAO.remove(cust1.getUuid());
        addressDAO.remove(a1.getUuid());

    }


    @Test
    public void update() throws Exception {
        AddressDAO addressDAO = daoManager.getAddressDao();
        CustomerDAO customerDAO = daoManager.getCustomerDAO();
        FleetDAO fleetDAO = daoManager.getFleetDAO();
        VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
        VehicleDAO vehicleDAO = daoManager.getVehicleDAO();

        Address a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Customer cust1 = customerDAO.create(new Customer(a1, "911", "customername 1", "btw123"));
        Fleet fleet1 = fleetDAO.create(new Fleet("fleet 1", cust1, a1));
        VehicleType t1 = vehicleTypeDAO.create(new VehicleType("type 1"));
        VehicleType t2 = vehicleTypeDAO.create(new VehicleType("type 2"));
        //add new vehicle to the database
        Vehicle v1 = vehicleDAO.create(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, LocalDate.of(2015, 6, 17), fleet1, null));
        //try to update the vehicle's brand field in the database
        v1.setBrand("brand 3");
        v1.setModel("model B");
        v1.setChassisNumber("AZ0UZABCUKZ12345A");
        v1.setLicensePlate("ABR 600");
        v1.setValue(37000);
        v1.setMileage(5900);
        v1.setType(t2);
        v1.setProductionDate(LocalDate.of(2016, 7, 18));
        vehicleDAO.update(v1);
        Vehicle v3 = vehicleDAO.get(v1.getUuid());
        assertEquals("brand field not updated correctly", "brand 3", v3.getBrand());
        assertEquals("model field not updated correctly", "model B", v3.getModel());
        assertEquals("chassisNumber field not updated correctly", "AZ0UZABCUKZ12345A", v3.getChassisNumber());
        assertEquals("licensPlate field not updated correctly", "ABR 600", v3.getLicensePlate());
        assertEquals("value field not updated correctly", 37000, v3.getValue());
        assertEquals("mileage field not updated correctly", 5900, v3.getMileage());
        assertEquals("type field not updated correctly", t2, v3.getType());
        assertEquals("productionDate field not updated correctly", LocalDate.of(2016, 7, 18), v3.getProductionDate());
        //clean up database for new other tests
        vehicleDAO.remove(v1.getUuid());
        vehicleTypeDAO.remove(t1.getUuid());
        vehicleTypeDAO.remove(t2.getUuid());
        fleetDAO.remove(fleet1.getUuid());
        customerDAO.remove(cust1.getUuid());
        addressDAO.remove(a1.getUuid());

        
    }

}