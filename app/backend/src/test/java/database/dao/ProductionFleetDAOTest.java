package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.*;
import database.DAOTestUtil;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;


public class ProductionFleetDAOTest {

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
        Fleet fleet = null;
        Customer customer = null;

        //test if a fleet can be succesfully added to the database
        try {
            Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
            customer = DAOTestUtil.createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new customer");
        }
        try {
            Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
            fleet = DAOTestUtil.createFleet(new Fleet("fleet 1", customer, address));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new fleet");
        }
        //If a fleet was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            Fleet fleet1 = DAOTestUtil.getFleet(fleet.getUuid());
            assertEquals("name field not created correctly", fleet.getName(), fleet1.getName());
            assertEquals("customer field not created correctly", fleet.getOwner(), fleet1.getOwner());
            assertEquals("address field not created correctly", fleet.getAddress(), fleet1.getAddress());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing fleet from the database");
        }
        //If the fleet is confirmed to be present in the database, try to remove it
        try {
            DAOTestUtil.removeFleet(fleet.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove an fleet from the database");
        }
        //Check if the fleet is effectively removed (if create, get and remove tests passed)
        try {
            DAOTestUtil.getFleet(fleet.getUuid());
            //If get was successfull the test fails
            fail("Fleet is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }

        DAOTestUtil.removeCustomer(customer.getUuid());
    }

    @Test
    public void update() throws Exception {
        Address address1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Address address2 = DAOTestUtil.createAddress(new Address("streettest n2", "60", "town 2", "9990", "country 2"));
        Customer customer = DAOTestUtil.createCustomer(new Customer(address1, "911", "customername 1", "btw123"));
        Fleet fleet = DAOTestUtil.createFleet(new Fleet("fleet 1", customer, address1));

        fleet.setName("fleet 2");
        fleet.setAddress(address2);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFleetDAO().update(fleet);
        }
        Fleet fleet1 = DAOTestUtil.getFleet(fleet.getUuid());
        assertEquals("name field not updated correctly", "fleet 2", fleet1.getName());
        assertEquals("address field not updated correctly", address2, fleet1.getAddress());
        //assertEquals("customer field not updated correctly", cust2, fleet3.getOwner()); //Fleets never change owner so this is not needed currently

        DAOTestUtil.removeFleet(fleet.getUuid());
        DAOTestUtil.removeCustomer(customer.getUuid());
    }

}
