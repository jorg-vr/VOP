package spring.controller;

import controller.CustomerController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.identity.Address;
import model.identity.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * Created by jorg on 3/14/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
public class RESTCompanyControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;
    Address address;
    Customer customer;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @BeforeTransaction
    void beforeTransaction() {
        // logic to be executed before a transaction is started
        address= ProductionProvider.getInstance().getAddressDao().create("mystreet","123","lala","12345","land";)
        try {
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
        mvc.perform()
    }

    @Test
    public void post() throws Exception {

    }

    @Test
    public void getId() throws Exception {

    }

    @Test
    public void putId() throws Exception {

    }

    @Test
    public void deleteId() throws Exception {

    }

}