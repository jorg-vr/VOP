package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static database.DAOTestUtil.createRole;
import static database.DAOTestUtil.removeRole;
import static org.junit.Assert.fail;

@Ignore
public class RoleParametersTest {

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
    public void nameField() throws Exception {
        try {
            Role role = createRole(new Role(null));
            removeRole(role.getUuid());
            fail("Role succesfully created with name field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            e.printStackTrace();
            fail("Role succesfully created with name field null when an exception was expected");
        }
    }
}
