package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.*;
import model.account.*;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTAuth;
import spring.model.RESTUser;
import util.UUIDUtil;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tjupo on 07/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTUserMeControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTUserMeController())
            .addPlaceholderValue("path.users", "users")
            .addPlaceholderValue("path.functions", "functions")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static User user;
    private static Role role1, role2;
    private static Customer customer;

    private static String authString;

    private static DAOManager manager;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();

        try {
            role1 = new Role("roleName1-UserMeTest");
            addActions(role1, Resource.USER);
            addActions(role1, Resource.FUNCTION);
            role1 = manager.getRoleDAO().create(role1);

            role2 = new Role("roleName2-UserMeTest");
            addActions(role2, Resource.FLEET);
            role2 = manager.getRoleDAO().create(role2);

            Address address = new Address("street", "1", "town", "8530", "country");
            customer = manager.getCustomerDAO().create(new Customer(address, "123", "customerName-AuthUtil", "456-AuthUtil", Periodicity.QUARTERLY, Periodicity.QUARTERLY));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try {
            CustomerDAO customerDAO = manager.getCustomerDAO();
            RoleDAO roleDAO = manager.getRoleDAO();

            customerDAO.remove(customer.getUuid());
            roleDAO.remove(role1.getUuid());
            roleDAO.remove(role2.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        manager.close();
        ProductionProvider.getInstance().close();
    }

    @Before
    public void beforeTestMethod() throws Exception {
        UserDAO userDAO = manager.getUserDAO();

        user = userDAO.create(new User("firstname-UserMeTest", "lastname-UserMeTest", "admin@login.com-UserMeTest", "admin"));

        //Gets an authorization token for the created user
        RESTAuth restAuth = new RESTAuth();
        restAuth.setLogin("admin@login.com-UserMeTest");
        restAuth.setPassword("admin");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTAuthController())
                .addPlaceholderValue("path.auth", "auth")
                .addPlaceholderValue("path.login", "login")
                .addPlaceholderValue("path.refresh", "refresh")
                .build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth/login").header("Content-Type", "application/json").content(TestUtil.convertObjectToJsonBytes(restAuth))).andReturn();
        authString = result.getResponse().getContentAsString();
    }

    @After
    public void afterTestMethod() throws Exception {
        UserDAO userDAO = manager.getUserDAO();
        userDAO.remove(user.getUuid());
    }

    /**
     * GET /users/me
     *
     * @throws Exception
     */
    @Test
    public void getMe() throws Exception {
        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/users/me")
                .header("Authorization", authString)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(user.getFirstName())))
                .andExpect(jsonPath("$.lastName", equalTo(user.getLastName())))
                .andExpect(jsonPath("$.email", equalTo(user.getEmail())))
                .andExpect(jsonPath("$.password", equalTo(user.getPassword())))
                .andReturn();
    }

    /**
     * PUT /users/me
     *
     * @throws Exception
     */
    @Ignore
    @Test
    public void putMe() throws Exception {
        //Add function to database directly with DAO
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function = functionDAO.create(function);
        //Change a field of the object that has to be updated
        user.setLastName("lastNameTestChanged");
        RESTUser restUser = new RESTUser(user);
        //Perform the put request to update the object and check the fields of the returned object
        try {
            MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/users/me")
                    .header("Content-Type", "application/json")
                    .header("Authorization", authString)
                    .header("Function", UUIDUtil.UUIDToNumberString(function.getUuid()))
                    .content(TestUtil.convertObjectToJsonBytes(restUser))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", equalTo(restUser.getFirstName())))
                    .andExpect(jsonPath("$.lastName", equalTo(restUser.getLastName())))
                    .andExpect(jsonPath("$.email", equalTo(restUser.getEmail())))
                    .andExpect(jsonPath("$.password", equalTo(restUser.getPassword())))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            functionDAO.remove(function.getUuid());
        }

        //Test if changes actually went in effect in the database
        try {
            UserDAO userDAO = manager.getUserDAO();
            userDAO.refresh(user);
            User user1 = userDAO.get(user.getUuid());
            assertEquals("firstname field not updated correctly", "firstname-UserMeTest", user1.getFirstName());
            assertEquals("lastname field not updated correctly", "lastNameTestChanged", user1.getLastName());
            assertEquals("email field not updated correctly", "admin@login.com-UserMeTest", user1.getEmail());
            assertEquals("password field not updated correctly", "admin", user1.getPassword());
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    /**
     * GET /users/me/functions
     *
     * @throws Exception
     */
    @Test
    public void getFunctions() throws Exception {
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function1 = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function1 = functionDAO.create(function1);
        Function function2 = new Function(customer, role2, user, LocalDateTime.of(2015, 7, 15, 0, 0), LocalDateTime.of(2201, 8, 3, 0, 0));
        function2 = functionDAO.create(function2);

        try {
            mvc.perform(MockMvcRequestBuilders.get("/users/me/functions")
                    .header("Authorization", authString)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                    .andExpect(jsonPath("$.total", equalTo(2)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            functionDAO.remove(function1.getUuid());
            functionDAO.remove(function2.getUuid());
        }

    }

    /**
     * GET /users/me/functions/{id}
     *
     * @throws Exception
     */
    @Test
    public void getFunctionId() throws Exception {
        //Add to database directly with DAO
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function = functionDAO.create(function);

        try {
            //Attempt to retrieve the object with the given id
            mvc.perform(MockMvcRequestBuilders.get("/users/me/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                    .header("Authorization", authString)
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.company", equalTo(UUIDUtil.UUIDToNumberString(function.getCompany().getUuid()))))
                    .andExpect(jsonPath("$.companyName", equalTo(function.getCompany().getName())))
                    .andExpect(jsonPath("$.role", equalTo(UUIDUtil.UUIDToNumberString(function.getRole().getUuid()))))
                    .andExpect(jsonPath("$.roleName", equalTo(function.getRole().getName())))
                    .andExpect(jsonPath("$.user", equalTo(UUIDUtil.UUIDToNumberString(function.getUser().getUuid()))))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            functionDAO.remove(function.getUuid());
        }
    }

    private static void addActions(Role role, Resource resource) {
        role.setAccess(resource, Action.READ_ALL);
        role.setAccess(resource, Action.UPDATE_ALL);
        role.setAccess(resource, Action.REMOVE_ALL);
        role.setAccess(resource, Action.CREATE_ALL);
    }
}
