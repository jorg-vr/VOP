package database;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Tests the consistency and correct functioning of datastructures like collections and hashmaps (used in the models) in the database.
 */
public class DataStructuresTest {

    private static DAOProvider daoProvider;
    private static VehicleDAO vehicleDao;
    private static VehicleTypeDao vehicleTypeDAO;
    private static FleetDAO fleetDAO;
    private static CustomerDAO customerDAO;
    private static AddressDAO addressDAO;
    private static boolean notLocalTest = false;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        daoProvider = ProductionProvider.getInstance();
        if(daoProvider == null){
            ProductionProvider.initializeProvider("test");
            daoProvider = ProductionProvider.getInstance();
            notLocalTest = true;
        }
        vehicleDao = daoProvider.getVehicleDAO();
        vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
        customerDAO = daoProvider.getCustomerDAO();
        fleetDAO = daoProvider.getFleetDAO();
        addressDAO = daoProvider.getAddressDao();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        if(notLocalTest) {
            daoProvider.close();
        }
    }

    //TODO: test if duplicate vehicles can be added to a fleet
    /**
     * tests if the link between vehicles and their fleet is correctly maintained at all times.
     * @throws Exception
     */
    @Ignore
    @Test
    public void vehiclesCollection() throws Exception {
        Address a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Customer cust1 = customerDAO.create(new Customer(a1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));
        Fleet fleet1 = fleetDAO.create(new Fleet("fleet 1", cust1));
        VehicleType t1 = vehicleTypeDAO.create(new VehicleType("type 1", 2.5));
        VehicleType t2 = vehicleTypeDAO.create(new VehicleType("type 2", 3.5));

        //create vehicles without assigned fleet
        Vehicle v1 = vehicleDao.create(new Vehicle("brand 2", "model A", "AZ0UZABCUKZ12345L", "ABR 569", 36000, 4900, t1, LocalDate.of(2015, 6, 17), null, null));
        Vehicle v2 = vehicleDao.create(new Vehicle("brand 3", "model B", "BZ0UZABCUKZ12345L", "BBR 569", 36000, 4900, t1, LocalDate.of(2015, 7, 17), null, null));
        Vehicle v3 = vehicleDao.create(new Vehicle("brand 4", "model C", "CZ0UZABCUKZ12345L", "CBR 569", 36000, 4900, t2, LocalDate.of(2015, 8, 17), null, null));

        Fleet tempFleet;
        Collection<Vehicle> tempCollection;
        Vehicle tempVehicle;

        //Test if vehicles are correctly added to the fleet's vehicles collection
        fleet1.addVehicle(v1);
        fleetDAO.update(fleet1);
        tempFleet = fleetDAO.get(fleet1.getUuid());
        tempCollection = tempFleet.getVehicles();
        assertTrue("vehicle was not added to the fleet's vehicles collection", tempCollection.contains(v1));

        //Test if a vehicle's assigned fleet is automatically set when adding it to a fleet.
        tempVehicle = vehicleDao.get(v1.getUuid());
        assertEquals("fleet field of a vehicle not automatically set upon adding to a fleet's vehicles collection",fleet1,tempVehicle.getFleet());

        //Test if a vehicle is automatically added to a fleet's vehicles collection when setting the vehicle's fleet field.
        v2.setFleet(fleet1);
        vehicleDao.update(v2);
        tempFleet = fleetDAO.get(fleet1.getUuid());
        tempCollection = tempFleet.getVehicles();
        assertTrue("vehicle was not automatically added to the fleet's vehicles collection", tempCollection.contains(v2));

        if(tempCollection.contains(v1)){
            //Test if vehicles are correctly removed from the collection
            tempFleet.removeVehicle(v1);
            fleetDAO.update(tempFleet);
            tempFleet = fleetDAO.get(fleet1.getUuid());
            assertTrue("vehicle was not correctly removed from vehicles collection", !tempFleet.getVehicles().contains(v1));

            //Test if vehicle's fleet field is automatically set to null upon removal
            tempVehicle = vehicleDao.get(v1.getUuid());
            assertEquals("vehicle's fleet field was not automatically updated upon removal from it's fleet", null, tempVehicle.getFleet());
        }

        //Test if vehicles are automatically removed from the fleet's vehicle collection when a vehicle is removed from the database
        tempFleet = fleetDAO.get(fleet1.getUuid());
        tempFleet.addVehicle(v3);
        fleetDAO.update(tempFleet);
        vehicleDao.remove(v3.getUuid());
        assertTrue("vehicle still remains in the fleet's vehicles collection after removing it from the database", !fleetDAO.get(fleet1.getUuid()).getVehicles().contains(v3));

        //cleaning up the database
        vehicleDao.remove(v2.getUuid());
        vehicleDao.remove(v1.getUuid());
        vehicleTypeDAO.remove(t1.getUuid());
        vehicleTypeDAO.remove(t2.getUuid());
        fleetDAO.remove(fleet1.getUuid());
        customerDAO.remove(cust1.getUuid());
        addressDAO.remove(a1.getUuid());
    }

    @Ignore
    @Test
    public void functionsCollection() throws Exception {
        //TODO: make function and user DAO tests first before starting this








    }

    /**
     * tests the insurances collection of the InsuranceCompany model
     *
     * @throws Exception
     */
    @Ignore
    @Test
    public void insurancesCollection() throws Exception {
        //TODO
    }

    /**
     * the owner field is required for customers so less tests are required compared to vehicles
     * @throws Exception
     */
    @Ignore
    @Test
    public void fleetsCollection() throws Exception {
        Address a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Customer c1 = customerDAO.create(new Customer(a1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));

        //Test if a fleet is automatically added to the owner's fleets collection upon creation
        Fleet fleet1 = fleetDAO.create(new Fleet("fleet 1", c1));
        assertTrue("fleet was not automatically added to the owner's fleets collection", customerDAO.get(c1.getUuid()).getFleets().contains(fleet1));

        //Test if fleets are automatically removed from the customer's fleets collection when a fleet is removed from the database
        if(!customerDAO.get(c1.getUuid()).getFleets().contains(fleet1)){
            c1 = customerDAO.get(c1.getUuid());
            c1.addFleet(fleet1);
            customerDAO.update(c1);
        }
        fleetDAO.remove(fleet1.getUuid());
        c1 = customerDAO.get(c1.getUuid());
        assertTrue("fleet still remains in the customer's fleets collection after removing it from the database", c1.getFleets().contains(fleet1));

        //TODO: potentially tests on removing fleets from the fleets collection instead of directly from the database

        //cleaning up the database
        customerDAO.remove(c1.getUuid());
        addressDAO.remove(a1.getUuid());
    }

    @Ignore
    @Test
    public void rightsMap() throws Exception {
        //TODO
    }

    
}
