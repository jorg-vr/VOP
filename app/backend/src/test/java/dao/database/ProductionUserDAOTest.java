package dao.database;

import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static dao.database.DAOTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ProductionUserDAOTest {

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
        User user = null;
        //test if a user can be succesfully added to the database
        try {
            user = createUser(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to create a new user");
        }
        //If a user was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            User user1 = getUser(user.getUuid());
            assertEquals("firstName field not created correctly", user.getFirstName(), user1.getFirstName());
            assertEquals("lastName field not created correctly", user.getLastName(), user1.getLastName());
            assertEquals("email field not created correctly", user.getEmail(), user1.getEmail());
            assertEquals("password field not created correctly", user.getPassword(), user1.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to get an existing user from the database");
        }
        //If the function is confirmed to be present in the database, try to remove it
        try {
            removeUser(user.getUuid());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed trying to remove an function from the database");
        }
        //Check if the function is effectively removed (if create, get and remove tests passed)
        try {
            getUser(user.getUuid());
            //If get was successfull the test fails
            fail("User is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }
    }

    @Test
    public void update() throws Exception {

        User user = createUser(new User("Firstname 1", "Lastname 1", "Email@address1.com", "hashedPassword1"));
        user.setFirstName("Firstname 2");
        user.setLastName("Lastname 2");
        user.setEmail("Email@address2.com");
        user.setPassword("hashedPassword2");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getUserDAO().update(user);
        }

        User user1 = getUser(user.getUuid());
        assertEquals("firstName field not updated correctly", "Firstname 2", user1.getFirstName());
        assertEquals("lastName field not updated correctly", "Lastname 2", user1.getLastName());
        assertEquals("email field not updated correctly", "Email@address2.com", user1.getEmail());
        assertEquals("password field not updated correctly", "hashedPassword2", user1.getPassword());

        removeUser(user.getUuid());
    }
}