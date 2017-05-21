package spring.controller.insurance;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.identity.Periodicity;
import model.insurance.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
import spring.model.insurance.RESTVehicleInsurance;
import util.UUIDUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by tjupo on 14/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTVehicleInsuranceControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTVehicleInsuranceController())
            .addPlaceholderValue("path.contracts", "contracts")
            .addPlaceholderValue("path.vehicle_insurances", "insurances")
            .addPlaceholderValue("path.green_card", "green-card")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Vehicle vehicle1, vehicle2;
    private static Surety flatSurety;
    private static Contract contract;
    private static Customer customer;
    private static VehicleType vehicleType;
    private static Fleet fleet;
    private static InsuranceCompany insuranceCompany;
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer = manager.getCustomerDAO().create(new Customer(address, "04789456123", "customerName", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY));
            vehicleType = new VehicleType("vehicleType");
            vehicleType.setTax(SuretyType.CIVIL_LIABILITY, 2.5);
            vehicleType.setTax(SuretyType.LEGAL_AID, 2.5);
            vehicleType.setTax(SuretyType.OMNIUM_FULL, 2.5);
            vehicleType.setTax(SuretyType.OMNIUM_PARTIAL, 2.5);
            vehicleType.setTax(SuretyType.SAFETY, 2.5);
            vehicleType.setTax(SuretyType.TRAVEL_AID, 2.5);
            vehicleType.setCommission(SuretyType.CIVIL_LIABILITY, 2.5);
            vehicleType.setCommission(SuretyType.LEGAL_AID, 2.5);
            vehicleType.setCommission(SuretyType.OMNIUM_FULL, 2.5);
            vehicleType.setCommission(SuretyType.OMNIUM_PARTIAL, 2.5);
            vehicleType.setCommission(SuretyType.SAFETY, 2.5);
            vehicleType.setCommission(SuretyType.TRAVEL_AID, 2.5);
            vehicleType = manager.getVehicleTypeDAO().create(vehicleType);
            address = new Address("mystreet", "123", "lala", "12345", "land");
            fleet = manager.getFleetDAO().create(new Fleet("fleetName", customer, address));

            vehicle1 = new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAA", "ABC 123", 30000, 2500, vehicleType, LocalDate.of(2016, 1, 1), fleet);
            vehicle2 = new Vehicle("brand 1", "model A", "AAAAAAAAAAAAAAAAB", "ABC 124", 30000, 2500, vehicleType, LocalDate.of(2016, 1, 1), fleet);
            vehicle1.setSpecificCommission(SuretyType.CIVIL_LIABILITY, 2.5);
            vehicle2.setSpecificCommission(SuretyType.CIVIL_LIABILITY, 2.5);
            vehicle1 = manager.getVehicleDAO().create(vehicle1);
            vehicle2 = manager.getVehicleDAO().create(vehicle2);

            address = new Address("mystreet", "123", "lala", "12345", "land");
            insuranceCompany = manager.getInsuranceCompanyDao().create(new InsuranceCompany(address, "04789456122", "insuranceCompanyName", "123456780"));

            flatSurety = new FlatSurety(20);
            flatSurety.setInsuranceCompany(insuranceCompany);
            flatSurety.setSuretyType(SuretyType.CIVIL_LIABILITY);
            flatSurety = manager.getFlatSuretyDao().create((FlatSurety) flatSurety);

            contract = manager.getContractDao().create(new Contract(insuranceCompany, customer, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getContractDao().remove(contract.getUuid());
            manager.getFlatSuretyDao().remove(flatSurety.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany.getUuid());
            manager.getVehicleDAO().remove(vehicle1.getUuid());
            manager.getVehicleDAO().remove(vehicle2.getUuid());
            manager.getFleetDAO().remove(fleet.getUuid());
            manager.getVehicleTypeDAO().remove(vehicleType.getUuid());
            manager.getCustomerDAO().remove(customer.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        VehicleInsurance vehicleInsurance1 = create(new VehicleInsurance(vehicle1, flatSurety, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0), contract, 50, 300));
        VehicleInsurance vehicleInsurance2 = create(new VehicleInsurance(vehicle2, flatSurety, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0), contract, 50, 300));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/contracts/" + UUIDUtil.UUIDToNumberString(contract.getUuid()) + "/insurances")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(2)));
        } catch (Exception e) {
            remove(vehicleInsurance1.getUuid());
            remove(vehicleInsurance2.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicleInsurance1.getUuid());
        remove(vehicleInsurance2.getUuid());
    }

    @Test
    @Ignore
    public void post() throws Exception {

        RESTVehicleInsurance restVehicleInsurance = new RESTVehicleInsurance(new VehicleInsurance(vehicle1, flatSurety, null, null, contract, 50, 300));
        //RESTVehicleInsurance restVehicleInsurance = new RESTVehicleInsurance(new VehicleInsurance(vehicle1,flatSurety,LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0),contract,50,300));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/contracts/" + UUIDUtil.UUIDToNumberString(contract.getUuid()) + "/insurances")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restVehicleInsurance)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTVehicleInsurance.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.vehicle", equalTo(restVehicleInsurance.getVehicle())))
                    .andExpect(jsonPath("$.surety", equalTo(restVehicleInsurance.getSurety())))
                    .andExpect(jsonPath("$.contract", equalTo(restVehicleInsurance.getContract())))
                    /*.andExpect(jsonPath("$.startDate[0]", equalTo(restVehicleInsurance.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(restVehicleInsurance.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(restVehicleInsurance.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(restVehicleInsurance.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(restVehicleInsurance.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(restVehicleInsurance.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(restVehicleInsurance.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(restVehicleInsurance.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(restVehicleInsurance.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(restVehicleInsurance.getEndDate().getMinute())))*/
                    .andExpect(jsonPath("$.franchise", equalTo(restVehicleInsurance.getFranchise())))
                    .andExpect(jsonPath("$.insuredValue", equalTo(restVehicleInsurance.getInsuredValue())))
                    .andExpect(jsonPath("$.cost", equalTo(restVehicleInsurance.getCost())))
                    .andExpect(jsonPath("$.tax", equalTo(restVehicleInsurance.getTax())))
                    .andExpect(jsonPath("$.licensePlate", equalTo(restVehicleInsurance.getLicensePlate())))
                    .andExpect(jsonPath("$.brand", equalTo(restVehicleInsurance.getBrand())))
                    .andExpect(jsonPath("$.suretyType", equalTo(restVehicleInsurance.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(restVehicleInsurance.getInsuranceCompanyName())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            VehicleInsurance vehicleInsurance = manager.getVehicleInsuranceDao().get(restId);
            try {
                assertEquals("vehicle field not created correctly", vehicle1, vehicleInsurance.getVehicle());
                //assertEquals("surety field not created correctly", flatSurety, vehicleInsurance.getSurety());
                assertEquals("contract field not created correctly", contract, vehicleInsurance.getContract());
                //assertEquals("startDate field not created correctly", LocalDateTime.of(2017, 6, 1, 0, 0), vehicleInsurance.getStartDate());
                //assertEquals("endDate field not created correctly", LocalDateTime.of(2017, 9, 1, 0, 0), vehicleInsurance.getEndDate());
            } finally {
                remove(restId);
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    @Test
    @Ignore
    public void deleteId() throws Exception {

        //Add to database directly with DAO
        VehicleInsurance vehicleInsurance = create(new VehicleInsurance(vehicle1, flatSurety, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0), contract, 50, 300));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/contracts/" + UUIDUtil.UUIDToNumberString(contract.getUuid()) + "/insurances/{id}", UUIDUtil.UUIDToNumberString(vehicleInsurance.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(vehicleInsurance.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(vehicleInsurance.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        VehicleInsurance vehicleInsurance = create(new VehicleInsurance(vehicle1, flatSurety, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0), contract, 50, 300));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/contracts/" + UUIDUtil.UUIDToNumberString(contract.getUuid()) + "/insurances/{id}", UUIDUtil.UUIDToNumberString(vehicleInsurance.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.vehicle", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getVehicle().getUuid()))))
                    .andExpect(jsonPath("$.surety", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getSurety().getUuid()))))
                    .andExpect(jsonPath("$.contract", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getContract().getUuid()))))
                    /*
                    .andExpect(jsonPath("$.startDate[0]", equalTo(vehicleInsurance.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(vehicleInsurance.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(vehicleInsurance.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(vehicleInsurance.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(vehicleInsurance.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(vehicleInsurance.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(vehicleInsurance.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(vehicleInsurance.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(vehicleInsurance.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(vehicleInsurance.getEndDate().getMinute())))*/
                    .andExpect(jsonPath("$.franchise", equalTo(vehicleInsurance.getFranchise())))
                    .andExpect(jsonPath("$.insuredValue", equalTo(vehicleInsurance.getInsuredValue())))
                    .andExpect(jsonPath("$.cost", equalTo(vehicleInsurance.calculateCost())))
                    .andExpect(jsonPath("$.tax", equalTo(vehicleInsurance.calculateTax())))
                    .andExpect(jsonPath("$.licensePlate", equalTo(vehicleInsurance.getVehicle().getLicensePlate())))
                    .andExpect(jsonPath("$.brand", equalTo(vehicleInsurance.getVehicle().getBrand())))
                    .andExpect(jsonPath("$.suretyType", equalTo(vehicleInsurance.getSurety().getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(vehicleInsurance.getContract().getCompany().getName())));
        } catch (Exception e) {
            remove(vehicleInsurance.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(vehicleInsurance.getUuid());
    }

    @Test
    @Ignore
    public void putId() throws Exception {

        //Add to database directly with DAO
        VehicleInsurance vehicleInsurance = create(new VehicleInsurance(vehicle1, flatSurety, null, null, contract, 50, 300));
        //VehicleInsurance vehicleInsurance = create(new VehicleInsurance(vehicle1, flatSurety, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0), contract, 50, 300));

        //Change a field of the object that has to be updated
        vehicleInsurance.setVehicle(vehicle2);
        RESTVehicleInsurance restVehicleInsurance = new RESTVehicleInsurance(vehicleInsurance);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/contracts/" + UUIDUtil.UUIDToNumberString(contract.getUuid()) + "/insurances/{id}", UUIDUtil.UUIDToNumberString(vehicleInsurance.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restVehicleInsurance))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.vehicle", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getVehicle().getUuid()))))
                    .andExpect(jsonPath("$.surety", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getSurety().getUuid()))))
                    .andExpect(jsonPath("$.contract", equalTo(UUIDUtil.UUIDToNumberString(vehicleInsurance.getContract().getUuid()))))
                    /*.andExpect(jsonPath("$.startDate[0]", equalTo(vehicleInsurance.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(vehicleInsurance.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(vehicleInsurance.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(vehicleInsurance.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(vehicleInsurance.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(vehicleInsurance.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(vehicleInsurance.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(vehicleInsurance.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(vehicleInsurance.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(vehicleInsurance.getEndDate().getMinute())))*/
                    .andExpect(jsonPath("$.franchise", equalTo(vehicleInsurance.getFranchise())))
                    .andExpect(jsonPath("$.insuredValue", equalTo(vehicleInsurance.getInsuredValue())))
                    .andExpect(jsonPath("$.cost", equalTo(vehicleInsurance.calculateCost())))
                    .andExpect(jsonPath("$.tax", equalTo(vehicleInsurance.calculateTax())))
                    .andExpect(jsonPath("$.licensePlate", equalTo(vehicleInsurance.getVehicle().getLicensePlate())))
                    .andExpect(jsonPath("$.brand", equalTo(vehicleInsurance.getVehicle().getBrand())))
                    .andExpect(jsonPath("$.suretyType", equalTo(vehicleInsurance.getSurety().getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(vehicleInsurance.getContract().getCompany().getName())));
        } catch (AssertionError e) {
            remove(vehicleInsurance.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            vehicleInsurance = manager.getVehicleInsuranceDao().get(vehicleInsurance.getUuid());
            try {
                assertEquals("vehicle field not updated correctly", vehicle2, vehicleInsurance.getVehicle());
                //assertEquals("surety field not updated correctly", flatSurety, vehicleInsurance.getSurety());
                assertEquals("contract field not updated correctly", contract, vehicleInsurance.getContract());
                //assertEquals("startDate field not updated correctly", LocalDateTime.of(2017, 6, 1, 0, 0), vehicleInsurance.getStartDate());
                //assertEquals("endDate field not updated correctly", LocalDateTime.of(2017, 9, 1, 0, 0), vehicleInsurance.getEndDate());
            } finally {
                //Clean up database for other tests
                remove(vehicleInsurance.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    @Ignore
    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleInsuranceDao().remove(uuid);
        }
    }

    @Ignore
    private VehicleInsurance create(VehicleInsurance vehicleInsurance) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleInsuranceDao().create(vehicleInsurance);
        }
    }

    @Ignore
    private VehicleInsurance get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getVehicleInsuranceDao().get(uuid);
        }
    }
}
