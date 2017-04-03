package dao.database;

import dao.interfaces.*;
import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.*;


public class ProductionFunctionDAOTest {
    private static DAOProvider daoProvider;
    private static FunctionDAO functionDAO;
    private static CustomerDAO customerDAO;
    private static AddressDAO addressDAO;
    private static PersonDAO personDAO;
    private static AccountDAO accountDAO;
    //private static RoleDAO roleDAO;


    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        functionDAO = daoProvider.getFunctionDAO();
        customerDAO = daoProvider.getCustomerDAO();
        addressDAO = daoProvider.getAddressDao();
        personDAO = daoProvider.getPersonDAO();
        accountDAO = daoProvider.getAccountDao();
        //roleDAO = daoProvider.getRoleDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoProvider.close();
    }

    //TODO: change person creation when create method in PersonDAO gets changed
    @Ignore
    @Test
    public void createGetRemoveTest() throws Exception {
        Customer cust1 = null;
        Address adr1 = null;
        Person p1 = null;
        Account acc1 = null;
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
            cust1 = customerDAO.create(new Customer(adr1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));
        } catch (Exception e) {
            fail("Failed trying to create a new customer");
        }
        try {
            p1 = personDAO.create(new Person(null, "Email@address1.com", "123456789", "Firstname 1", "Lastname 1"));
        } catch (Exception e) {
            fail("Failed trying to create a new person");
        }
        try {
            acc1 = accountDAO.create(new Account("login1", "hashedPassword1", p1));
        } catch (Exception e) {
            fail("Failed trying to create a new account");
        }
        /*try {
            r1 = roleDAO.create();
        } catch (Exception e) {
            fail("Failed trying to create a new role");
        }*/
        try {
            f1 = functionDAO.create(new Function(cust1, r1, acc1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        } catch (Exception e) {
            fail("Failed trying to create a new function");
        }
        //If a function was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (f1 != null) {
                Function f2 = functionDAO.get(f1.getUuid());
                assertEquals("customer field not created correctly", f1.getCompany(), f2.getCompany());
                assertEquals("role field not created correctly", f1.getRole(), f2.getRole());
                assertEquals("account field not created correctly", f1.getAccount(), f2.getAccount());
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
        if (f1 != null) {
            functionDAO.remove(f1.getUuid());
        }
        //if(r1 != null){roleDAO.remove(r1.getUuid());}
        if (acc1 != null) {
            accountDAO.remove(acc1.getUuid());
        }
        if (p1 != null) {
            personDAO.remove(p1.getUuid());
        }
        if (adr1 != null) {
            addressDAO.remove(adr1.getUuid());
        }
        if (cust1 != null) {
            customerDAO.remove(cust1.getUuid());
        }
    }

    @Ignore
    @Test
    public void update() throws Exception {
        Address adr1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
        Customer cust1 = customerDAO.create(new Customer(adr1, "Email@address1.com", "911", "customername 1", "btw123", "123456789", CompanyType.TYPE1));
        Person p1 = personDAO.create(new Person(null, "Email@address1.com", "123456789", "Firstname 1", "Lastname 1"));
        Account acc1 = accountDAO.create(new Account("login1", "hashedPassword1", p1));
        Role r1 = null;
        //Role r1 = roleDAO.create();

        Address adr2 = addressDAO.create(new Address("streettest n2", "60", "town 2", "99999", "country 2"));
        Customer cust2 = customerDAO.create(new Customer(adr2, "Email@address2.com", "912", "customername 2", "btw124", "123456781", CompanyType.TYPE2));
        Account acc2 = accountDAO.create(new Account("login2", "hashedPassword2", p1));
        Role r2 = null;
        //Role r2 = roleDAO.create();
        LocalDateTime t1 = LocalDateTime.of(2017, 7, 15, 0, 0);
        LocalDateTime t2 = LocalDateTime.of(2018, 8, 3, 0, 0);

        Function f1 = functionDAO.create(new Function(cust1, r1, acc1, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2017, 8, 3, 0, 0)));
        Function f2 = functionDAO.create(new Function(cust2, r2, acc2, t1, t2));
        f2.setUuid(f1.getUuid());
        functionDAO.update(f2);
        Function f3 = functionDAO.get(f1.getUuid());
        assertEquals("customer field not updated correctly", cust2, f3.getCompany());
        assertEquals("role field not updated correctly", r2, f3.getRole());
        assertEquals("account field not updated correctly", acc2, f3.getAccount());
        assertEquals("startDate field not updated correctly", t1, f3.getStartDate());
        assertEquals("endDate field not updated correctly", t2, f3.getEndDate());

        functionDAO.remove(f1.getUuid());
        //roleDAO.remove(r1.getUuid());
        //roleDAO.remove(r2.getUuid());
        accountDAO.remove(acc1.getUuid());
        accountDAO.remove(acc2.getUuid());
        personDAO.remove(p1.getUuid());
        customerDAO.remove(cust1.getUuid());
        customerDAO.remove(cust2.getUuid());
        addressDAO.remove(adr1.getUuid());
        addressDAO.remove(adr2.getUuid());
    }
}
