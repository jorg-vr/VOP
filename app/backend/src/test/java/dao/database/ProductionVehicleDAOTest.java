package dao.database;

import dao.exceptions.ObjectNotFoundException;
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

import static dao.database.DAOTestUtil.*;
import static org.junit.Assert.*;


public class ProductionVehicleDAOTest {

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
        Customer customer = null;
        Fleet fleet = null;
        VehicleType vehicleType = null;
        Vehicle vehicle = null;

        //test if a vehicle can be succesfully added to the database
        try {
            Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
            customer = createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new customer");
        }
        try {
            Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
            fleet = createFleet(new Fleet("fleet 1", customer, address));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new fleet");
        }
        try {
            vehicleType = createVehicleType(new VehicleType("type 1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new vehicleType");
        }
        try {
            vehicle = createVehicle(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, LocalDate.now(), fleet));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new vehicle");
        }
        //If a vehicle was succesfully added, test if it can be retrieved succesfully
        try {
            Vehicle vehicle1 = getVehicle(vehicle.getUuid());
            assertEquals("type field not equal", vehicle.getType(), vehicle1.getType());
            assertEquals("brand field not equal", vehicle.getBrand(), vehicle1.getBrand());
            assertEquals("model field not equal", vehicle.getModel(), vehicle1.getModel());
            assertEquals("chassisNumber field not equal", vehicle.getVin(), vehicle1.getVin());
            assertEquals("licensePlate field not equal", vehicle.getLicensePlate(), vehicle1.getLicensePlate());
            assertEquals("value field not equal", vehicle.getValue(), vehicle1.getValue());
            assertEquals("mileage field not equal", vehicle.getMileage(), vehicle1.getMileage());
            assertEquals("productionDate field not equal", vehicle.getYear(), vehicle1.getYear());
            assertEquals("fleet field not equal", vehicle.getFleet(), vehicle1.getFleet());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing vehicle from the database");
        }
        //If the vehicle is confirmed to be present in the database, try to remove it
        try {
            removeVehicle(vehicle.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove a vehicle from the database");
        }
        //Check if the vehicle is effectively removed (if create, get and remove tests passed)
        try {
            getVehicle(vehicle.getUuid());
            //If get was successfull the test fails
            assertNull("Vehicle is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }

        removeVehicleType(vehicleType.getUuid());
        removeFleet(fleet.getUuid());
        removeCustomer(customer.getUuid());
    }


    @Test
    public void update() throws Exception {

        Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Customer customer = createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Fleet fleet = createFleet(new Fleet("fleet 1", customer, address));
        VehicleType vehicleType1 = createVehicleType(new VehicleType("type 1"));
        VehicleType vehicleType2 = createVehicleType(new VehicleType("type 2"));

        //add new vehicle to the database
        Vehicle vehicle = createVehicle(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, vehicleType1, LocalDate.of(2015, 6, 17), fleet));

        //try to update the vehicle's brand field in the database
        vehicle.setBrand("brand 3");
        vehicle.setModel("model B");
        vehicle.setVin("AZ0UZABCUKZ12345A");
        vehicle.setLicensePlate("ABR 600");
        vehicle.setValue(37000);
        vehicle.setMileage(5900);
        vehicle.setType(vehicleType2);
        vehicle.setYear(LocalDate.of(2016, 7, 18));
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleDAO().update(vehicle);
        }

        Vehicle vehicle1 = getVehicle(vehicle.getUuid());
        assertEquals("brand field not updated correctly", "brand 3", vehicle1.getBrand());
        assertEquals("model field not updated correctly", "model B", vehicle1.getModel());
        assertEquals("chassisNumber field not updated correctly", "AZ0UZABCUKZ12345A", vehicle1.getVin());
        assertEquals("licensPlate field not updated correctly", "ABR 600", vehicle1.getLicensePlate());
        assertEquals("value field not updated correctly", 37000, vehicle1.getValue());
        assertEquals("mileage field not updated correctly", 5900, vehicle1.getMileage());
        assertEquals("type field not updated correctly", vehicleType2, vehicle1.getType());
        assertEquals("productionDate field not updated correctly", LocalDate.of(2016, 7, 18), vehicle1.getYear());

        //clean up database for new other tests
        removeVehicle(vehicle.getUuid());
        removeVehicleType(vehicleType1.getUuid());
        removeVehicleType(vehicleType2.getUuid());
        removeFleet(fleet.getUuid());
        removeCustomer(customer.getUuid());
    }

}