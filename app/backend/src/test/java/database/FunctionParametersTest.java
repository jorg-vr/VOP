package database;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by Ponti on 10/04/2017.
 */
public class FunctionParametersTest {

    private static DAOProvider daoProvider;
    private static Address address;
    private static Customer customer;
    private static Role role;
    private static User user;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             RoleDAO roleDAO = daoProvider.getRoleDAO();
             UserDAO userDAO = daoProvider.getUserDAO()) {

            address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
            customer = customerDAO.create(new Customer(address, "911", "Name", "btw123"));
            role = roleDAO.create(new Role("roleName"));
            user = userDAO.create(new User("firstname", "lastname", "email.address@test.com", "password123"));
        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             RoleDAO roleDAO = daoProvider.getRoleDAO();
             UserDAO userDAO = daoProvider.getUserDAO()) {

            userDAO.remove(user.getUuid());
            roleDAO.remove(role.getUuid());
            customerDAO.remove(customer.getUuid());
            addressDAO.remove(address.getUuid());
        }
        daoProvider.close();
    }

    @Test
    public void allFields() throws Exception {
        Function function = null;
        try (FunctionDAO functionDAO = daoProvider.getFunctionDAO()) {
            function = functionDAO.create(new Function(customer, role, user, null, null));
            functionDAO.remove(function.getUuid());
        } catch (DataAccessException d) {
            if (function == null) {
                fail("Could not make function despite all required fields being filled in");
            }
        }
    }

    @Test
    public void companyField() throws Exception {
        Function function = null;
        try (FunctionDAO functionDAO = daoProvider.getFunctionDAO()) {
            function = functionDAO.create(new Function(null, role, user, null, null));
            functionDAO.remove(function.getUuid());
            fail("Function succesfully created with company field null when an exception was expected");
        } catch (DataAccessException d) {
            if (function != null) {
                fail("Function succesfully created with company field null when an exception was expected");
            }
        }
    }

    @Test
    public void roleField() throws Exception {
        Function function = null;
        try (FunctionDAO functionDAO = daoProvider.getFunctionDAO()) {
            function = functionDAO.create(new Function(customer, null, user, null, null));
            functionDAO.remove(function.getUuid());
            fail("Function succesfully created with role field null when an exception was expected");
        } catch (DataAccessException d) {
            if (function != null) {
                fail("Function succesfully created with role field null when an exception was expected");
            }
        }
    }

    @Test
    public void userField() throws Exception {
        Function function = null;
        try (FunctionDAO functionDAO = daoProvider.getFunctionDAO()) {
            function = functionDAO.create(new Function(customer, role, null, null, null));
            functionDAO.remove(function.getUuid());
            fail("Function succesfully created with user field null when an exception was expected");
        } catch (DataAccessException d) {
            if (function != null) {
                fail("Function succesfully created with user field null when an exception was expected");
            }
        }
    }
}
