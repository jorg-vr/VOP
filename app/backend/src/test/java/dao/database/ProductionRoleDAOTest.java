package dao.database;

import dao.interfaces.DAOProvider;
import dao.interfaces.RoleDAO;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Ponti on 5/04/2017.
 */
public class ProductionRoleDAOTest {
    private static DAOProvider daoProvider;
    private static RoleDAO roleDAO;


    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        roleDAO = daoProvider.getRoleDAO();
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
        Role r1 = null;
        boolean present = false;
        boolean removed = false;
        //test if a role can be succesfully added to the database
        try {
            r1 = roleDAO.create(new Role("testRole1"));
        } catch (Exception e) {
            fail("Failed trying to create a new role");
        }
        //If a role was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            if (r1 != null) {
                Role r2 = roleDAO.get(r1.getUuid());
                assertEquals("name field not created correctly", r1.getName(), r2.getName());
                assertEquals("rights field not created correctly", r1.getRights(), r2.getRights());
                present = true;
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing role from the database");
        }
        //If the role is confirmed to be present in the database, try to remove it
        try {
            if (r1 != null && present) {
                roleDAO.remove(r1.getUuid());
                removed = true;
            }
        } catch (Exception e) {
            fail("Failed trying to remove a role from the database");
        }
        //Check if the role is effectively removed (if create, get and remove tests passed)
        try {
            if (r1 != null && present && removed) {
                Role r2 = roleDAO.get(r1.getUuid());
                //adding this because I'm not sure if the get method returns a null object or an error for a non existing uuid
                assertNull("Role is still in database after removal", r2);
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
        Role r1 = roleDAO.create(new Role("testRole1"));
        Role r2 = roleDAO.create(new Role("testRole2"));
        r2.setUuid(r1.getUuid());
        roleDAO.update(r2);
        Role r3 = roleDAO.get(r1.getUuid());

        assertEquals("name field not updated correctly", r1.getName(), r3.getName());
        roleDAO.remove(r1.getUuid());
    }
}
