package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.RoleDAO;
import model.account.Role;
import org.hibernate.UnresolvableObjectException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTRole;
import util.UUIDUtil;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tjupo on 04/05/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTRoleControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTRoleController())
            .addPlaceholderValue("path.auth", "auth")
            .addPlaceholderValue("path.roles", "roles")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static String[] authPair;

    private static DAOManager manager;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        manager.close();
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {
        RoleDAO roleDAO = manager.getRoleDAO();
        Role role = roleDAO.create(new Role("roleName"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/auth/roles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 2 instead of 1 because there's 1 extra Role in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(2)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            roleDAO.remove(role.getUuid());
        }

    }

    //TODO compare permissions between database object and restobject (low priority)
    @Test
    public void post() throws Exception {
        RESTRole restRole = new RESTRole(new Role("roleName"));
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth/roles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restRole))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restRole.getName())))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTRole restRole1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTRole.class);
        RoleDAO roleDAO = manager.getRoleDAO();

        try {
            Role role = roleDAO.get(UUIDUtil.toUUID(restRole1.getId()));
            assertEquals("name field not created correctly", "roleName", role.getName());
            roleDAO.remove(UUIDUtil.toUUID(restRole1.getId()));
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    @Test
    public void deleteId() throws Exception {
        //Add to database directly with DAO
        RoleDAO roleDAO = manager.getRoleDAO();
        Role role = roleDAO.create(new Role("roleName"));

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            roleDAO.refresh(role);
            roleDAO.get(role.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            roleDAO.remove(role.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {
        //Add to database directly with DAO
        RoleDAO roleDAO = manager.getRoleDAO();
        Role role = roleDAO.create(new Role("roleName"));

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(role.getName())))
                .andReturn();

        //Clean up database for other tests
        roleDAO.remove(role.getUuid());
    }

    @Ignore
    @Test
    public void putId() throws Exception {
        //Add to database directly with DAO
        RoleDAO roleDAO = manager.getRoleDAO();
        Role role = roleDAO.create(new Role("roleNameTest"));

        //Change a field of the object that has to be updated
        role.setName("roleNameChanged");
        RESTRole restRole = new RESTRole(role);
        //Perform the put request to update the object and check the fields of the returned object
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restRole))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restRole.getName())))
                .andReturn();

        //Test if changes actually went in effect in the database
        try {
            roleDAO.refresh(role);
            Role role1 = roleDAO.get(role.getUuid());
            assertEquals("name field not updated correctly", "roleNameChanged", role1.getName());
            //Clean up database for other tests
            roleDAO.remove(role.getUuid());
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }
}