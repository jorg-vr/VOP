package database.constraints;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.UserDAO;
import model.account.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class UserParametersTest {

    private static DAOManager daoManager;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoManager = ProductionProvider.getInstance().getDaoManager();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoManager.close();
        ProductionProvider.getInstance().close();

    }

    @Test
    public void firstNameField() throws Exception {
        User user = null;
        try {
            UserDAO userDAO = daoManager.getUserDAO();
            user = userDAO.create(new User(null, "Lastname", "email.address@test.com", "Password"));
            userDAO.remove(user.getUuid());
            fail("User succesfully created with firstName field null when an exception was expected");
        } catch (DataAccessException d) {
            if (user != null) {
                fail("User succesfully created with firstName field null when an exception was expected");
            }
        }
    }

    @Test
    public void lastNameField() throws Exception {
        User user = null;
        try {
            UserDAO userDAO = daoManager.getUserDAO();
            user = userDAO.create(new User("Firstname", null, "email.address@test.com", "Password"));
            userDAO.remove(user.getUuid());
            fail("User succesfully created with lastName field null when an exception was expected");
        } catch (DataAccessException d) {
            if (user != null) {
                fail("User succesfully created with lastName field null when an exception was expected");
            }
        }
    }

    @Test
    public void emailField() throws Exception {
        User user = null;
        try {
            UserDAO userDAO = daoManager.getUserDAO();
            user = userDAO.create(new User("Firstname", "Lastname", null, "Password"));
            userDAO.remove(user.getUuid());
            fail("User succesfully created with email field null when an exception was expected");
        } catch (DataAccessException d) {
            if (user != null) {
                fail("User succesfully created with email field null when an exception was expected");
            }
        }
    }

    @Test
    public void passwordField() throws Exception {
        User user = null;
        try {
            UserDAO userDAO = daoManager.getUserDAO();
            user = userDAO.create(new User("Firstname", "Lastname", "email.address@test.com", null));
            userDAO.remove(user.getUuid());
            fail("User succesfully created with password field null when an exception was expected");
        } catch (DataAccessException d) {
            if (user != null) {
                fail("User succesfully created with password field null when an exception was expected");
            }
        }
    }
}
