package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.DAOManager;
import model.billing.Invoice;
import model.billing.InvoiceType;
import model.identity.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by Ponti on 9/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTInvoiceControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTInvoiceController())
            .addPlaceholderValue("path.companies", "companies")
            .addPlaceholderValue("path.invoices", "invoices")
            .addPlaceholderValue("path.contracts", "contracts")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static Company payer, beneficiary;

    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            payer = manager.getCustomerDAO().create(new Customer(address, "04789456123", "payerName", "123456789", Periodicity.QUARTERLY, Periodicity.QUARTERLY));
            address = new Address("mystreet", "123", "lala", "12345", "land");
            beneficiary = manager.getInsuranceCompanyDao().create(new InsuranceCompany(address, "04789456123", "payerName", "123456789"));
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()){
            manager.getCustomerDAO().remove(payer.getUuid());
            manager.getInsuranceCompanyDao().remove(beneficiary.getUuid());
        }

        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {

        //Add to database directly with DAO
        Invoice invoice = create(new Invoice(payer,beneficiary, InvoiceType.BILLING,false,LocalDateTime.of(2017,3,1,0,0,0),LocalDateTime.of(2017,4,1,0,0,0)));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/" + UUIDUtil.UUIDToNumberString(payer.getUuid()) + "/invoices")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    //Expect 3 instead of 2 because there's 1 extra Function in the database for authentication purposes while testing
                    .andExpect(jsonPath("$.data", hasSize(greaterThanOrEqualTo(1))))
                    .andExpect(jsonPath("$.total", greaterThanOrEqualTo(1)));
        } catch (Exception e) {
            remove(invoice.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(invoice.getUuid());
    }

    @Test
    public void getId() throws Exception {

        //Add to database directly with DAO
        Invoice invoice = create(new Invoice(payer,beneficiary, InvoiceType.BILLING,false,LocalDateTime.of(2017,3,1,0,0),LocalDateTime.of(2017,4,1,0,0)));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/" + UUIDUtil.UUIDToNumberString(payer.getUuid()) + "/invoices/{id}", UUIDUtil.UUIDToNumberString(invoice.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andExpect(jsonPath("$.payer", equalTo(UUIDUtil.UUIDToNumberString(invoice.getPayer().getUuid()))))
                    .andExpect(jsonPath("$.beneficiary", equalTo(UUIDUtil.UUIDToNumberString(invoice.getBeneficiary().getUuid()))))
                    .andExpect(jsonPath("$.type", equalTo(invoice.getType().toString())))
                    .andExpect(jsonPath("$.paid", equalTo(invoice.isPaid())))
                    .andExpect(jsonPath("$.startDate[0]", equalTo(invoice.getStartDate().getYear())))
                    .andExpect(jsonPath("$.startDate[1]", equalTo(invoice.getStartDate().getMonthValue())))
                    .andExpect(jsonPath("$.startDate[2]", equalTo(invoice.getStartDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.startDate[3]", equalTo(invoice.getStartDate().getHour())))
                    .andExpect(jsonPath("$.startDate[4]", equalTo(invoice.getStartDate().getMinute())))
                    .andExpect(jsonPath("$.endDate[0]", equalTo(invoice.getEndDate().getYear())))
                    .andExpect(jsonPath("$.endDate[1]", equalTo(invoice.getEndDate().getMonthValue())))
                    .andExpect(jsonPath("$.endDate[2]", equalTo(invoice.getEndDate().getDayOfMonth())))
                    .andExpect(jsonPath("$.endDate[3]", equalTo(invoice.getEndDate().getHour())))
                    .andExpect(jsonPath("$.endDate[4]", equalTo(invoice.getEndDate().getMinute())));
        } catch (Exception e) {
            remove(invoice.getUuid());
            throw e;
        }

        //Clean up database for other tests
        remove(invoice.getUuid());
    }

    private void remove(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInvoiceDao().remove(uuid);
        }
    }

    private Invoice create(Invoice invoice) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getInvoiceDao().create(invoice);
        }
    }

    private Invoice get(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getInvoiceDao().get(uuid);
        }
    }
}