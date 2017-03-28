package spring.controller;

import controller.AccountController;
import controller.CustomerController;
import controller.FunctionController;
import controller.PersonController;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.account.Function;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Person;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTRole;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jorg on 3/15/17.
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTRoleControllerTest {
    private MockMvc mvc= MockMvcBuilders.standaloneSetup(new RESTRoleController()).build();

    private static Address address;
    private static Customer customer;
    private static Person person;
    private static Account account;
    private static Function function;

    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider("unittest");
        try {
            address= new Address("mystreet","123","lala","12345","land");
            customer= new CustomerController().create(address,"04789456123","anita","123456789");
            person=new PersonController().createPerson("jon","doe","jon.doe@hotmail.com");
            account=new AccountController().createAccount("jon.doe@hotmail.com","054561dfs5f465",person.getUuid());
            function=new FunctionController().create(customer.getUuid(),"",account.getUuid(), LocalDateTime.now().minusMonths(12),LocalDateTime.now().plusMonths(12));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void afterTransaction() {
        try {
            new FunctionController().archive(function.getUuid());
            new AccountController().archive(account.getUuid());
            new PersonController().archive(person.getUuid());
            new CustomerController().archive(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        ProductionProvider.getInstance().close();
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total",greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTRole restRole=new RESTRole();
        restRole.setCompany(UUIDUtil.UUIDToNumberString(customer.getUuid()));
        restRole.setUser(UUIDUtil.UUIDToNumberString(account.getUuid()));

        MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/roles")
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restRole))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company",equalTo(restRole.getCompany())))
                .andExpect(jsonPath("$.user",equalTo(restRole.getUser()))).andReturn();
        RESTRole restRole1 =  TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(),RESTRole.class);
        mvc.perform(MockMvcRequestBuilders.delete("/roles/{id}",restRole1.getId()))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/roles/{id}",restRole1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getId() throws Exception {
        RESTRole restRole=new RESTRole();
        restRole.setCompany(UUIDUtil.UUIDToNumberString(customer.getUuid()));
        restRole.setUser(UUIDUtil.UUIDToNumberString(account.getUuid()));

        mvc.perform(MockMvcRequestBuilders.get("/roles/{id}",UUIDUtil.UUIDToNumberString(function.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company",equalTo(restRole.getCompany())))
                .andExpect(jsonPath("$.user",equalTo(restRole.getUser())))
                .andReturn();
    }

    @Test
    public void putId() throws Exception {
        RESTRole restRole=new RESTRole();
        restRole.setId(UUIDUtil.UUIDToNumberString( function.getUuid()));
        restRole.setCompany(UUIDUtil.UUIDToNumberString(customer.getUuid()));
        restRole.setFunction("");
        restRole.setUser(UUIDUtil.UUIDToNumberString(account.getUuid()));

        MvcResult result =mvc.perform(MockMvcRequestBuilders.put("/roles/{id}",UUIDUtil.UUIDToNumberString(function.getUuid()))
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restRole))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.company",equalTo(restRole.getCompany())))
                .andExpect(jsonPath("$.user",equalTo(restRole.getUser())))
                .andReturn();

    }
}