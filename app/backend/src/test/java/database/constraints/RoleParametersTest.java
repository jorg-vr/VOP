package database.constraints;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.RoleDAO;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

@Ignore
public class RoleParametersTest {

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
    public void nameField() throws Exception {
        Role role = null;
        try {
            RoleDAO roleDAO = daoManager.getRoleDAO();
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
