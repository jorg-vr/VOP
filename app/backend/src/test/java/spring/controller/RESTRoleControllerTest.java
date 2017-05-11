package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Role;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTRole;
import util.UUIDUtil;

import java.util.UUID;

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
        Role role = create(new Role("roleName"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/auth/roles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 2 instead of 1 because there's 1 extra Role in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(2)));
        } catch (Exception e) {
            remove(role.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(role.getUuid());
    }

    //TODO compare permissions between database object and restobject (low priority)
    @Test
    public void post() throws Exception {

        RESTRole restRole = new RESTRole(new Role("roleName"));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/auth/roles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restRole)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTRole.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions.andExpect(jsonPath("$.name", equalTo(restRole.getName())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            Role role = get(restId);
            try {
                assertEquals("name field not created correctly", "roleName", role.getName());
            } finally {
                remove(restId);
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    @Test
    public void deleteId() throws Exception {

        //Add to database directly with DAO
        Role role = create(new Role("roleName"));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(role.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(role.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        Role role = create(new Role("roleName"));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(role.getName())));
        } catch (Exception e) {
            remove(role.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(role.getUuid());
    }

    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        Role role = create(new Role("roleName"));

        //Change a field of the object that has to be updated
        role.setName("roleNameChanged");
        RESTRole restRole = new RESTRole(role);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/auth/roles/{id}", UUIDUtil.UUIDToNumberString(role.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restRole))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(restRole.getName())));
        } catch (Exception e) {
            remove(role.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            role = get(role.getUuid());
            try {
                assertEquals("name field not updated correctly", "roleNameChanged", role.getName());
            } finally {
                //Clean up database for other tests
                remove(role.getUuid());
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