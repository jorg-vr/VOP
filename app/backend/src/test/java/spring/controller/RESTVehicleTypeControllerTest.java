package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.interfaces.DataAccessException;
import dao.interfaces.RoleDAO;
import dao.interfaces.VehicleTypeDAO;
import model.account.Role;
import model.fleet.VehicleType;
import org.hibernate.UnresolvableObjectException;
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
import spring.model.RESTRole;
import spring.model.RESTVehicleType;
import util.UUIDUtil;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
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

    private static DAOManager manager;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        manager.close();
        ProductionProvider.getInstance().close();
    }

    /**
     * GET /vehicles/types
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();
        VehicleType vehicleType = vehicleTypeDAO.create(new VehicleType("type"));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles/types")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            vehicleTypeDAO.remove(vehicleType.getUuid());
        }

    }

    /**
     * POST /vehicles/types
     * @throws Exception
     */
    @Test
    public void post() throws Exception {
        RESTVehicleType restVehicleType = new RESTVehicleType(new VehicleType("type"));
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vehicles/types")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicleType))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restVehicleType.getName())))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTVehicleType restVehicleType1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicleType.class);
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();

        try {
            VehicleType vehicleType = vehicleTypeDAO.get(UUIDUtil.toUUID(restVehicleType1.getId()));
            assertEquals("type field not created correctly", "type", vehicleType.getType());
            vehicleTypeDAO.remove(UUIDUtil.toUUID(restVehicleType1.getId()));
        } catch (DataAccessException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /vehicles/types/{id}
     * @throws Exception
     */
    @Test
    public void deleteId() throws Exception {
        //Add to database directly with DAO
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();
        VehicleType vehicleType = vehicleTypeDAO.create(new VehicleType("type"));

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            vehicleTypeDAO.refresh(vehicleType);
            vehicleTypeDAO.get(vehicleType.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            vehicleTypeDAO.remove(vehicleType.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /vehicles/types/{id}
     * @throws Exception
     */
    @Test
    public void getId() throws Exception {
        //Add to database directly with DAO
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();
        VehicleType vehicleType = vehicleTypeDAO.create(new VehicleType("type"));

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(vehicleType.getType())))
                .andReturn();

        //Clean up database for other tests
        vehicleTypeDAO.remove(vehicleType.getUuid());
    }

    /**
     * PUT /vehicles/types/{id}
     * @throws Exception
     */
    @Test
    public void putId() throws Exception {
        //Add to database directly with DAO
        VehicleTypeDAO vehicleTypeDAO = manager.getVehicleTypeDAO();
        VehicleType vehicleType = vehicleTypeDAO.create(new VehicleType("type"));

        //Change a field of the object that has to be updated
        vehicleType.setType("typeChanged");
        RESTVehicleType restVehicleType = new RESTVehicleType(vehicleType);
        //Perform the put request to update the object and check the fields of the returned object
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/vehicles/types/{id}", UUIDUtil.UUIDToNumberString(vehicleType.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicleType))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restVehicleType.getName())))
                .andReturn();

        //Test if changes actually went in effect in the database
        try {
            vehicleTypeDAO.refresh(vehicleType);
            VehicleType vehicleType1 = vehicleTypeDAO.get(vehicleType.getUuid());
            assertEquals("type field not updated correctly", "typeChanged", vehicleType1.getType());
            //Clean up database for other tests
            vehicleTypeDAO.remove(vehicleType.getUuid());
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }
}
