package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static database.DAOTestUtil.*;
import static org.junit.Assert.fail;

public class FunctionParametersTest {

    private static Customer customer;
    private static Role role;
    private static User user;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        Address address = new Address("Street", "55", "Town", "9000", "Country");
        customer = createCustomer(new Customer(address, "911", "Name", "btw123"));
        role = createRole(new Role("roleName"));
        user = createUser(new User("firstname", "lastname", "email.address@test.com", "password123"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeUser(user.getUuid());
        removeRole(role.getUuid());
        removeCustomer(customer.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        try {
            Function function = createFunction(new Function(customer, role, user, null, null));
            removeFunction(function.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
            fail("Could not make function despite all required fields being filled in");
        }
    }

    @Test
    public void companyField() throws Exception {
        try {
            Function function = createFunction(new Function(null, role, user, null, null));
            removeFunction(function.getUuid());
            fail("Function succesfully created with company field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void roleField() throws Exception {
        try {
            Function function = createFunction(new Function(customer, null, user, null, null));
            removeFunction(function.getUuid());
            fail("Function succesfully created with role field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void userField() throws Exception {
        try {
            Function function = createFunction(new Function(customer, role, null, null, null));
            removeFunction(function.getUuid());
            fail("Function succesfully created with user field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
