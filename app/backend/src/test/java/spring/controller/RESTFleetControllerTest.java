package spring.controller;

import dao.database.ProductionProvider;

import dao.interfaces.DAOManager;
import dao.interfaces.AddressDAO;
import dao.interfaces.CustomerDAO;
import dao.exceptions.DataAccessException;
import dao.interfaces.FleetDAO;

import dao.interfaces.*;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTFleet;
import util.UUIDUtil;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by jorg on 3/15/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {WebAppContext.class})
//@WebAppConfiguration
//@TestPropertySource(locations = {"classpath:/application.properties"})
public class RESTFleetControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTFleetController())
            .addPlaceholderValue("path.fleets", "fleets")
            .addPlaceholderValue("path.companies", "companies")
            .build();


    //@Autowired
    //private WebApplicationContext wac;

    private static Address address1, address2;
    private static Customer customer;
    private static Fleet fleet;
    private static String[] authPair;

    private static DAOManager manager;

    @BeforeClass
    public static void classSetup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
        try {
            AddressDAO addressDAO = manager.getAddressDao();
            CustomerDAO customerDAO = manager.getCustomerDAO();
            FleetDAO fleetDAO = manager.getFleetDAO();
            address1 = new Address();
            address1.setStreet("mystreet");
            address1.setStreetNumber("123");
            address1.setTown("lala");
            address1.setPostalCode("12345");
            address1.setCountry("land");
            address1 = addressDAO.create(address1);
            address2 = new Address();
            address2.setStreet("mystreet");
            address2.setStreetNumber("123");
            address2.setTown("lala");
            address2.setPostalCode("12345");
            address2.setCountry("land");
            address2 = addressDAO.create(address2);
            customer = new Customer();
            customer.setAddress(address1);
            customer.setPhoneNumber("04789456123");
            customer.setName("anita");
            customer.setBtwNumber("123456789");
            customer.setInvoicePeriodicity(Periodicity.QUARTERLY);
            customer.setStatementPeriodicity(Periodicity.QUARTERLY);
            customer = customerDAO.create(customer);
            fleet = new Fleet();
            fleet.setOwner(customer);
            fleet.setName("myFleet");
            fleet.setAddress(address2);
            fleet = fleetDAO.create(fleet);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    @AfterClass

    public static void afterTransaction() throws Exception {
        try {
            CustomerDAO customerDAO = manager.getCustomerDAO();
            FleetDAO fleetDAO = manager.getFleetDAO();
            fleetDAO.remove(fleet.getUuid());
            customerDAO.remove(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        manager.close();
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fleets?")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total", greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTFleet restFleet = new RESTFleet(null, UUIDUtil.UUIDToNumberString(customer.getUuid()), "newFleet", null, null, null, null);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/fleets")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFleet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company", equalTo(restFleet.getCompany())))
                .andDo(print())
                .andReturn();
        RESTFleet restFleet1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTFleet.class);
        mvc.perform(MockMvcRequestBuilders.delete("/fleets/{id}", restFleet1.getId())
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}", restFleet1.getId())
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(fleet.getName())))
                .andExpect(jsonPath("$.company", equalTo(UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid()))))
                .andReturn();
    }

    @Test
    public void putId() throws Exception {
        fleet.setName("newName");
        RESTFleet restFleet = new RESTFleet(UUIDUtil.UUIDToNumberString(fleet.getUuid()),
                UUIDUtil.UUIDToNumberString(customer.getUuid()),
                fleet.getName(), null, null, null, null);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restFleet))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company", equalTo(restFleet.getCompany())))
                .andReturn();
        //tests if changes ar preserved
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}", UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company", equalTo(restFleet.getCompany())))
                .andReturn();
    }

}