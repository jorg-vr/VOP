package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.UserDAO;
import model.account.User;
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
import spring.model.RESTUser;
import util.UUIDUtil;

import static org.hamcrest.Matchers.*;
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
        UserDAO userDAO = manager.getUserDAO();
        User user = userDAO.create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/users")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 2 instead of 1 because there's 1 extra User in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                    .andExpect(jsonPath("$.total", equalTo(2)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            userDAO.remove(user.getUuid());
        }

    }

    //Look to test password
    @Test
    public void post() throws Exception {
        RESTUser restUser = new RESTUser("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest");
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restUser))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(restUser.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(restUser.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(restUser.getEmail())))
                .andExpect(jsonPath("$.password", equalTo(null)))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTUser restUser1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTUser.class);
        UserDAO userDAO = manager.getUserDAO();
        try {
            User user = userDAO.get(UUIDUtil.toUUID(restUser1.getId()));
            assertEquals("firstname field not created correctly", "firstNameTest", user.getFirstName());
            assertEquals("lastname field not created correctly", "lastNameTest", user.getLastName());
            assertEquals("email field not created correctly", "emailTest@mail.com", user.getEmail());
            assertEquals("password field not created correctly", "passwordTest", user.getPassword());
            userDAO.remove(UUIDUtil.toUUID(restUser1.getId()));
        } catch (DataAccessException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    @Test
    public void deleteId() throws Exception {
        //Add to database directly with DAO
        UserDAO userDAO = manager.getUserDAO();
        User user = userDAO.create(new User("firstNameTest", "lastNameTest", "emailTestt@mail.com", "passwordTest"));

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            userDAO.refresh(user);
            userDAO.get(user.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            userDAO.remove(user.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {
        //Add to database directly with DAO
        UserDAO userDAO = manager.getUserDAO();
        User user = userDAO.create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.password", equalTo(null)))
                .andReturn();

        //Clean up database for other tests
        userDAO.remove(user.getUuid());
    }

    @Ignore
    @Test
    public void putId() throws Exception {
        //Add to database directly with DAO
        UserDAO userDAO = manager.getUserDAO();
        User user = userDAO.create(new User("firstNameTest", "lastNameTest", "emailTest@mail.com", "passwordTest"));

        //Change a field of the object that has to be updated
        user.setLastName("lastNameTestChanged");
        RESTUser restUser = new RESTUser(user);
        //Perform the put request to update the object and check the fields of the returned object
        try {
            MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/users/{id}", UUIDUtil.UUIDToNumberString(user.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restUser))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(restUser.getFirstName())))
                    .andExpect(jsonPath("$.lastName", equalTo(restUser.getLastName())))
                    .andExpect(jsonPath("$.email", equalTo(restUser.getEmail())))
                    .andExpect(jsonPath("$.password", equalTo(restUser.getPassword())))
                    .andReturn();
        } catch (Exception e) {
            e.printStackTrace();
            userDAO.remove(user.getUuid());
        }

        //Test if changes actually went in effect in the database
        try {
            userDAO.refresh(user);
            User user1 = userDAO.get(user.getUuid());
            assertEquals("firstname field not updated correctly", "firstNameTest", user1.getFirstName());
            assertEquals("lastname field not updated correctly", "lastNameTestChanged", user1.getLastName());
            assertEquals("email field not updated correctly", "emailTest@mail.com", user1.getEmail());
            assertEquals("password field not updated correctly", "passwordTest", user1.getPassword());
            //Clean up database for other tests
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        } finally {
            userDAO.remove(user.getUuid());
        }
    }
}
