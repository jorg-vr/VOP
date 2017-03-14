package spring.controller;

import controller.CustomerController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.Customer;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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

    private Address address;
    private Customer customer;

    @Before
    public void setup() {
        ProductionProvider.initializeProvider(false);
        mvc= MockMvcBuilders.standaloneSetup(new RESTCompanyController()).build();
    }

    @BeforeTransaction
    void beforeTransaction() {
        // logic to be executed before a transaction is started
        try {
            address= ProductionProvider.getInstance().getAddressDao().create("mystreet","123","lala","12345","land");
            customer= new CustomerController().create(address,"04789456123","anita","123456789");
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @AfterTransaction
    void afterTransaction() {
        try {
            new CustomerController().archive(customer.getUuid());
            ProductionProvider.getInstance().getAddressDao().remove(address.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void get() throws Exception {
        MvcResult result = mvc.perform(MockMvcRequestBuilders.get("/companies"))
                .andExpect(status().isOk())
                .andReturn();
                //.andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))));
        System.out.println("result: "+result.getResponse().getContentAsString());

    }
    @Ignore
    @Test
    public void post() throws Exception {

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