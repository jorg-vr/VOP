package database;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.CompanyType;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

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
