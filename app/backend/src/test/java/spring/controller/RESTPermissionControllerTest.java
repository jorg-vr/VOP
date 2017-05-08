package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.interfaces.RoleDAO;
import model.account.Action;
import model.account.Resource;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTPermission;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertTrue;
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

        Role role = new Role("roleControllerTestName");
        role.setAccess(Resource.BILLING, Action.READ_MINE);
        role.setAccess(Resource.BILLING, Action.CREATE_ALL);
        role.setAccess(Resource.FLEET, Action.READ_ALL);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            role =  manager.getRoleDAO().create(role);
        }

        try {
            mvc.perform(MockMvcRequestBuilders.get("/auth/roles/" + UUIDUtil.UUIDToNumberString(role.getUuid()) + "/permissions")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                    .andExpect(jsonPath("$.total", equalTo(3)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                manager.getRoleDAO().remove(role.getUuid());
            }
        }

    }

    @Test
    public void put() throws Exception {
        Role role = new Role("roleControllerTestName");
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            RoleDAO roleDAO = manager.getRoleDAO();
            role = roleDAO.create(role);
        }

        RESTPermission permission1 = new RESTPermission(Resource.BILLING, Action.READ_MINE);
        RESTPermission permission2 = new RESTPermission(Resource.BILLING, Action.CREATE_ALL);
        RESTPermission permission3 = new RESTPermission(Resource.FLEET, Action.READ_ALL);
        List<Long> permissions = new ArrayList<>();
        permissions.add(permission1.getId());
        permissions.add(permission2.getId());
        permissions.add(permission3.getId());

        try {
            //Perform PUT request to change the permissions
            MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/auth/roles/" + UUIDUtil.UUIDToNumberString(role.getUuid()) + "/permissions")
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(permissions))
            )
                    .andExpect(status().isOk())
                    .andReturn();

            //Test if permissions were correctly added
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                RoleDAO roleDAO = manager.getRoleDAO();
                role = roleDAO.get(role.getUuid());
                assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.BILLING, Action.READ_MINE));
                assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.BILLING, Action.CREATE_ALL));
                assertTrue("Permissions were not correctly updated", role.hasAccess(Resource.FLEET, Action.READ_ALL));
            }
        } finally {
            //Clean up database for other tests
            try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
                manager.getRoleDAO().remove(role.getUuid());
            }
        }
    }
}
