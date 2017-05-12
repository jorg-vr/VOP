package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.*;
import database.DAOTestUtil;
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


public class ProductionFunctionDAOTest {

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
        Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        User user = null;
        Role role = null;
        Function function = null;

        //test if a function can be succesfully added to the database
        try {
            customer = DAOTestUtil.createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new customer");
        }
        try {
            user = DAOTestUtil.createUser(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new user");
        }
        try {
            role = DAOTestUtil.createRole(new Role("testRole1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new role");
        }
        try {
            function = DAOTestUtil.createFunction(new Function(customer, role, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new function");
        }
        //If a function was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            Function function1 = DAOTestUtil.getFunction(function.getUuid());
            assertEquals("customer field not created correctly", function.getCompany(), function1.getCompany());
            assertEquals("role field not created correctly", function.getRole(), function1.getRole());
            assertEquals("user field not created correctly", function.getUser(), function1.getUser());
            assertEquals("startDate field not created correctly", function.getStartDate(), function1.getStartDate());
            assertEquals("endDate field not created correctly", function.getEndDate(), function1.getEndDate());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing function from the database");
        }
        //If the function is confirmed to be present in the database, try to remove it
        try {
            DAOTestUtil.removeFunction(function.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove an function from the database");
        }
        //Check if the function is effectively removed (if create, get and remove tests passed)
        try {
            DAOTestUtil.getFunction(function.getUuid());
            //If get was successfull the test fails
            fail("Function is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }

        //make sure everything is removed from the database again
        DAOTestUtil.removeRole(role.getUuid());
        DAOTestUtil.removeUser(user.getUuid());
        DAOTestUtil.removeCustomer(customer.getUuid());
    }

    @Test
    public void update() throws Exception {
        Address address = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Customer customer = DAOTestUtil.createCustomer(new Customer(address, "911", "customername 1", "btw123"));
        User user = DAOTestUtil.createUser(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        Role role = DAOTestUtil.createRole(new Role("testRole1"));

        Address address1 = new Address("streettest n1", "59", "town 1", "9999", "country 1");
        Customer customer1 = DAOTestUtil.createCustomer(new Customer(address1, "912", "customername 2", "btw124"));
        User user1 = DAOTestUtil.createUser(new User("Firstname 2", "Lastname 2", "Email@address2.com", "hashedPassword2"));
        Role role1 = DAOTestUtil.createRole(new Role("testRole2"));
        LocalDateTime localDateTime1 = LocalDateTime.of(2017, 7, 15, 0, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2018, 8, 3, 0, 0);

        Function function = DAOTestUtil.createFunction(new Function(customer, role, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        function.setCompany(customer1);
        function.setRole(role1);
        function.setUser(user1);
        function.setStartDate(localDateTime1);
        function.setEndDate(localDateTime2);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFunctionDAO().update(function);
        }
        Function function1 = DAOTestUtil.getFunction(function.getUuid());
        assertEquals("customer field not updated correctly", customer1, function1.getCompany());
        assertEquals("role field not updated correctly", role1, function1.getRole());
        assertEquals("user field not updated correctly", user1, function1.getUser());
        assertEquals("startDate field not updated correctly", localDateTime1, function1.getStartDate());
        assertEquals("endDate field not updated correctly", localDateTime2, function1.getEndDate());

        DAOTestUtil.removeFunction(function.getUuid());
        DAOTestUtil.removeRole(role.getUuid());
        DAOTestUtil.removeRole(role1.getUuid());
        DAOTestUtil.removeUser(user.getUuid());
        DAOTestUtil.removeUser(user1.getUuid());
        DAOTestUtil.removeCustomer(customer.getUuid());
        DAOTestUtil.removeCustomer(customer1.getUuid());
    }
}
