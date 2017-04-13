package dao.database;

import dao.interfaces.DAOProvider;
import dao.interfaces.UserDAO;
import model.account.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductionUserDAOTest {
    private static DAOProvider daoProvider;
    private static UserDAO userDAO;


    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        userDAO = daoProvider.getUserDAO();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        userDAO.close();
        daoProvider.close();

    }


    @Test
    public void createGetRemoveTest() throws Exception {
        User usr1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a user can be succesfully added to the database
        try {
            usr1 = userDAO.create(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        } catch (Exception e) {
            fail("Failed trying to create a new user");
        }
        //If a user was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (usr1 != null) {
                User usr2 = userDAO.get(usr1.getUuid());
                assertEquals("firstName field not created correctly", usr1.getFirstName(), usr2.getFirstName());
                assertEquals("lastName field not created correctly", usr1.getLastName(), usr2.getLastName());
                assertEquals("email field not created correctly", usr1.getEmail(), usr2.getEmail());
                assertEquals("password field not created correctly", usr1.getPassword(), usr2.getPassword());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing user from the database");
        }
        //If the function is confirmed to be present in the database, try to remove it
        try {
            if (usr1 != null && present) {
                userDAO.remove(usr1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove an function from the database");
        }
        //Check if the function is effectively removed (if create, get and remove tests passed)
        try {
            if (usr1 != null && present && removed) {
                User usr2 = userDAO.get(usr1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("User is still in database after removal", usr2);
            }
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (Exception e) {
            //Nothing because the test passed in this case
        }
    }

    @Test
    public void update() throws Exception {
        User usr1 = userDAO.create(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        usr1.setFirstName("Firstname 2");
        usr1.setLastName("Lastname 2");
        usr1.setEmail("Email@address2.com");
        usr1.setPassword("hashedPassword2");
        userDAO.update(usr1);

        User usr2 = userDAO.get(usr1.getUuid());
        assertEquals("firstName field not updated correctly", "Firstname 2", usr2.getFirstName());
        assertEquals("lastName field not updated correctly", "Lastname 2", usr2.getLastName());
        assertEquals("email field not updated correctly", "Email@address2.com", usr2.getEmail());
        assertEquals("password field not updated correctly", "hashedPassword2", usr2.getPassword());
        userDAO.remove(usr1.getUuid());
    }
}