package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTUser;
import util.UUIDUtil;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 6/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTUserControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTUserController())
            .addPlaceholderValue("path.users", "users")
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

        User user = create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/users")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 2 instead of 1 because there's 1 extra User in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                    .andExpect(jsonPath("$.total", equalTo(2)));
        } catch (Exception e) {
            remove(user.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(user.getUuid());
    }

    //Look to test password
    @Test
    public void post() throws Exception {

        RESTUser restUser = new RESTUser(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/users")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restUser)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTUser.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(jsonPath("$.firstName", equalTo(restUser.getFirstName())))
                    .andExpect(jsonPath("$.lastName", equalTo(restUser.getLastName())))
                    .andExpect(jsonPath("$.email", equalTo(restUser.getEmail())))
                    .andExpect(jsonPath("$.password", equalTo(restUser.getPassword())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            User user = get(restId);
            try {
                assertEquals("firstname field not created correctly", "firstNameTest", user.getFirstName());
                assertEquals("lastname field not created correctly", "lastNameTest", user.getLastName());
                assertEquals("email field not created correctly", "emailTest@mail.com", user.getEmail());
                assertEquals("password field not created correctly", "passwordTest", user.getPassword());
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
        User user = create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(user.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(user.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        User user = create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                    .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                    .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                    .andExpect(jsonPath("$.password", equalTo(user.getPassword())));
        } catch (Exception e) {
            remove(user.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(user.getUuid());
    }

    @Ignore
    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        User user = create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Change a field of the object that has to be updated
        user.setLastName("lastNameTestChanged");
        RESTUser restUser = new RESTUser(user);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restUser))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(restUser.getFirstName())))
                    .andExpect(jsonPath("$.lastName", equalTo(restUser.getLastName())))
                    .andExpect(jsonPath("$.email", equalTo(restUser.getEmail())))
                    .andExpect(jsonPath("$.password", equalTo(restUser.getPassword())));
        } catch (Exception e) {
            remove(user.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            user = get(user.getUuid());
            try {
            assertEquals("firstname field not updated correctly", "firstNameTest", user.getFirstName());
            assertEquals("lastname field not updated correctly", "lastNameTestChanged", user.getLastName());
            assertEquals("email field not updated correctly", "emailTest@mail.com", user.getEmail());
            assertEquals("password field not updated correctly", "passwordTest", user.getPassword());
            } finally {
                //Clean up database for other tests
                remove(user.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getUserDAO().remove(uuid);
        }
    }

    private User create(User user) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getUserDAO().create(user);
        }
    }

    private User get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getUserDAO().get(uuid);
        }
    }
}
