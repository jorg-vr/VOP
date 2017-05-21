package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.VehicleType;
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
import spring.model.RESTVehicleType;
import util.UUIDUtil;

import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tjupo on 07/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTVehicleTypeControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTVehicleTypeController())
            .addPlaceholderValue("path.vehicles", "vehicles")
            .addPlaceholderValue("path.types", "types")
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

    /**
     * GET /vehicles/types
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        VehicleType vehicleType = create(new VehicleType("type"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles/types")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)));
        } catch (Exception e) {
            remove(vehicleType.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicleType.getUuid());
    }

    /**
     * POST /vehicles/types
     *
     * @throws Exception
     */
    @Test
    public void post() throws Exception {

        RESTVehicleType restVehicleType = new RESTVehicleType(new VehicleType("type"));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/vehicles/types")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicleType)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicleType.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(restVehicleType.getName())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            VehicleType vehicleType = get(restId);
            try {
                assertEquals("type field not created correctly", "type", vehicleType.getType());
            } finally {
                remove(restId);
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /vehicles/types/{id}
     *
     * @throws Exception
     */
    @Test
    public void deleteId() throws Exception {

        //Add to database directly with DAO
        VehicleType vehicleType = create(new VehicleType("type"));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(vehicleType.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(vehicleType.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /vehicles/types/{id}
     *
     * @throws Exception
     */
    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        VehicleType vehicleType = create(new VehicleType("type"));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(vehicleType.getType())));
        } catch (Exception e) {
            remove(vehicleType.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicleType.getUuid());
    }

    /**
     * PUT /vehicles/types/{id}
     *
     * @throws Exception
     */
    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        VehicleType vehicleType = create(new VehicleType("type"));

        //Change a field of the object that has to be updated
        vehicleType.setType("typeChanged");
        RESTVehicleType restVehicleType = new RESTVehicleType(vehicleType);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restVehicleType))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name", equalTo(restVehicleType.getName())));
        } catch (AssertionError e) {
            remove(vehicleType.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            vehicleType = get(vehicleType.getUuid());
            try {
                assertEquals("type field not updated correctly", "typeChanged", vehicleType.getType());
            } finally {
                //Clean up database for other tests
                remove(vehicleType.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleTypeDAO().remove(uuid);
        }
    }

    private VehicleType create(VehicleType vehicleType) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleTypeDAO().create(vehicleType);
        }
    }

    private VehicleType get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleTypeDAO().get(uuid);
        }
    }
}
