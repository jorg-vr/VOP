package spring.controller.filters;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.controller.AuthUtil;
import spring.controller.insurance.RESTContractController;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 15/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTContractControllerFiltersTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTContractController())
            .addPlaceholderValue("path.contracts", "contracts")
            .addPlaceholderValue("path.contract_types", "types")
            //.setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Contract contract1, contract2, contract3, contract4;
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

            contract1 = new Contract(insuranceCompany1, customer1, LocalDateTime.of(2017, 6, 1, 0, 0), LocalDateTime.of(2017, 8, 1, 0, 0));
            contract2 = new Contract(insuranceCompany1, customer2, LocalDateTime.of(2017, 7, 1, 0, 0), LocalDateTime.of(2017, 9, 1, 0, 0));
            contract3 = new Contract(insuranceCompany2, customer1, LocalDateTime.of(2017, 7, 1, 5, 0), LocalDateTime.of(2017, 9, 1, 5, 0));
            contract4 = new Contract(insuranceCompany2, customer2, LocalDateTime.of(2017, 8, 1, 0, 0), LocalDateTime.of(2017, 10, 1, 0, 0));
            contract1 = manager.getContractDao().create(contract1);
            contract2 = manager.getContractDao().create(contract2);
            contract3 = manager.getContractDao().create(contract3);
            contract4 = manager.getContractDao().create(contract4);
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
            manager.getContractDao().remove(contract1.getUuid());
            manager.getContractDao().remove(contract2.getUuid());
            manager.getContractDao().remove(contract3.getUuid());
            manager.getContractDao().remove(contract4.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        ProductionProvider.getInstance().close();
    }

    @Test
    public void customerFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?customer=" + UUIDUtil.UUIDToNumberString(customer2.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.data[*].customer", everyItem(equalTo(UUIDUtil.UUIDToNumberString(customer2.getUuid())))));
    }

    @Test
    public void insuranceCompanyFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?insuranceCompany=" + UUIDUtil.UUIDToNumberString(insuranceCompany2.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.data[*].insuranceCompany", everyItem(equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany2.getUuid())))));
    }

    @Test
    public void startsBeforeFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?startsBefore=2017-08-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                .andExpect(jsonPath("$.data[*].startDate[1]", everyItem(lessThan(8))));
    }

    @Test
    public void startsOnFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?startsOn=2017-07-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.data[*].startDate[1]", everyItem(equalTo(7))));
    }

    @Test
    public void startsAfterFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?startsAfter=2017-07-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.data[*].startDate[1]", everyItem(greaterThan(7))));
    }

    @Test
    public void endsBeforeFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?endsBefore=2017-10-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(3))))
                .andExpect(jsonPath("$.data[*].endDate[1]", everyItem(lessThan(10))));
    }

    @Test
    public void endsOnFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?endsOn=2017-09-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(2))))
                .andExpect(jsonPath("$.data[*].endDate[1]", everyItem(equalTo(9))));
    }

    @Test
    public void endsAfterFilter() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/contracts?endsAfter=2017-09-01")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                .andExpect(jsonPath("$.data[*].endDate[1]", everyItem(greaterThan(9))));
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
