package spring.controller.insurance;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.identity.Periodicity;
import model.insurance.Contract;
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
import spring.model.insurance.RESTContract;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 15/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTContractControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTContractController())
            .addPlaceholderValue("path.contracts", "contracts")
            .addPlaceholderValue("path.contract_types", "types")
            //.setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Customer customer1, customer2;
    private static InsuranceCompany insuranceCompany1, insuranceCompany2;
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            customer1 = manager.getCustomerDAO().create(new Customer(address, "04789456123", "customerName1", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY));
            address = new Address("mystreet", "123", "lala", "12345", "land");
            customer2 = manager.getCustomerDAO().create(new Customer(address, "04789456124", "customerName2", "123456781", Periodicity.QUARTERLY, Periodicity.QUARTERLY));

            address = new Address("mystreet", "123", "lala", "12345", "land");
            insuranceCompany1 = manager.getInsuranceCompanyDao().create(new InsuranceCompany(address, "04789456122", "insuranceCompanyNam1", "123456780"));
            address = new Address("mystreet", "123", "lala", "12345", "land");
            insuranceCompany2 = manager.getInsuranceCompanyDao().create(new InsuranceCompany(address, "04789456121", "insuranceCompanyNam2", "123456783"));

            //contract = manager.getContractDao().create(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().remove(insuranceCompany1.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany2.getUuid());
            manager.getCustomerDAO().remove(customer1.getUuid());
            manager.getCustomerDAO().remove(customer2.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        Contract contract1 = create(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));
        Contract contract2 = create(new Contract(insuranceCompany1, customer2, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/contracts")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(2))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(2)));
        } catch (Exception e) {
            remove(contract1.getUuid());
            remove(contract2.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(contract1.getUuid());
        remove(contract2.getUuid());
    }

    @Test
    public void post() throws Exception {

        RESTContract restContract = new RESTContract(new Contract(insuranceCompany1, customer1, null, null));
        //RESTContract restContract = new RESTContract(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/contracts")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restContract)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTContract.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany1.getUuid()))))
                    .andExpect(jsonPath("$.customer", equalTo(UUIDUtil.UUIDToNumberString(customer1.getUuid()))))
                    /*.andExpect(jsonPath("$.startDate[0]", equalTo(restContract.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(restContract.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(restContract.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(restContract.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(restContract.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(restContract.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(restContract.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(restContract.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(restContract.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(restContract.getEndDate().getMinute())))*/
                    .andExpect(jsonPath("$.customerName", equalTo(customer1.getName())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(insuranceCompany1.getName())))
                    .andExpect(jsonPath("$.totalCost", equalTo(restContract.getTotalCost())))
                    .andExpect(jsonPath("$.totalTax", equalTo(restContract.getTotalTax())));
        } catch (AssertionError e) {
            remove(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Contract contract = manager.getContractDao().get(restId);
            try {
                assertEquals("insuranceCompany1 field not created correctly", insuranceCompany1, contract.getCompany());
                assertEquals("customer field not created correctly", customer1, contract.getCustomer());
                //assertEquals("startDate field not created correctly", LocalDateTime.of(2017, 6, 1, 0, 0), contract.getStartDate());
                //assertEquals("endDate field not created correctly", LocalDateTime.of(2017, 9, 1, 0, 0), contract.getEndDate());
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
        Contract contract = create(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/contracts/{id}", UUIDUtil.UUIDToNumberString(contract.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            remove(contract.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            remove(contract.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        Contract contract = create(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/contracts/{id}", UUIDUtil.UUIDToNumberString(contract.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany1.getUuid()))))
                    .andExpect(jsonPath("$.customer", equalTo(UUIDUtil.UUIDToNumberString(customer1.getUuid()))))
                    .andExpect(jsonPath("$.startDate[0]", equalTo(contract.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(contract.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(contract.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(contract.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(contract.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(contract.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(contract.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(contract.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(contract.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(contract.getEndDate().getMinute())))
                    .andExpect(jsonPath("$.customerName", equalTo(customer1.getName())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(insuranceCompany1.getName())))
                    .andExpect(jsonPath("$.totalCost", equalTo(contract.calculateCost())))
                    .andExpect(jsonPath("$.totalTax", equalTo(contract.calculateTax())));
        } catch (Exception e) {
            remove(contract.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(contract.getUuid());
    }

    @Test
    public void putId() throws Exception {

        //Add to database directly with DAO
        Contract contract = create(new Contract(insuranceCompany1, customer1, null, null));
        //Contract contract = create(new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0)));

        //Change a field of the object that has to be updated
        contract.setCustomer(customer2);
        RESTContract restContract = new RESTContract(contract);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            mvc.perform(MockMvcRequestBuilders.put("/contracts/{id}", UUIDUtil.UUIDToNumberString(contract.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restContract))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany1.getUuid()))))
                    .andExpect(jsonPath("$.customer", equalTo(UUIDUtil.UUIDToNumberString(customer2.getUuid()))))
                    /*.andExpect(jsonPath("$.startDate[0]", equalTo(contract.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(contract.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(contract.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(contract.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(contract.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(contract.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(contract.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(contract.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(contract.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(contract.getEndDate().getMinute())))*/
                    .andExpect(jsonPath("$.customerName", equalTo(customer2.getName())))
                    .andExpect(jsonPath("$.insuranceCompanyName", equalTo(insuranceCompany1.getName())))
                    .andExpect(jsonPath("$.totalCost", equalTo(contract.calculateCost())))
                    .andExpect(jsonPath("$.totalTax", equalTo(contract.calculateTax())));
        } catch (Exception e) {
            remove(contract.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            contract = manager.getContractDao().get(contract.getUuid());
            try {
                assertEquals("insuranceCompany1 field not created correctly", insuranceCompany1, contract.getCompany());
                assertEquals("customer field not created correctly", customer2, contract.getCustomer());
                //assertEquals("startDate field not created correctly", LocalDateTime.of(2017, 6, 1, 0, 0), contract.getStartDate());
                //assertEquals("endDate field not created correctly", LocalDateTime.of(2017, 9, 1, 0, 0), contract.getEndDate());
            } finally {
                //Clean up database for other tests
                remove(contract.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getContractDao().remove(uuid);
        }
    }

    private Contract create(Contract contract) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getContractDao().create(contract);
        }
    }

    private Contract get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getContractDao().get(uuid);
        }
    }
}
