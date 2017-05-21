package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Action;
import model.account.Resource;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTPermission;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 4/05/2017.
 */
public class RESTPermissionControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTPermissionController())
            .addPlaceholderValue("path.auth", "auth")
            .addPlaceholderValue("path.roles", "roles")
            .addPlaceholderValue("path.permissions", "permissions")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        Role role = new Role("roleControllerTestName");
        role.setAccess(Resource.BILLING, Action.READ_MINE);
        role.setAccess(Resource.BILLING, Action.CREATE_ALL);
        role.setAccess(Resource.FLEET, Action.READ_ALL);

        role = create(role);

        try {
            mvc.perform(MockMvcRequestBuilders.get("/auth/roles/" + UUIDUtil.UUIDToNumberString(role.getUuid()) + "/permissions")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                    .andExpect(jsonPath("$.total", equalTo(3)));
        } catch (Exception e) {
            remove(role.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(role.getUuid());
    }

    @Test
    public void put() throws Exception {

        //Add to database directly with DAO
        Role role = create(new Role("roleControllerTestName"));

        RESTPermission permission1 = new RESTPermission(Resource.BILLING, Action.READ_MINE);
        RESTPermission permission2 = new RESTPermission(Resource.BILLING, Action.CREATE_ALL);
        RESTPermission permission3 = new RESTPermission(Resource.FLEET, Action.READ_ALL);
        List<Long> permissions = new ArrayList<>();
        permissions.add(permission1.getId());
        permissions.add(permission2.getId());
        permissions.add(permission3.getId());

        //Perform PUT request to change the permissions
        try {
            mvc.perform(MockMvcRequestBuilders.put("/auth/roles/" + UUIDUtil.UUIDToNumberString(role.getUuid()) + "/permissions")
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
                    .andExpect(status().isOk());
        } catch (AssertionError e) {
            remove(role.getUuid());
            throw e;
        }

        //Test if permissions were correctly added
        try {
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                role = manager.getRoleDAO().get(role.getUuid());
                try {
                    assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.BILLING, Action.READ_MINE));
                    assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.BILLING, Action.CREATE_ALL));
                    assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.FLEET, Action.READ_ALL));
                } finally {
                    //Clean up database for other tests
                    remove(role.getUuid());
                }
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getRoleDAO().remove(uuid);
        }
    }

    private Role create(Role role) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getRoleDAO().create(role);
        }
    }

    private Role get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getRoleDAO().get(uuid);
        }
    }
}
