package database.consistency;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import database.DAOTestUtil;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;

import static database.DAOTestUtil.*;
import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 7/04/2017.
 */
public class VehiclesCollectionTest {

    private static Customer customer;
    private static VehicleType vehicleType1, vehicleType2;
    private static Fleet fleet;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        customer = createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        fleet = createFleet(new Fleet("fleet 1", customer, address));
        vehicleType1 = createVehicleType(new VehicleType("type 1"));
        vehicleType2 = createVehicleType(new VehicleType("type 2"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeVehicleType(vehicleType2.getUuid());
        removeVehicleType(vehicleType1.getUuid());
        removeFleet(fleet.getUuid());
        removeCustomer(customer.getUuid());

        ProductionProvider.getInstance().close();
    }

    /**
     * Test if a vehicle is automatically added to a fleet's vehicles collection when setting the vehicle's 'fleet' field to that fleet.
     *
     * @throws Exception
     */
    @Test
    public void setFleetOfVehicle() throws Exception {

        Vehicle vehicle1 = null, vehicle2 = null;
        try {
            //create vehicles
            fleet = getFleet(fleet.getUuid());
            vehicle1 = createVehicle(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, vehicleType1, LocalDate.of(2015, 6, 17), fleet));
            vehicle2 = createVehicle(new Vehicle("brand 3", "model B", "BZ0UZABCUKZ12345L", "BBR 569", 36000, 4900, vehicleType1, LocalDate.of(2015, 7, 17), null));

            //Test if a vehicle is automatically added to a fleet's vehicles collection when creating the vehicle with a given fleet parameter
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                fleet = manager.getFleetDAO().get(fleet.getUuid());
                assertTrue("vehicle was not automatically added to the fleet's vehicles collection when created with given fleet parameter", fleet.getVehicles().contains(vehicle1));
            }

            //Test if a vehicle is automatically added to a fleet's vehicles collection when setting the vehicle's fleet field.
            vehicle2.setFleet(fleet);
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                manager.getVehicleDAO().update(vehicle2);
            }
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                fleet = manager.getFleetDAO().get(fleet.getUuid());
                assertTrue("vehicle was not automatically added to the fleet's vehicles collection when updating the vehicle with a fleet parameter", fleet.getVehicles().contains(vehicle2));
            }
        } finally {
            if (vehicle1 != null) {
                DAOTestUtil.removeVehicle(vehicle1.getUuid());
            }
            if (vehicle2 != null) {
                DAOTestUtil.removeVehicle(vehicle2.getUuid());
            }
        }
    }

    /**
     * Test if a vehicle is correctly removed from it's fleet's vehicles collection when removing the vehicle from the database.
     *
     * @throws Exception
     */
    @Test
    public void removeVehicle() throws Exception {

        Vehicle vehicle = null;
        try {
            //create vehicle
            fleet = getFleet(fleet.getUuid());
            vehicle = createVehicle(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, vehicleType1, LocalDate.of(2015, 6, 17), fleet));

            //Test if a vehicle is automatically added to a fleet's vehicles collection when creating the vehicle with a given fleet parameter (if this fails the rest of the test is irrelevant)
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                fleet = manager.getFleetDAO().get(fleet.getUuid());
                assertTrue("vehicle was not automatically added to the fleet's vehicles collection when created with given fleet parameter", fleet.getVehicles().contains(vehicle));
            }

            //Test if vehicles are automatically removed from the fleet's vehicle collection when a vehicle is removed from the database
            DAOTestUtil.removeVehicle(vehicle.getUuid());
            Vehicle tempVehicle = vehicle;
            vehicle = null;
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                fleet = manager.getFleetDAO().get(fleet.getUuid());
                assertTrue("vehicle still remains in the fleet's vehicles collection after removing it from the database", !fleet.getVehicles().contains(tempVehicle));
            }

        } finally {
            if (vehicle != null) {
                DAOTestUtil.removeVehicle(vehicle.getUuid());
            }
        }
    }
}
