package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.identity.Person;
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
import spring.model.RESTUser;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by jorg on 3/15/17.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTUserControllerTest {
    /*
    private MockMvc mvc= MockMvcBuilders.standaloneSetup(new RESTUserController()).build();

    private static Person person;
    private static Account account;
    @BeforeClass
    public static void setup() {
        ProductionProvider.initializeProvider("unittest");
        try {
            person=new Person();
            person.setLastName("doe");
            person.setFirstName("jon");
            person.setEmail("jon.doe@hotmail.com");
            person=ProductionProvider.getInstance().getPersonDAO().create(person);
            account=new Account();
            account.setPerson(person);
            account.setLogin("jon.doe@hotmail.com");
            account.setHashedPassword("054561dfs5f465");
            account=ProductionProvider.getInstance().getAccountDao().create(account);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    @AfterClass
    public static void afterTransaction() {
        try {
            ProductionProvider.getInstance().getAccountDao().remove(account.getUuid());
            ProductionProvider.getInstance().getPersonDAO().remove(person.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        ProductionProvider.getInstance().close();
    }

    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(".data",hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$.total",greaterThanOrEqualTo(1)))
                .andReturn();

    }

    @Test
    public void post() throws Exception {
        RESTUser restUser=new RESTUser();
        restUser.setEmail("Mymail2@this.id");
        restUser.setFirstName("Tim");
        restUser.setLastName("Tom");
        restUser.setPassword("password012345");

        MvcResult result =mvc.perform(MockMvcRequestBuilders.post("/users")
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restUser))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email",equalTo(restUser.getEmail())))
                .andExpect(jsonPath("$.lastName",equalTo(restUser.getLastName())))
                .andExpect(jsonPath("$.firstName",equalTo(restUser.getFirstName())))
                .andExpect(jsonPath("$.password",equalTo(restUser.getPassword())))
                .andReturn();
        RESTUser restUser1 =  TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(),RESTUser.class);
        mvc.perform(MockMvcRequestBuilders.delete("/users/{id}",restUser1.getId()))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/users/{id}",restUser1.getId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getId() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/users/{id}",UUIDUtil.UUIDToNumberString(account.getUuid())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(UUIDUtil.UUIDToNumberString(account.getUuid()))))
                .andExpect(jsonPath("$.email",equalTo(person.getEmail())))
                .andExpect(jsonPath("$.lastName",equalTo(person.getLastName())))
                .andExpect(jsonPath("$.firstName",equalTo(person.getFirstName())))
                .andExpect(jsonPath("$.password",equalTo(account.getHashedPassword())))
                .andReturn();
    }

    @Test
    public void putId() throws Exception {
        RESTUser restUser=new RESTUser();
        restUser.setId(UUIDUtil.UUIDToNumberString(account.getUuid()));
        restUser.setEmail(person.getEmail());
        restUser.setFirstName(person.getFirstName());
        restUser.setLastName("Tom");
        restUser.setPassword(account.getHashedPassword());

        MvcResult result =mvc.perform(MockMvcRequestBuilders.put("/users/{id}",UUIDUtil.UUIDToNumberString(account.getUuid()))
                .header("Content-Type","application/json")
                .content(TestUtil.convertObjectToJsonBytes(restUser))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",equalTo(restUser.getId())))
                .andExpect(jsonPath("$.email",equalTo(restUser.getEmail())))
                .andExpect(jsonPath("$.lastName",equalTo(restUser.getLastName())))
                .andExpect(jsonPath("$.firstName",equalTo(restUser.getFirstName())))
                .andExpect(jsonPath("$.password",equalTo(restUser.getPassword())))
                .andReturn();

    }
    */
}