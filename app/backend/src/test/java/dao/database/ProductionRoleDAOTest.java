package dao.database;

import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Action;
import model.account.Permission;
import model.account.Resource;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static dao.database.DAOTestUtil.*;
import static org.junit.Assert.*;

/**
 * Created by Ponti on 5/04/2017.
 */
public class ProductionRoleDAOTest {

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

    //TODO: change person creation when create method in PersonDAO gets changed

    @Test
    public void createGetRemoveTest() throws Exception {
        Role role = null;

        //test if a role can be succesfully added to the database
        try {
            role = createRole(new Role("testRole1"));
        } catch (Exception e) {
            fail("Failed trying to create a new role");
        }
        //If a role was succesfully added, test if it can be retrieved succesfully and if all fields were correctly set
        try {
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                Role role1 = manager.getRoleDAO().get(role.getUuid());
                assertEquals("name field not created correctly", role.getName(), role1.getName());
                assertEquals("rights field not created correctly", role.getRights(), role1.getRights());
            }
        } catch (Exception e) {
            fail("Failed trying to get an existing role from the database");
        }
        //If the role is confirmed to be present in the database, try to remove it
        try {
            removeRole(role.getUuid());
        } catch (Exception e) {
            fail("Failed trying to remove a role from the database");
        }
        //Check if the role is effectively removed (if create, get and remove tests passed)
        try {
            getRole(role.getUuid());
            //If get was successfull the test fails
            fail("Role is still in database after removal");
        }
        //In case the get method returns an exception when given a uuid that's not present in the database.
        catch (ObjectNotFoundException e) {
            //Nothing because the test passed in this case
        }
    }

    @Ignore
    @Test
    public void update() throws Exception {

        Role role = createRole(new Role("test1"));
        role.setAccess(Resource.INSURANCE, Action.READ_ALL);
        role.setAccess(Resource.INSURANCE, Action.UPDATE_ALL);
        role.setAccess(Resource.INSURANCE, Action.REMOVE_ALL);
        role.setAccess(Resource.INSURANCE, Action.CREATE_ALL);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getRoleDAO().update(role);
        }


        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Role role1 = manager.getRoleDAO().get(role.getUuid());
            assertEquals(role.getRights(), role1.getRights());
        }
        role.setAccess(Resource.BILLING, Action.READ_ALL);
        role.setAccess(Resource.BILLING, Action.UPDATE_ALL);
        role.setAccess(Resource.BILLING, Action.REMOVE_ALL);
        role.setAccess(Resource.BILLING, Action.CREATE_ALL);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getRoleDAO().update(role);
        }


        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Role role2 = manager.getRoleDAO().get(role.getUuid());
            assertTrue(role2.getRights().keySet().contains(Resource.BILLING));
            assertTrue(role2.getRights().keySet().contains(Resource.INSURANCE));
        }

        Map<Resource, Permission> permissionMap = new HashMap<>();
        Permission permission = new Permission();
        permission.setResource(Resource.COMPANY);
        permission.addAction(Action.READ_ALL);
        permission.addAction(Action.CREATE_ALL);
        permissionMap.put(Resource.COMPANY, permission);

        role.setRights(permissionMap);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getRoleDAO().update(role);
        }


        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Role role3 = manager.getRoleDAO().get(role.getUuid());
            assertTrue(!role3.getRights().containsKey(Resource.BILLING));
        }

        removeRole(role.getUuid());
    }
}

