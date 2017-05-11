package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import model.account.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static database.DAOTestUtil.createUser;
import static database.DAOTestUtil.removeUser;
import static org.junit.Assert.fail;

public class UserParametersTest {

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
    public void firstNameField() throws Exception {
        try {
            User user = createUser(new User(null, "Lastname", "email.address@test.com", "Password"));
            removeUser(user.getUuid());
            fail("User succesfully created with firstName field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void lastNameField() throws Exception {
        try {
            User user = createUser(new User("Firstname", null, "email.address@test.com", "Password"));
            removeUser(user.getUuid());
            fail("User succesfully created with lastName field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void emailField() throws Exception {
        try {
            User user = createUser(new User("Firstname", "Lastname", null, "Password"));
            removeUser(user.getUuid());
            fail("User succesfully created with email field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void passwordField() throws Exception {
        try {
            User user = createUser(new User("Firstname", "Lastname", "email.address@test.com", null));
            removeUser(user.getUuid());
            fail("User succesfully created with password field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
