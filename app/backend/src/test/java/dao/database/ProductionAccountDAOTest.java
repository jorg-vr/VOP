package dao.database;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.PersonDAO;
import model.account.Account;
import model.identity.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 14/03/2017.
 */
public class ProductionAccountDAOTest {
    private static DAOProvider daoProvider;
    private static AccountDAO accountDAO;
    private static PersonDAO personDAO;
    //private static Person p1;

    //TODO: production to false, when local
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider(true);
        daoProvider = ProductionProvider.getInstance();
        accountDAO = daoProvider.getAccountDao();
        personDAO = daoProvider.getPersonDAO();
        //p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        //personDAO.remove(p1.getUuid());
    }

    @Ignore
    @Test
    public void createGetRemoveTest() throws Exception {
        Person p1 = null;
        Account a1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a vehicle can be succesfully added to the database
        try {
            p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
            assertNotNull("Failed to create a person object", p1);
            a1 = accountDAO.create("login1", "hashedPassword1", p1);
            assertNotNull("Failed to create an account object", a1);
        } catch (Exception e) {
            fail("Failed trying to create a new account");
        }
        //If a address was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (a1 != null) {
                Account a2 = accountDAO.get(a1.getUuid());
                assertEquals("login field not created correctly", a1.getLogin(), a2.getLogin());
                assertEquals("hashedPassword field not created correctly", a1.getHashedPassword(), a2.getHashedPassword());
                assertEquals("person field not created correctly", a1.getPerson(), a2.getPerson());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing account from the database");
        }
        //If the address is confirmed to be present in the database, try to remove it
        try {
            if (a1 != null && present) {
                accountDAO.remove(a1.getUuid());
                personDAO.remove(p1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an account from the database");
        }
        //Check if the address is effectively removed (if create, get and remove tests passed)
        try {
            if (a1 != null && present && removed) {
                Account a2 = accountDAO.get(a1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("account is still in database after removal", a2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
    }


    @Ignore
    @Test
    public void update() throws Exception {
        Person p1 = personDAO.create("Firstname 1", "Lastname 1", "Email@address1.com");
        Account a1 = accountDAO.create("login1", "hashedPassword1", p1);
        Account a2 = accountDAO.update(a1.getUuid(), "login2", "hashedPassword2");
        Account a3 = accountDAO.get(a1.getUuid());
        assertEquals("login field not updated correctly","login2" , a3.getLogin());
        assertEquals("hashedPassword field not updated correctly","hashedPassword2" , a3.getHashedPassword());
        assertEquals("person field not updated correctly", p1, a3.getPerson());

        accountDAO.remove(a1.getUuid());
        personDAO.remove(p1.getUuid());
    }
}
