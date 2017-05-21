package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
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
import spring.model.RESTFleet;
import util.UUIDUtil;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jorg on 3/15/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTFleetControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTFleetController())
            .addPlaceholderValue("path.fleets", "fleets")
            .addPlaceholderValue("path.companies", "companies")
            .addPlaceholderValue("path.import", "import")
            .addPlaceholderValue("path.example", "example")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Customer customer;
    private static String[] authPair;

    @BeforeClass
    public static void classSetup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer = new Customer(address, "04789456123", "anita", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer = manager.getCustomerDAO().create(customer);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getCustomerDAO().remove(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        Address address = new Address("mystreet", "123", "lala", "12345", "land");
        Fleet fleet = create(new Fleet("myFleet", customer, address));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/fleets")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)));
        } catch (Exception e) {
            remove(fleet.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(fleet.getUuid());
    }

    @Test
    public void post() throws Exception {

        RESTFleet restFleet = new RESTFleet(null, UUIDUtil.UUIDToNumberString(customer.getUuid()), "newFleet", null, null, null, null);

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/fleets")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFleet)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTFleet.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(restFleet.getName())))
                    .andExpect(jsonPath("$.company", equalTo(restFleet.getCompany())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            Fleet fleet = get(restId);
            try {
                assertEquals("name field not created correctly", "newFleet", fleet.getName());
                assertEquals("customer field not created correctly", customer, fleet.getOwner());
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
        Address address = new Address("mystreet", "123", "lala", "12345", "land");
        Fleet fleet = create(new Fleet("myFleet", customer, address));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(fleet.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(fleet.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        Address address = new Address("mystreet", "123", "lala", "12345", "land");
        Fleet fleet = create(new Fleet("myFleet", customer, address));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(fleet.getName())))
                    .andExpect(jsonPath("$.company", equalTo(UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid()))));
        } catch (Exception e) {
            remove(fleet.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(fleet.getUuid());
    }

    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        Address address = new Address("mystreet", "123", "lala", "12345", "land");
        Fleet fleet = create(new Fleet("myFleet", customer, address));

        //Change a field of the object that has to be updated
        fleet.setName("newName");
        RESTFleet restFleet = new RESTFleet(fleet);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restFleet))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(restFleet.getName())))
                    .andExpect(jsonPath("$.company", equalTo(restFleet.getCompany())));
        } catch (AssertionError e) {
            remove(fleet.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            fleet = get(fleet.getUuid());
            try {
                assertEquals("name field not created correctly", "newName", fleet.getName());
                assertEquals("customer field not created correctly", customer, fleet.getOwner());
            } finally {
                //Clean up database for other tests
                remove(fleet.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFleetDAO().remove(uuid);
        }
    }

    private Fleet create(Fleet fleet) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFleetDAO().create(fleet);
        }
    }

    private Fleet get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFleetDAO().get(uuid);
        }
    }
}