package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
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
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTVehicle;
import util.UUIDUtil;

import java.time.LocalDate;
import java.util.UUID;

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
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static VehicleType vehicleType;
    private static Fleet fleet1, fleet2, fleet3;
    private static Customer customer1, customer2;
    private static LocalDate localDate = LocalDate.of(2016, 1, 1);
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer1 = new Customer(address, "04789456121", "customerName1", "custBTW1", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer1 = manager.getCustomerDAO().create(customer1);
            customer2 = new Customer(address, "04789456122", "customerName2", "custBTW2", Periodicity.QUARTERLY, Periodicity.QUARTERLY);
            customer2 = manager.getCustomerDAO().create(customer2);
            vehicleType = new VehicleType("vehicleType");
            vehicleType = manager.getVehicleTypeDAO().create(vehicleType);
            fleet1 = new Fleet("fleetName1", customer1, address);
            fleet1 = manager.getFleetDAO().create(fleet1);
            fleet2 = new Fleet("fleetName2", customer1, address);
            fleet2 = manager.getFleetDAO().create(fleet2);
            fleet3 = new Fleet("fleetName3", customer2, address);
            fleet3 = manager.getFleetDAO().create(fleet3);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFleetDAO().remove(fleet1.getUuid());
            manager.getFleetDAO().remove(fleet2.getUuid());
            manager.getFleetDAO().remove(fleet3.getUuid());
            manager.getVehicleTypeDAO().remove(vehicleType.getUuid());
            manager.getCustomerDAO().remove(customer1.getUuid());
            manager.getCustomerDAO().remove(customer2.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }

    /**
     * GET /vehicles
     *
     * @throws Exception
     */
    @Test
    public void get() throws Exception {

        Vehicle vehicle1 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));
        Vehicle vehicle2 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAB", "ABC 124", 30000, 2500, vehicleType, localDate, fleet2));
        Vehicle vehicle3 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAC", "ABC 125", 30000, 2500, vehicleType, localDate, fleet3));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                    .andExpect(jsonPath("$.total", equalTo(3)));
        } catch (Exception e) {
            remove(vehicle1.getUuid());
            remove(vehicle2.getUuid());
            remove(vehicle3.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicle1.getUuid());
        remove(vehicle2.getUuid());
        remove(vehicle3.getUuid());
    }

    /**
     * POST /vehicles
     *
     * @throws Exception
     */
    @Test
    public void post() throws Exception {

        RESTVehicle restVehicle = new RESTVehicle(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/vehicles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicle.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                    .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                    .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                    .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                    .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                    .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                    .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                    .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                    .andExpect(jsonPath("$.fleet", equalTo(restVehicle.getFleet())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            Vehicle vehicle = get(restId);
            try {
                assertEquals("licensePlate field not created correctly", "ABC 123", vehicle.getLicensePlate());
                assertEquals("chassisNumber field not created correctly", "AAAAAAAAAAAAAAAAA", vehicle.getVin());
                assertEquals("brand field not created correctly", "brand 1", vehicle.getBrand());
                assertEquals("model field not created correctly", "model A", vehicle.getModel());
                assertEquals("type field not created correctly", vehicleType, vehicle.getType());
                assertEquals("value field not created correctly", 30000, vehicle.getValue());
                assertEquals("mileage field not created correctly", 2500, vehicle.getMileage());
                assertEquals("productionDate field not created correctly", localDate, vehicle.getYear());
                assertEquals("fleet field not created correctly", fleet1, vehicle.getFleet());
            } finally {
                remove(restId);
            }
        } catch (ObjectNotFoundException e) {
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
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(vehicle.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
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
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.licensePlate", equalTo(vehicle.getLicensePlate())))
                    .andExpect(jsonPath("$.vin", equalTo(vehicle.getVin())))
                    .andExpect(jsonPath("$.brand", equalTo(vehicle.getBrand())))
                    .andExpect(jsonPath("$.model", equalTo(vehicle.getModel())))
                    .andExpect(jsonPath("$.type", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()))))
                    .andExpect(jsonPath("$.value", equalTo(vehicle.getValue())))
                    .andExpect(jsonPath("$.mileage", equalTo(vehicle.getMileage())))
                    .andExpect(jsonPath("$.year", equalTo(Integer.toString(vehicle.getYear().getYear()))))
                    .andExpect(jsonPath("$.fleet", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()))));
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicle.getUuid());
    }

    /**
     * PUT /vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Change a field of the object that has to be updated
        vehicle.setMileage(3500);
        RESTVehicle restVehicle = new RESTVehicle(vehicle);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
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
                    .andExpect(jsonPath("$.fleet", equalTo(restVehicle.getFleet())));
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            vehicle = get(vehicle.getUuid());
            try {
                assertEquals("licensePlate field not updated correctly", "ABC 123", vehicle.getLicensePlate());
                assertEquals("chassisNumber field not updated correctly", "AAAAAAAAAAAAAAAAA", vehicle.getVin());
                assertEquals("brand field not updated correctly", "brand 1", vehicle.getBrand());
                assertEquals("model field not updated correctly", "model A", vehicle.getModel());
                assertEquals("type field not updated correctly", vehicleType, vehicle.getType());
                assertEquals("value field not updated correctly", 30000, vehicle.getValue());
                assertEquals("mileage field not updated correctly", 3500, vehicle.getMileage());
                assertEquals("productionDate field not updated correctly", localDate, vehicle.getYear());
                assertEquals("fleet field not updated correctly", fleet1, vehicle.getFleet());
            } finally {
                //Clean up database for other tests
                remove(vehicle.getUuid());
            }
        } catch (ObjectNotFoundException e) {
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

        Vehicle vehicle1 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));
        Vehicle vehicle2 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAB", "ABC 124", 30000, 2500, vehicleType, localDate, fleet2));
        Vehicle vehicle3 = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAC", "ABC 125", 30000, 2500, vehicleType, localDate, fleet3));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/" + UUIDUtil.UUIDToNumberString(customer1.getUuid()) + "/fleets/" + UUIDUtil.UUIDToNumberString(fleet2.getUuid()) + "/vehicles")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(1)));
        } catch (Exception e) {
            remove(vehicle1.getUuid());
            remove(vehicle2.getUuid());
            remove(vehicle3.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicle1.getUuid());
        remove(vehicle2.getUuid());
        remove(vehicle3.getUuid());
    }

    /**
     * POST /companies/{companyId}/fleets/{fleetId}/vehicles
     *
     * @throws Exception
     */
    @Test
    public void postNested() throws Exception {

        RESTVehicle restVehicle = new RESTVehicle(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/companies/" + UUIDUtil.UUIDToNumberString(customer1.getUuid()) + "/fleets/" + UUIDUtil.UUIDToNumberString(fleet1.getUuid()) + "/vehicles")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicle)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicle.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(jsonPath("$.licensePlate", equalTo(restVehicle.getLicensePlate())))
                    .andExpect(jsonPath("$.vin", equalTo(restVehicle.getVin())))
                    .andExpect(jsonPath("$.brand", equalTo(restVehicle.getBrand())))
                    .andExpect(jsonPath("$.model", equalTo(restVehicle.getModel())))
                    .andExpect(jsonPath("$.type", equalTo(restVehicle.getType())))
                    .andExpect(jsonPath("$.value", equalTo(restVehicle.getValue())))
                    .andExpect(jsonPath("$.mileage", equalTo(restVehicle.getMileage())))
                    .andExpect(jsonPath("$.year", equalTo(restVehicle.getYear())))
                    .andExpect(jsonPath("$.fleet", equalTo(restVehicle.getFleet())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            Vehicle vehicle = get(restId);
            try {
                assertEquals("licensePlate field not created correctly", "ABC 123", vehicle.getLicensePlate());
                assertEquals("chassisNumber field not created correctly", "AAAAAAAAAAAAAAAAA", vehicle.getVin());
                assertEquals("brand field not created correctly", "brand 1", vehicle.getBrand());
                assertEquals("model field not created correctly", "model A", vehicle.getModel());
                assertEquals("type field not created correctly", vehicleType, vehicle.getType());
                assertEquals("value field not created correctly", 30000, vehicle.getValue());
                assertEquals("mileage field not created correctly", 2500, vehicle.getMileage());
                assertEquals("productionDate field not created correctly", localDate, vehicle.getYear());
                assertEquals("fleet field not created correctly", fleet1, vehicle.getFleet());
            } finally {
                remove(restId);
            }
        } catch (ObjectNotFoundException e) {
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
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/companies/" + UUIDUtil.UUIDToNumberString(customer1.getUuid()) + "/fleets/" + UUIDUtil.UUIDToNumberString(fleet1.getUuid()) + "/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(vehicle.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
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
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/" + UUIDUtil.UUIDToNumberString(customer1.getUuid()) + "/fleets/" + UUIDUtil.UUIDToNumberString(fleet1.getUuid()) + "/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.licensePlate", equalTo(vehicle.getLicensePlate())))
                    .andExpect(jsonPath("$.vin", equalTo(vehicle.getVin())))
                    .andExpect(jsonPath("$.brand", equalTo(vehicle.getBrand())))
                    .andExpect(jsonPath("$.model", equalTo(vehicle.getModel())))
                    .andExpect(jsonPath("$.type", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getType().getUuid()))))
                    .andExpect(jsonPath("$.value", equalTo(vehicle.getValue())))
                    .andExpect(jsonPath("$.mileage", equalTo(vehicle.getMileage())))
                    .andExpect(jsonPath("$.year", equalTo(Integer.toString(vehicle.getYear().getYear()))))
                    .andExpect(jsonPath("$.fleet", equalTo(UUIDUtil.UUIDToNumberString(vehicle.getFleet().getUuid()))));
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicle.getUuid());
    }

    /**
     * PUT /companies/{companyId}/fleets/{fleetId}/vehicles/{id}
     *
     * @throws Exception
     */
    @Test
    public void putIdNested() throws Exception {

        //Add to database directly with DAO
        Vehicle vehicle = create(new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, localDate, fleet1));

        //Change a field of the object that has to be updated
        vehicle.setMileage(3500);
        RESTVehicle restVehicle = new RESTVehicle(vehicle);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/companies/" + UUIDUtil.UUIDToNumberString(customer1.getUuid()) + "/fleets/" + UUIDUtil.UUIDToNumberString(fleet1.getUuid()) + "/vehicles/{id}", UUIDUtil.UUIDToNumberString(vehicle.getUuid()))
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
                    .andExpect(jsonPath("$.fleet", equalTo(restVehicle.getFleet())));
        } catch (Exception e) {
            remove(vehicle.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            vehicle = get(vehicle.getUuid());
            try {
                assertEquals("licensePlate field not updated correctly", "ABC 123", vehicle.getLicensePlate());
                assertEquals("chassisNumber field not updated correctly", "AAAAAAAAAAAAAAAAA", vehicle.getVin());
                assertEquals("brand field not updated correctly", "brand 1", vehicle.getBrand());
                assertEquals("model field not updated correctly", "model A", vehicle.getModel());
                assertEquals("type field not updated correctly", vehicleType, vehicle.getType());
                assertEquals("value field not updated correctly", 30000, vehicle.getValue());
                assertEquals("mileage field not updated correctly", 3500, vehicle.getMileage());
                assertEquals("productionDate field not updated correctly", localDate, vehicle.getYear());
                assertEquals("fleet field not updated correctly", fleet1, vehicle.getFleet());
            } finally {
                //Clean up database for other tests
                remove(vehicle.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleDAO().remove(uuid);
        }
    }

    private Vehicle create(Vehicle vehicle) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleDAO().create(vehicle);
        }
    }

    private Vehicle get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleDAO().get(uuid);
        }
    }
}
