package spring.controller;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.CompanyDAO;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.Company;
import model.identity.CompanyType;
import model.identity.Periodicity;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTAddress;
import spring.model.RESTCompany;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
public class RESTCompanyControllerTest {


    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTCompanyController())
            .addPlaceholderValue("path.companies", "companies")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static String[] authPair;
    private static DAOManager manager;
    private static CompanyDAO<Company> dao;

    private static List<Company> companies;
    private static Address address;
    private static Company company;

    private static String[] NAMES = new String[]{"anita", "iiiii", "bbbbbbb"};
    private static String[] COUNTRIES = new String[]{"Tanzania", "Sierra Leone", "Sierra Leone"};
    private static String[] CITIES = new String[]{"Dar es Salaam", "Freetown", "Freetown"};
    private static String[] POSTAL_CODES = new String[]{"1234", "1234", "1489"};
    private static CompanyType[] COMPANY_TYPES = new CompanyType[]{CompanyType.CUSTOMER, CompanyType.CUSTOMER, CompanyType.INSURANCE_COMPANY};


    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        manager = ProductionProvider.getInstance().getDaoManager();
        authPair = AuthUtil.getAdminToken();
        dao = manager.getCompanyDAO();
        companies = new ArrayList<>();

        try {
            for (int i = 0; i < NAMES.length; i++) {
                Address a = new Address("mystreet", "123", CITIES[i], POSTAL_CODES[i], COUNTRIES[i]);
                Company c = COMPANY_TYPES[i].getFactory().create();
                c.setAddress(a);
                c.setName(NAMES[i]);
                c.setPhoneNumber("04789456123");
                c.setBtwNumber("123456789");
                c.setFacturationPeriod(Periodicity.HALF_YEARLY);
                c.setPaymentPeriod(Periodicity.HALF_YEARLY);
                dao.create(c);
                companies.add(c);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        company = companies.get(0);
        address = company.getAddress();
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try {

            for (Company company : companies) {
                dao.remove(company.getUuid());
            }
        } catch (DataAccessException e) {

            e.printStackTrace();
        }
        manager.close();
        ProductionProvider.getInstance().close();
    }


    @Test
    public void get() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(companies.size() + 1))) // + 1 because there is also an admin account and this is not in the list
                .andExpect(jsonPath("$.total", equalTo(companies.size() + 1)))
                .andReturn();
    }

    @Test
    public void post() throws Exception {
        RESTCompany restCompany = new RESTCompany(null, "frank", "sinatra", "0123456", new RESTAddress("a", "b", "c", "d", "e"));
        restCompany.setType(CompanyType.CUSTOMER.toString());
        restCompany.setFacturationPeriod(1);
        restCompany.setPaymentPeriod(1);
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/companies")
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(restCompany.getName())))
                .andExpect(jsonPath("$.vatNumber", equalTo("sinatra")))
                .andExpect(jsonPath("$.phoneNumber", equalTo("0123456")))
                .andExpect(jsonPath("$.address.country", equalTo("a")))
                .andExpect(jsonPath("$.address.city", equalTo("b")))
                .andExpect(jsonPath("$.address.street", equalTo("c")))
                .andExpect(jsonPath("$.address.houseNumber", equalTo("d")))
                .andExpect(jsonPath("$.address.postalCode", equalTo("e")))
                .andReturn();
        RESTCompany restCompany1 = TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTCompany.class);
        mvc.perform(MockMvcRequestBuilders.delete("/companies/{id}", restCompany1.getId())
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/companies/{id}", restCompany1.getId())
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getId() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies/{id}", UUIDUtil.UUIDToNumberString(company.getUuid()))
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(company.getName())))
                .andExpect(jsonPath("$.vatNumber", equalTo(company.getBtwNumber())))
                .andExpect(jsonPath("$.phoneNumber", equalTo(company.getPhoneNumber())))
                .andExpect(jsonPath("$.address.country", equalTo(address.getCountry())))
                .andExpect(jsonPath("$.address.city", equalTo(address.getTown())))
                .andExpect(jsonPath("$.address.street", equalTo(address.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber", equalTo(address.getStreetNumber())))
                .andExpect(jsonPath("$.address.postalCode", equalTo(address.getPostalCode()))).andReturn();
    }

    @Test
    public void putId() throws Exception {
        company.setBtwNumber("new");
        RESTCompany restCompany = new RESTCompany(company);

        mvc.perform(MockMvcRequestBuilders.put("/companies/{id}", UUIDUtil.UUIDToNumberString(company.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restCompany)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(company.getName())))
                .andExpect(jsonPath("$.vatNumber", equalTo(company.getBtwNumber())))
                .andExpect(jsonPath("$.phoneNumber", equalTo(company.getPhoneNumber())))
                .andExpect(jsonPath("$.address.country", equalTo(address.getCountry())))
                .andExpect(jsonPath("$.address.city", equalTo(address.getTown())))
                .andExpect(jsonPath("$.address.street", equalTo(address.getStreet())))
                .andExpect(jsonPath("$.address.houseNumber", equalTo(address.getStreetNumber())))
                .andExpect(jsonPath("$.address.postalCode", equalTo(address.getPostalCode())));

        // Test if changes are preserved
        getId();
    }

    @Test
    public void nameContainsFilter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies?nameContains=i")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].name", everyItem(containsString("i"))));
    }

    @Test
    public void typeFilter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies?type=" + CompanyType.INSURANCE_COMPANY.toString())
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].type", everyItem(equalTo(CompanyType.INSURANCE_COMPANY.toString()))));
    }

    @Test
    public void countryFilter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies?country=tanzania")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].address.country", everyItem(containsString("Tanzania"))));
    }

    @Test
    public void cityFilter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies?city=freetown")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].address.city", everyItem(containsString("Freetown"))));
    }

    @Test
    public void postalCodeFilter() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/companies?postalCode=1234")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1]))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[*].address.postalCode", everyItem(containsString("1234"))));
    }

}