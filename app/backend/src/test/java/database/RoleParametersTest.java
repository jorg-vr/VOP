package database;

import dao.database.ProductionProvider;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.RoleDAO;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class RoleParametersTest {

    private static DAOProvider daoProvider;
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        daoProvider.close();
    }

    @Test
    public void nameField() throws Exception {
        Role role = null;
        try (RoleDAO roleDAO = daoProvider.getRoleDAO()) {
            role = roleDAO.create(new Role(null));
            roleDAO.remove(role.getUuid());
            fail("Role succesfully created with name field null when an exception was expected");
        } catch (DataAccessException d) {
            if (role != null) {
                fail("Role succesfully created with name field null when an exception was expected");
            }
        }
    }
}