package spring.controller;

import controller.CustomerController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.Customer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import spring.model.RESTAddress;
import spring.model.RESTCompany;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



/**
 * Created by jorg on 3/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTCompanyControllerTest {



    private MockMvc mvc;

    private static Address address;
    private static Customer customer;

    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider(false);
        try {
            //TODO find out why this doesn't work
            //address= ProductionProvider.getInstance().getAddressDao().create("mystreet","123","lala","12345","land");
            //address=new Address("mystreet","123","lala","12345","land");
            customer= new CustomerController().create(null,"04789456123","anita","123456789");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @Before
    public void before(){
        mvc= MockMvcBuilders.standaloneSetup(new RESTCompanyController()).build();
    }
    @AfterClass
    public static void afterTransaction() {
        try {
            new CustomerController().archive(customer.getUuid());
//            ProductionProvider.getInstance().getAddressDao().remove(address.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total",greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTCompany restCompany=new RESTCompany(null,"frank","sinatra","0123456",new RESTAddress("a","b","c","d","e"),null,null,null,null);
        mvc.perform(MockMvcRequestBuilders.post("/companies").header("Content-Type","application/json").content(TestUtil.convertObjectToJsonBytes(restCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",equalTo("frank")))
                .andExpect(jsonPath("$.name",equalTo("frank")));
    }
    @Ignore
    @Test
    public void getId() throws Exception {

    }
    @Ignore
    @Test
    public void putId() throws Exception {

    }
    @Ignore
    @Test
    public void deleteId() throws Exception {

    }

}