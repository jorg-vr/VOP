package spring.controller.insurance;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import model.insurance.Contract;
import model.insurance.Surety;
import model.insurance.VehicleInsurance;
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
import spring.controller.AuthUtil;
import spring.controller.RESTFunctionController;
import spring.controller.TestUtil;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTFunction;
import util.UUIDUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tjupo on 14/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTVehicleInsuranceControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTVehicleInsuranceController())
            .addPlaceholderValue("path.contracts", "contracts")
            .addPlaceholderValue("path.vehicle_insurances", "vehicle_insurances")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Vehicle vehicle;
    private static Surety surety;
    private static Contract contract;
    private static VehicleType vehicleType;
    private static Fleet fleet;
    private static Customer customer;
    private static LocalDate localDate = LocalDate.of(2016, 1, 1);
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            vehicle = new V
            customer = new Customer(address, "04789456123", "anita", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer = manager.getCustomerDAO().create(customer);
            role1 = new Role("role1");
            role1 = manager.getRoleDAO().create(role1);
            role2 = new Role("role2");
            role2 = manager.getRoleDAO().create(role2);
            user = new User("firstname", "lastname", "email@a.com", "password");
            user = manager.getUserDAO().create(user);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            manager.getCustomerDAO().remove(customer.getUuid());
            manager.getRoleDAO().remove(role1.getUuid());
            manager.getRoleDAO().remove(role2.getUuid());
            manager.getUserDAO().remove(user.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        Function function1 = create(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));
        Function function2 = create(new Function(customer, role2, user, LocalDateTime.of(2015, 7, 15, 0, 0), LocalDateTime.of(2201, 8, 3, 0, 0)));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(2)));
        } catch (Exception e) {
            remove(function1.getUuid());
            remove(function2.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(function1.getUuid());
        remove(function2.getUuid());
    }

    @Test
    public void post() throws Exception {

        RESTFunction restFunction = new RESTFunction(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFunction)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTFunction.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.company", equalTo(restFunction.getCompany())))
                    .andExpect(jsonPath("$.companyName", equalTo(restFunction.getCompanyName())))
                    .andExpect(jsonPath("$.role", equalTo(restFunction.getRole())))
                    .andExpect(jsonPath("$.roleName", equalTo(restFunction.getRoleName())))
                    .andExpect(jsonPath("$.user", equalTo(restFunction.getUser())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            Function function = get(restId);
            try {
                assertEquals("customer field not created correctly", customer, function.getCompany());
                assertEquals("role field not created correctly", role1, function.getRole());
                assertEquals("user field not created correctly", user, function.getUser());
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
        Function function = create(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(function.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(function.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        Function function = create(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.company", equalTo(UUIDUtil.UUIDToNumberString(function.getCompany().getUuid()))))
                    .andExpect(jsonPath("$.companyName", equalTo(function.getCompany().getName())))
                    .andExpect(jsonPath("$.role", equalTo(UUIDUtil.UUIDToNumberString(function.getRole().getUuid()))))
                    .andExpect(jsonPath("$.roleName", equalTo(function.getRole().getName())))
                    .andExpect(jsonPath("$.user", equalTo(UUIDUtil.UUIDToNumberString(function.getUser().getUuid()))));
        } catch (Exception e) {
            remove(function.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(function.getUuid());
    }

    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        Function function = create(new Function(customer, role1, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));

        //Change a field of the object that has to be updated
        function.setRole(role2);
        RESTFunction restFunction = new RESTFunction(function);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/users/" + UUIDUtil.UUIDToNumberString(user.getUuid()) + "/functions/{id}", UUIDUtil.UUIDToNumberString(function.getUuid()))
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
                    .andExpect(jsonPath("$.user", equalTo(restFunction.getUser())));
        } catch (Exception e) {
            remove(function.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            function = get(function.getUuid());
            try {
                assertEquals("customer field not updated correctly", customer, function.getCompany());
                assertEquals("role field not updated correctly", role2, function.getRole());
                assertEquals("user field not updated correctly", user, function.getUser());
            } finally {
                //Clean up database for other tests
                remove(function.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleInsuranceDao().remove(uuid);
        }
    }

    private VehicleInsurance create(VehicleInsurance vehicleInsurance) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleInsuranceDao().create(vehicleInsurance);
        }
    }

    private VehicleInsurance get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleInsuranceDao().get(uuid);
        }
    }
}
