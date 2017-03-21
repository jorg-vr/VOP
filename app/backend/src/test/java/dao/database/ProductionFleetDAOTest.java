package dao.database;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 15/03/2017.
 */
public class ProductionFleetDAOTest {
    private static DAOProvider daoProvider;
    private static FleetDAO fleetDAO;
    private static CustomerDAO customerDAO;

    //TODO: production to false, when local
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("test");
        daoProvider = ProductionProvider.getInstance();
        fleetDAO = daoProvider.getFleetDAO();
        customerDAO = daoProvider.getCustomerDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoProvider.close();
    }

    @Test
    public void createGetRemoveTest() throws Exception {
        Fleet fleet1 = null;
        Customer cust1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a fleet can be succesfully added to the database
        try {
            cust1 = customerDAO.create("customername 1", null, "911", "123456789");
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        try {
            fleet1 = fleetDAO.create("fleet 1", cust1);
        } catch (Exception e) {
            fail("Failed trying to create a new fleet");
        }
        //If a fleet was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (fleet1 != null) {
                Fleet fleet2 = fleetDAO.get(fleet1.getUuid());
                assertEquals("name field not created correctly", fleet1.getName(), fleet2.getName());
                assertEquals("customer field not created correctly", fleet1.getOwner(), fleet2.getOwner());
                assertTrue("vehicles field not created correctly", fleet2.size()== 0);
                present = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing fleet from the database");
        }
        //If the fleet is confirmed to be present in the database, try to remove it
        try {
            if (fleet1 != null && present) {
                fleetDAO.remove(fleet1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an fleet from the database");
        }
        //Check if the fleet is effectively removed (if create, get and remove tests passed)
        try {
            if (fleet1 != null && present && removed) {
                Fleet fleet2 = fleetDAO.get(fleet1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Fleet is still in database after removal", fleet2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }

        if (cust1 != null) {
            customerDAO.remove(cust1.getUuid());
        }
    }

    @Test
    public void update() throws Exception {
        Customer cust1 = customerDAO.create("customername 1", null, "911", "123456789");
        Customer cust2 = customerDAO.create("customername 2", null, "912", "123456788");
        Fleet fleet1 = fleetDAO.create("fleet 1", cust1);
        Fleet fleet2 = fleetDAO.update(fleet1.getUuid(), "fleet 2", cust2);
        Fleet fleet3 = fleetDAO.get(fleet1.getUuid());
        assertEquals("name field not updated correctly", "fleet 2", fleet3.getName());
        assertEquals("customer field not updated correctly", cust2, fleet3.getOwner());

        fleetDAO.remove(fleet1.getUuid());
        customerDAO.remove(cust1.getUuid());
        customerDAO.remove(cust2.getUuid());
    }

}
