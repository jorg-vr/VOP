package spring.controller;

import controller.CustomerController;
import controller.FleetController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTAddress;
import spring.model.RESTCompany;
import spring.model.RESTFleet;
import spring.model.RESTVehicle;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jorg on 3/15/17.
 */
public class RESTFleetControllerTest {


    private MockMvc mvc= MockMvcBuilders.standaloneSetup(new RESTFleetController()).build();

    private static Address address;
    private static Customer customer;
    private static Fleet fleet;

    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider(false);
        try {
            //TODO find out why this doesn't work
            address= new Address("mystreet","123","lala","12345","land");
            customer= new CustomerController().create(address,"04789456123","anita","123456789");
            fleet=new FleetController().create(customer.getUuid(),"MyFleet");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void afterTransaction() {
        try {
            new FleetController().archive(fleet.getUuid());
            new CustomerController().archive(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
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
        MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/companies").header("Content-Type","application/json").content(TestUtil.convertObjectToJsonBytes(restFleet)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(restFleet.getName())))
                .andExpect(jsonPath("$.vatCompany",equalTo(restFleet.getCompany()))).andReturn();
        RESTFleet restFleet1 =  TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(),RESTFleet.class);
        mvc.perform(MockMvcRequestBuilders.delete("/fleets/{id}",restFleet1.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies/{id}",UUIDUtil.UUIDToNumberString(customer.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(customer.getName())))
                .andExpect(jsonPath("$.vatNumber",equalTo(customer.getBtwNumber())))
                .andExpect(jsonPath("$.phoneNumber",equalTo(customer.getPhoneNumber())))
                .andExpect(jsonPath("$.address.country",equalTo(address.getCountry())))
                .andExpect(jsonPath("$.address.city",equalTo(address.getTown())))
                .andExpect(jsonPath("$.address.street",equalTo(address.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber",equalTo(address.getStreetNumber())))
                .andExpect(jsonPath("$.address.postalCode",equalTo(address.getPostalCode()))).andReturn();
    }

    @Test
    public void putId() throws Exception {
        customer.setBtwNumber("new");
        RESTCompany restCompany=new RESTCompany(UUIDUtil.UUIDToNumberString(customer.getUuid()),
                customer.getName(),customer.getBtwNumber(),customer.getPhoneNumber(),
                new RESTAddress(address.getCountry(),address.getTown(),address.getStreet(),address.getStreetNumber(),address.getPostalCode()),
                null,null,null,null);

        MvcResult result =mvc.perform(MockMvcRequestBuilders.put("/companies/{id}",UUIDUtil.UUIDToNumberString(customer.getUuid()))
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restCompany))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(customer.getName())))
                .andExpect(jsonPath("$.vatNumber",equalTo(customer.getBtwNumber())))
                .andExpect(jsonPath("$.phoneNumber",equalTo(customer.getPhoneNumber())))
                .andExpect(jsonPath("$.address.country",equalTo(address.getCountry())))
                .andExpect(jsonPath("$.address.city",equalTo(address.getTown())))
                .andExpect(jsonPath("$.address.street",equalTo(address.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber",equalTo(address.getStreetNumber())))
                .andExpect(jsonPath("$.address.postalCode",equalTo(address.getPostalCode())))
                .andReturn();
        //tests if changes ar preserved
        mvc.perform(MockMvcRequestBuilders.get("/companies/{id}",UUIDUtil.UUIDToNumberString(customer.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo(customer.getName())))
                .andExpect(jsonPath("$.vatNumber",equalTo(customer.getBtwNumber())))
                .andExpect(jsonPath("$.phoneNumber",equalTo(customer.getPhoneNumber())))
                .andExpect(jsonPath("$.address.country",equalTo(address.getCountry())))
                .andExpect(jsonPath("$.address.city",equalTo(address.getTown())))
                .andExpect(jsonPath("$.address.street",equalTo(address.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber",equalTo(address.getStreetNumber())))
                .andExpect(jsonPath("$.address.postalCode",equalTo(address.getPostalCode()))).andReturn();
    }
}