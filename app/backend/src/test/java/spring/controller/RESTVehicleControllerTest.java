package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import dao.interfaces.DataAccessException;
import dao.interfaces.VehicleDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.hibernate.UnresolvableObjectException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTVehicle;
import util.UUIDUtil;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 6/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTVehicleControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTVehicleController())
            .addPlaceholderValue("path.vehicles", "vehicles")
            .addPlaceholderValue("path.companies", "companies")
            .addPlaceholderValue("path.fleets", "fleets")
            .build();

    private static VehicleType vehicleType;
    private static Fleet fleet1, fleet2, fleet3;
    private static Customer customer1, customer2;
    private static LocalDate localDate = LocalDate.of(2016, 1, 1);
    private static String[] authPair;

    private static DAOManager manager;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
        try {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer1 = new Customer(address, "04789456123", "customerName", "custBTW", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer1 = manager.getCustomerDAO().create(customer1);
            vehicleType = new VehicleType("vehicleType");
            vehicleType = manager.getVehicleTypeDAO().create(vehicleType);
            fleet1 = new Fleet("fleetName", customer1, address);
            fleet1 = manager.getFleetDAO().create(fleet1);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try {
            manager.getFleetDAO().remove(fleet1.getUuid());
            manager.getVehicleTypeDAO().remove(vehicleType.getUuid());
            manager.getCustomerDAO().remove(customer1.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        manager.close();
        ProductionProvider.getInstance().close();
    }

    /**
     * GET /vehicles
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                    .andExpect(jsonPath("$.total", equalTo(3)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            vehicleDAO.remove(vehicle.getUuid());
        }

    }

    /**
     * POST /vehicles
     *
     * @throws Exception
     */
    @Test
    public void post() throws Exception {
        RESTVehicle restVehicle = new RESTVehicle(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                .andExpect(jsonPath("$.leasingCompany", equalTo(restVehicle.getLeasingCompany())))
                .andExpect(jsonPath("$.fleet1", equalTo(restVehicle.getFleet())))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTVehicle restVehicle1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicle.class);
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        try {
            Vehicle vehicle = vehicleDAO.get(UUIDUtil.toUUID(restVehicle1.getId()));
            assertEquals("licensePlate field not created correctly", "ABC 123", vehicle.getLicensePlate());
            assertEquals("chassisNumber field not created correctly", "UZ0UZABCUKZ12345L", vehicle.getChassisNumber());
            assertEquals("brand field not created correctly", "brand 1", vehicle.getBrand());
            assertEquals("model field not created correctly", "model A", vehicle.getModel());
            assertEquals("type field not created correctly", vehicleType, vehicle.getType());
            assertEquals("value field not created correctly", 30000, vehicle.getValue());
            assertEquals("mileage field not created correctly", 2500, vehicle.getMileage());
            assertEquals("productionDate field not created correctly", localDate, vehicle.getProductionDate());
            assertEquals("leasingCompany field not created correctly", null, vehicle.getLeasingCompany());
            assertEquals("fleet1 field not created correctly", fleet1, vehicle.getFleet());
            vehicleDAO.remove(UUIDUtil.toUUID(restVehicle1.getId()));
        } catch (DataAccessException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void deleteId() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            vehicleDAO.refresh(vehicle);
            vehicleDAO.get(vehicle.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            vehicleDAO.remove(vehicle.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void getId() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(vehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(vehicle.getChassisNumber())))
                .andExpect(jsonPath("$.brand", equalTo(vehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(vehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()))))
                .andExpect(jsonPath("$.value", equalTo(vehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(vehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(vehicle.getProductionDate().toString())))
                //.andExpect(jsonPath("$.leasingCompany", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getLeasingCompany().getUuid()))))
                .andExpect(jsonPath("$.fleet1", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()))))
                .andReturn();

        //Clean up database for other tests
        vehicleDAO.remove(vehicle.getUuid());
    }

    /**
     * PUT /vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void putId() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Change a field of the object that has to be updated
        vehicle.setMileage(3500);
        RESTVehicle restVehicle = new RESTVehicle(vehicle);
        //Perform the put request to update the object and check the fields of the returned object
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                .andExpect(jsonPath("$.leasingCompany", equalTo(restVehicle.getLeasingCompany())))
                .andExpect(jsonPath("$.fleet1", equalTo(restVehicle.getFleet())))
                .andReturn();

        //Test if changes actually went in effect in the database
        try {
            vehicleDAO.refresh(vehicle);
            Vehicle vehicle1 = vehicleDAO.get(vehicle.getUuid());
            assertEquals("licensePlate field not updated correctly", "ABC 123", vehicle1.getLicensePlate());
            assertEquals("chassisNumber field not updated correctly", "UZ0UZABCUKZ12345L", vehicle1.getChassisNumber());
            assertEquals("brand field not updated correctly", "brand 1", vehicle1.getBrand());
            assertEquals("model field not updated correctly", "model A", vehicle1.getModel());
            assertEquals("type field not updated correctly", vehicleType, vehicle1.getType());
            assertEquals("value field not updated correctly", 30000, vehicle1.getValue());
            assertEquals("mileage field not updated correctly", 3500, vehicle1.getMileage());
            assertEquals("productionDate field not updated correctly", localDate, vehicle1.getProductionDate());
            assertEquals("leasingCompany field not updated correctly", null, vehicle1.getLeasingCompany());
            assertEquals("fleet1 field not updated correctly", fleet1, vehicle1.getFleet());
            //Clean up database for other tests
            vehicleDAO.remove(vehicle.getUuid());
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    /**
     * GET /companies/{companyId}/fleets/{fleetId}/vehicles
     *
     * @throws Exception
     */
    @Test
    public void getNested() throws Exception {
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(1)))
                    .andReturn();
        } finally {
            //Clean up database for other tests
            vehicleDAO.remove(vehicle.getUuid());
        }

    }

    /**
     * POST /companies/{companyId}/fleets/{fleetId}/vehicles
     *
     * @throws Exception
     */
    @Test
    public void postNested() throws Exception {
        RESTVehicle restVehicle = new RESTVehicle(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));
        //Test if response object fields are equal to posted data
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                .andExpect(jsonPath("$.leasingCompany", equalTo(restVehicle.getLeasingCompany())))
                .andExpect(jsonPath("$.fleet1", equalTo(restVehicle.getFleet())))
                .andReturn();

        //Test if posted object was actually added correctly to the database
        RESTVehicle restVehicle1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicle.class);
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        try {
            Vehicle vehicle = vehicleDAO.get(UUIDUtil.toUUID(restVehicle1.getId()));
            assertEquals("licensePlate field not created correctly", "ABC 123", vehicle.getLicensePlate());
            assertEquals("chassisNumber field not created correctly", "UZ0UZABCUKZ12345L", vehicle.getChassisNumber());
            assertEquals("brand field not created correctly", "brand 1", vehicle.getBrand());
            assertEquals("model field not created correctly", "model A", vehicle.getModel());
            assertEquals("type field not created correctly", vehicleType, vehicle.getType());
            assertEquals("value field not created correctly", 30000, vehicle.getValue());
            assertEquals("mileage field not created correctly", 2500, vehicle.getMileage());
            assertEquals("productionDate field not created correctly", localDate, vehicle.getProductionDate());
            assertEquals("leasingCompany field not created correctly", null, vehicle.getLeasingCompany());
            assertEquals("fleet1 field not created correctly", fleet1, vehicle.getFleet());
            vehicleDAO.remove(UUIDUtil.toUUID(restVehicle1.getId()));
        } catch (DataAccessException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void deleteIdNested() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Attempt to remove from the database with delete request
        mvc.perform(MockMvcRequestBuilders.delete("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk());
        //Check if successfully removed from database
        try {
            vehicleDAO.refresh(vehicle);
            vehicleDAO.get(vehicle.getUuid());
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            vehicleDAO.remove(vehicle.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (UnresolvableObjectException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void getIdNested() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Attempt to retrieve the object with the given id
        mvc.perform(MockMvcRequestBuilders.get("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(vehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(vehicle.getChassisNumber())))
                .andExpect(jsonPath("$.brand", equalTo(vehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(vehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()))))
                .andExpect(jsonPath("$.value", equalTo(vehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(vehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(vehicle.getProductionDate().toString())))
                //.andExpect(jsonPath("$.leasingCompany", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getLeasingCompany().getUuid()))))
                .andExpect(jsonPath("$.fleet1", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()))))
                .andReturn();

        //Clean up database for other tests
        vehicleDAO.remove(vehicle.getUuid());
    }

    /**
     * PUT /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void putIdNested() throws Exception {
        //Add to database directly with DAO
        VehicleDAO vehicleDAO = manager.getVehicleDAO();
        Vehicle vehicle = vehicleDAO.create(new Vehicle("brand 1", "model A", "UZ0UZABCUKZ12345L", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1, null));

        //Change a field of the object that has to be updated
        vehicle.setMileage(3500);
        RESTVehicle restVehicle = new RESTVehicle(vehicle);
        //Perform the put request to update the object and check the fields of the returned object
        MvcResult result = mvc.perform(MockMvcRequestBuilders.put("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                .andExpect(jsonPath("$.leasingCompany", equalTo(restVehicle.getLeasingCompany())))
                .andExpect(jsonPath("$.fleet1", equalTo(restVehicle.getFleet())))
                .andReturn();

        //Test if changes actually went in effect in the database
        try {
            vehicleDAO.refresh(vehicle);
            Vehicle vehicle1 = vehicleDAO.get(vehicle.getUuid());
            assertEquals("licensePlate field not updated correctly", "ABC 123", vehicle1.getLicensePlate());
            assertEquals("chassisNumber field not updated correctly", "UZ0UZABCUKZ12345L", vehicle1.getChassisNumber());
            assertEquals("brand field not updated correctly", "brand 1", vehicle1.getBrand());
            assertEquals("model field not updated correctly", "model A", vehicle1.getModel());
            assertEquals("type field not updated correctly", vehicleType, vehicle1.getType());
            assertEquals("value field not updated correctly", 30000, vehicle1.getValue());
            assertEquals("mileage field not updated correctly", 3500, vehicle1.getMileage());
            assertEquals("productionDate field not updated correctly", localDate, vehicle1.getProductionDate());
            assertEquals("leasingCompany field not updated correctly", null, vehicle1.getLeasingCompany());
            assertEquals("fleet1 field not updated correctly", fleet1, vehicle1.getFleet());
            //Clean up database for other tests
            vehicleDAO.remove(vehicle.getUuid());
        } catch (DataAccessException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }
}
