package dao.database;

import dao.interfaces.*;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 6/04/2017.
 */
public class ProductionFunctionDAOTest {
    private static DAOProvider daoProvider;
    private static FunctionDAO functionDAO;
    private static CustomerDAO customerDAO;
    private static AddressDAO addressDAO;
    private static UserDAO userDAO;
    private static RoleDAO roleDAO;


    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        functionDAO = daoProvider.getFunctionDAO();
        customerDAO = daoProvider.getCustomerDAO();
        addressDAO = daoProvider.getAddressDao();
        userDAO = daoProvider.getUserDAO();
        roleDAO = daoProvider.getRoleDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        functionDAO.close();
        customerDAO.close();
        addressDAO.close();
        userDAO.close();
        roleDAO.close();
        daoProvider.close();
    }


    @Test
    public void createGetRemoveTest() throws Exception {
        Customer cust1 = null;
        Address adr1 = null;
        User usr1 = null;
        Role r1 = null;
        Function f1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a function can be succesfully added to the database
        try {
            adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new address");
        }
        try {
            cust1 = customerDAO.create(new Customer(adr1, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        try {
            usr1 = userDAO.create(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        } catch (Exception e) {
            fail("Failed trying to create a new user");
        }
        try {
            r1 = roleDAO.create(new Role("testRole1"));
        } catch (Exception e) {
            fail("Failed trying to create a new role");
        }
        try {
            f1 = functionDAO.create(new Function(cust1, r1, usr1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        } catch (Exception e) {
            fail("Failed trying to create a new function");
        }
        //If a function was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (f1 != null) {
                Function f2 = functionDAO.get(f1.getUuid());
                assertEquals("customer field not created correctly", f1.getCompany(), f2.getCompany());
                assertEquals("role field not created correctly", f1.getRole(), f2.getRole());
                assertEquals("user field not created correctly", f1.getUser(), f2.getUser());
                assertEquals("startDate field not created correctly", f1.getStartDate(), f2.getStartDate());
                assertEquals("endDate field not created correctly", f1.getEndDate(), f2.getEndDate());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing function from the database");
        }
        //If the function is confirmed to be present in the database, try to remove it
        try {
            if (f1 != null && present) {
                functionDAO.remove(f1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an function from the database");
        }
        //Check if the function is effectively removed (if create, get and remove tests passed)
        try {
            if (f1 != null && present && removed) {
                Function f2 = functionDAO.get(f1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Function is still in database after removal", f2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
        //make sure everything is removed from the database again
        if (r1 != null) {
            roleDAO.remove(r1.getUuid());
        }
        if (usr1 != null) {
            userDAO.remove(usr1.getUuid());
        }
        if (cust1 != null) {
            customerDAO.remove(cust1.getUuid());
        }
        if (adr1 != null) {
            addressDAO.remove(adr1.getUuid());
        }
    }

    @Test
    public void update() throws Exception {
        Address adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Customer cust1 = customerDAO.create(new Customer(adr1, "911", "customername 1", "btw123"));
        User usr1 = userDAO.create(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        Role r1 = roleDAO.create(new Role("testRole1"));

        Address adr2 = addressDAO.create(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
        Customer cust2 = customerDAO.create(new Customer(adr2, "912", "customername 2", "btw124"));
        User usr2 = userDAO.create(new User("Firstname 2", "Lastname 2", "Email@address2.com", "hashedPassword2"));
        Role r2 = roleDAO.create(new Role("testRole2"));
        LocalDateTime t1 = LocalDateTime.of(2017, 7, 15, 0, 0);
        LocalDateTime t2 = LocalDateTime.of(2018, 8, 3, 0, 0);

        Function f1 = functionDAO.create(new Function(cust1, r1, usr1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        f1.setCompany(cust2);
        f1.setRole(r2);
        f1.setUser(usr2);
        f1.setStartDate(t1);
        f1.setEndDate(t2);
        functionDAO.update(f1);
        Function f3 = functionDAO.get(f1.getUuid());
        assertEquals("customer field not updated correctly", cust2, f3.getCompany());
        assertEquals("role field not updated correctly", r2, f3.getRole());
        assertEquals("user field not updated correctly", usr2, f3.getUser());
        assertEquals("startDate field not updated correctly", t1, f3.getStartDate());
        assertEquals("endDate field not updated correctly", t2, f3.getEndDate());

        functionDAO.remove(f1.getUuid());
        roleDAO.remove(r1.getUuid());
        roleDAO.remove(r2.getUuid());
        userDAO.remove(usr1.getUuid());
        userDAO.remove(usr2.getUuid());
        customerDAO.remove(cust1.getUuid());
        customerDAO.remove(cust2.getUuid());
        addressDAO.remove(adr1.getUuid());
        addressDAO.remove(adr2.getUuid());
    }
}
