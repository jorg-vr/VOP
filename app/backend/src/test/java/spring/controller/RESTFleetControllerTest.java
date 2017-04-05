package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
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
import spring.model.RESTFleet;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jorg on 3/15/17.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTFleetControllerTest {


    private MockMvc mvc= MockMvcBuilders.standaloneSetup(new RESTFleetController()).build();

    private static Address address;
    private static Customer customer;
    private static Fleet fleet;

    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider("unittest");
        try {
            address= new Address("mystreet","123","lala","12345","land");
            customer= new Customer();
            customer.setAddress(address);
            customer.setName("anita");
            customer.setPhoneNumber("04789456123");
            customer.setBtwNumber("123456789");
            customer=ProductionProvider.getInstance().getCustomerDAO().create(customer);
            fleet=new Fleet();
            fleet.setOwner(customer);
            fleet.setName("myFleet");
            fleet=ProductionProvider.getInstance().getFleetDAO().create(fleet);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }
    @AfterClass
    public static void afterTransaction() {
        try {
            ProductionProvider.getInstance().getFleetDAO().remove(fleet.getUuid());
            ProductionProvider.getInstance().getCustomerDAO().remove(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fleets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total",greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTFleet restFleet=new RESTFleet(null,UUIDUtil.UUIDToNumberString(customer.getUuid()),"newFleet",null,null,null,null);
        MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/fleets").header("Content-Type","application/json").content(TestUtil.convertObjectToJsonBytes(restFleet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company",equalTo(restFleet.getCompany()))).andReturn();
        RESTFleet restFleet1 =  TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(),RESTFleet.class);
        mvc.perform(MockMvcRequestBuilders.delete("/fleets/{id}",restFleet1.getId()))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}",restFleet1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}",UUIDUtil.UUIDToNumberString(fleet.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(fleet.getName())))
                .andExpect(jsonPath("$.company",equalTo(UUIDUtil.UUIDToNumberString(fleet.getOwner().getUuid()))))
                .andReturn();
    }

    @Test
    public void putId() throws Exception {
        fleet.setName("newName");
        RESTFleet restFleet=new RESTFleet(UUIDUtil.UUIDToNumberString(fleet.getUuid()),
                UUIDUtil.UUIDToNumberString(customer.getUuid()),
                fleet.getName(),null,null,null,null);

        MvcResult result =mvc.perform(MockMvcRequestBuilders.put("/fleets/{id}",UUIDUtil.UUIDToNumberString(fleet.getUuid()))
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restFleet))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company",equalTo(restFleet.getCompany())))
                .andReturn();
        //tests if changes ar preserved
        mvc.perform(MockMvcRequestBuilders.get("/fleets/{id}",UUIDUtil.UUIDToNumberString(fleet.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.company",equalTo(restFleet.getCompany())))
                .andReturn();
    }
}