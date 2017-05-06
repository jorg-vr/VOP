package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.FunctionDAO;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.hibernate.UnresolvableObjectException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTFunction;
import util.UUIDUtil;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 4/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTFunctionControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTFunctionController())
            .addPlaceholderValue("path.users", "users")
            .addPlaceholderValue("path.functions", "functions")
            .build();

    private static Customer customer;
    private static Role role1, role2;
    private static User user;
    private static String[] authPair;

    private static DAOManager manager;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
        try {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer = new Customer(address, "04789456123", "anita", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer = manager.getCustomerDAO().create(customer);
            role1 = new Role("role1");
            role1 = manager.getRoleDAO().create(role1);
            role2 = new Role("role2");
            role2 = manager.getRoleDAO().create(role2);
            user = new User("firstname", "lastname", "email", "password");
            user = manager.getUserDAO().create(user);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try {
            manager.getCustomerDAO().remove(customer.getUuid());
            manager.getRoleDAO().remove(role1.getUuid());
            manager.getRoleDAO().remove(role2.getUuid());
            manager.getUserDAO().remove(user.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        manager.close();
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function1 = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function1 = functionDAO.create(function1);
        Function function2 = new Function(customer, role2, user, LocalDateTime.of(2015, 7, 15, 0, 0), LocalDateTime.of(2201, 8, 3, 0, 0));
        function2 = functionDAO.create(function2);

        try {
            mvc.perform(MockMvcRequestBuilders.get("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 3 instead of 2 because there's 1 extra Function in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(3))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(3)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            functionDAO.remove(function1.getUuid());
            functionDAO.remove(function2.getUuid());
        }

    }

    @Test
    public void post() throws Exception {
        RESTFunction restFunction = new RESTFunction(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFunction))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company", equalTo(restFunction.getCompany())))
                .andExpect(jsonPath("$.companyName", equalTo(restFunction.getCompanyName())))
                .andExpect(jsonPath("$.role", equalTo(restFunction.getRole())))
                .andExpect(jsonPath("$.roleName", equalTo(restFunction.getRoleName())))
                .andExpect(jsonPath("$.user", equalTo(restFunction.getUser())))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTFunction restFunction1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTFunction.class);
        FunctionDAO functionDAO = manager.getFunctionDAO();
        try {
            Function function = functionDAO.get(UUIDUtil.toUUID(restFunction1.getId()));
            assertEquals("customer field not created correctly", customer, function.getCompany());
            assertEquals("role field not created correctly", role1, function.getRole());
            assertEquals("user field not created correctly", user, function.getUser());
            functionDAO.remove(UUIDUtil.toUUID(restFunction1.getId()));
        } catch (DataAccessException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    @Test
    public void deleteId() throws Exception {
        //Add to database directly with DAO
        Function function = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        FunctionDAO functionDAO = manager.getFunctionDAO();
        function = functionDAO.create(function);

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            functionDAO.refresh(function);
            functionDAO.get(function.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            functionDAO.remove(function.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {
        //Add to database directly with DAO
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function = functionDAO.create(function);

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company", equalTo(UUIDUtil.UUIDToNumberString(function.getCompany().getUuid()))))
                .andExpect(jsonPath("$.companyName", equalTo(function.getCompany().getName())))
                .andExpect(jsonPath("$.role", equalTo(UUIDUtil.UUIDToNumberString(function.getRole().getUuid()))))
                .andExpect(jsonPath("$.roleName", equalTo(function.getRole().getName())))
                .andExpect(jsonPath("$.user", equalTo(UUIDUtil.UUIDToNumberString(function.getUser().getUuid()))))
                .andReturn();

        //Clean up database for other tests
        functionDAO.remove(function.getUuid());
    }

    @Test
    public void putId() throws Exception {
        //Add to database directly with DAO
        FunctionDAO functionDAO = manager.getFunctionDAO();
        Function function = new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0));
        function = functionDAO.create(function);

        //Change a field of the object that has to be updated
        function.setRole(role2);
        RESTFunction restFunction = new RESTFunction(function);
        //Perform the put request to update the object and check the fields of the returned object
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFunction))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company", equalTo(restFunction.getCompany())))
                .andExpect(jsonPath("$.companyName", equalTo(restFunction.getCompanyName())))
                .andExpect(jsonPath("$.role", equalTo(restFunction.getRole())))
                .andExpect(jsonPath("$.roleName", equalTo(restFunction.getRoleName())))
                .andExpect(jsonPath("$.user", equalTo(restFunction.getUser())))
                .andReturn();

        //Test if changes actually went in effect in the database
        try {
            functionDAO.refresh(function);
            Function function1 = functionDAO.get(function.getUuid());
            assertEquals("customer field not updated correctly", customer, function1.getCompany());
            assertEquals("role field not updated correctly", role2, function1.getRole());
            assertEquals("user field not updated correctly", user, function1.getUser());
            //Clean up database for other tests
            functionDAO.remove(function.getUuid());
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }
}
