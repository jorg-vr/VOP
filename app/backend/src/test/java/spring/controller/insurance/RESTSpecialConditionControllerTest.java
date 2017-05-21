package spring.controller.insurance;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.insurance.SpecialCondition;
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
import spring.controller.AuthUtil;
import spring.controller.TestUtil;
import spring.exceptions.MyExceptionHandler;
import spring.model.insurance.RESTSpecialCondition;
import util.UUIDUtil;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 19/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTSpecialConditionControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTSpecialConditionController())
            .addPlaceholderValue("path.special_conditions", "special-conditions")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        SpecialCondition specialCondition = create(new SpecialCondition("specialTitle", "specialText", "specialReferenceCode"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/special-conditions")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)));
        } catch (Exception e) {
            remove(specialCondition.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(specialCondition.getUuid());
    }

    //TODO compare permissions between database object and restobject (low priority)
    @Test
    public void post() throws Exception {

        RESTSpecialCondition restSpecialCondition = new RESTSpecialCondition(new SpecialCondition("specialTitle", "specialText", "specialReferenceCode"));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/special-conditions")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restSpecialCondition)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTSpecialCondition.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions.andExpect(jsonPath("$.title", equalTo(restSpecialCondition.getTitle())))
                    .andExpect(jsonPath("$.text", equalTo(restSpecialCondition.getText())))
                    .andExpect(jsonPath("$.referenceCode", equalTo(restSpecialCondition.getReferenceCode())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            SpecialCondition specialCondition = get(restId);
            try {
                assertEquals("title field not created correctly", "specialTitle", specialCondition.getTitle());
                assertEquals("text field not created correctly", "specialText", specialCondition.getText());
                assertEquals("referenceCode field not created correctly", "specialReferenceCode", specialCondition.getReferenceCode());
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
        SpecialCondition specialCondition = create(new SpecialCondition("specialTitle", "specialText", "specialReferenceCode"));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/special-conditions/{id}", UUIDUtil.UUIDToNumberString(specialCondition.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(specialCondition.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(specialCondition.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        SpecialCondition specialCondition = create(new SpecialCondition("specialTitle", "specialText", "specialReferenceCode"));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/special-conditions/{id}", UUIDUtil.UUIDToNumberString(specialCondition.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", equalTo(specialCondition.getTitle())))
                    .andExpect(jsonPath("$.text", equalTo(specialCondition.getText())))
                    .andExpect(jsonPath("$.referenceCode", equalTo(specialCondition.getReferenceCode())));
        } catch (Exception e) {
            remove(specialCondition.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(specialCondition.getUuid());
    }

    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        SpecialCondition specialCondition = create(new SpecialCondition("specialTitle", "specialText", "specialReferenceCode"));

        //Change a field of the object that has to be updated
        specialCondition.setText("specialTextChanged");
        RESTSpecialCondition restSpecialCondition = new RESTSpecialCondition(specialCondition);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/special-conditions/{id}", UUIDUtil.UUIDToNumberString(specialCondition.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restSpecialCondition))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title", equalTo(restSpecialCondition.getTitle())))
                    .andExpect(jsonPath("$.text", equalTo(restSpecialCondition.getText())))
                    .andExpect(jsonPath("$.referenceCode", equalTo(restSpecialCondition.getReferenceCode())));
        } catch (AssertionError e) {
            remove(specialCondition.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            specialCondition = get(specialCondition.getUuid());
            try {
                assertEquals("title field not updated correctly", "specialTitle", specialCondition.getTitle());
                assertEquals("text field not updated correctly", "specialTextChanged", specialCondition.getText());
                assertEquals("referenceCode field not updated correctly", "specialReferenceCode", specialCondition.getReferenceCode());
            } finally {
                //Clean up database for other tests
                remove(specialCondition.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSpecialConditionDao().remove(uuid);
        }
    }

    private SpecialCondition create(SpecialCondition specialCondition) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getSpecialConditionDao().create(specialCondition);
        }
    }

    private SpecialCondition get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getSpecialConditionDao().get(uuid);
        }
    }
}
