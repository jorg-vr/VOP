package spring.controller;

import controller.CustomerController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTAddress;
import spring.model.RESTCompany;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



/**
 * Created by jorg on 3/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTCompanyControllerTest {



    private MockMvc mvc= MockMvcBuilders.standaloneSetup(new RESTCompanyController()).build();

    private static Address address;
    private static Customer customer;

    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider("test");
        //ProductionProvider.initializeProvider(false);
        try {
            address= new Address("mystreet","123","lala","12345","land");
            customer= new CustomerController().create(address,"04789456123","anita","123456789");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() {
        try {
            new CustomerController().archive(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        //ProductionProvider.getInstance().close();
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total",greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTCompany restCompany=new RESTCompany(null,"frank","sinatra","0123456",new RESTAddress("a","b","c","d","e"),null,null,null,null);
        MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/companies").header("Content-Type","application/json").content(TestUtil.convertObjectToJsonBytes(restCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("frank")))
                .andExpect(jsonPath("$.vatNumber",equalTo("sinatra")))
                .andExpect(jsonPath("$.phoneNumber",equalTo("0123456")))
                .andExpect(jsonPath("$.address.country",equalTo("a")))
                .andExpect(jsonPath("$.address.city",equalTo("b")))
                .andExpect(jsonPath("$.address.street",equalTo("c")))
                .andExpect(jsonPath("$.address.houseNumber",equalTo("d")))
                .andExpect(jsonPath("$.address.postalCode",equalTo("e"))).andReturn();
        RESTCompany restCompany1 =  TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(),RESTCompany.class);
        mvc.perform(MockMvcRequestBuilders.delete("/companies/{id}",restCompany1.getId()))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/companies/{id}",restCompany1.getId()))
                .andExpect(status().isNotFound());
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