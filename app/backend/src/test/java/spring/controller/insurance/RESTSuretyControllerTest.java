package spring.controller.insurance;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;
import model.identity.Periodicity;
import model.insurance.FlatSurety;
import model.insurance.NonFlatSurety;
import model.insurance.SuretyType;
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
import spring.controller.RESTFunctionController;
import spring.controller.TestUtil;
import spring.exceptions.MyExceptionHandler;
import spring.model.RESTFunction;
import spring.model.insurance.RESTSurety;
import util.UUIDUtil;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Ponti on 18/05/2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RESTSuretyControllerTest {

    private MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTSuretyController())
            .addPlaceholderValue("path.companies", "companies")
            .addPlaceholderValue("path.sureties", "sureties")
            .setControllerAdvice(new MyExceptionHandler())
            .build();

    private static InsuranceCompany insuranceCompany;
    private static String[] authPair;

    @BeforeClass
    public static void setup() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        authPair = AuthUtil.getAdminToken();
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Address address = new Address("mystreet", "123", "lala", "12345", "land");
            insuranceCompany = new InsuranceCompany(address, "04789456123", "anita", "123456789");
            insuranceCompany = manager.getInsuranceCompanyDao().create(insuranceCompany);
        }
    }


    @AfterClass
    public static void afterTransaction() throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().remove(insuranceCompany.getUuid());
        }
        ProductionProvider.getInstance().close();
    }

    /**
     * GET /companies/{companyId}/sureties
     *
     * @throws Exception
     */
    @Test
    public void getFlat() throws Exception {

        //Add to database directly with DAO
        FlatSurety flatSurety = createFlat(new FlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 50));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/sureties", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)));
        } catch (Exception e) {
            removeFlat(flatSurety.getUuid());
            throw e;
        }

        //Clean up database for other tests
        removeFlat(flatSurety.getUuid());
    }

    /**
     * POST /companies/{companyId}/sureties
     *
     * @throws Exception
     */
    @Test
    public void postFlat() throws Exception {

        RESTSurety restSurety = new RESTSurety(new FlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 50));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/companies/{companyId}/sureties", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restSurety)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTSurety.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(restSurety.getPremium())))
                    .andExpect(jsonPath("$.suretyType", equalTo(restSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(restSurety.getInsuranceCompany())));
        } catch (AssertionError e) {
            removeFlat(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            FlatSurety flatSurety = getFlat(restId);
            try {
                assertEquals("insuranceCompany field not created correctly", insuranceCompany, flatSurety.getInsuranceCompany());
                assertEquals("premium field not created correctly", 50, flatSurety.getPremium());
                assertEquals("suretyType field not created correctly", SuretyType.CIVIL_LIABILITY, flatSurety.getSuretyType());
            } finally {
                removeFlat(restId);
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void deleteIdFlat() throws Exception {

        //Add to database directly with DAO
        FlatSurety flatSurety = createFlat(new FlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 50));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(flatSurety.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            removeFlat(flatSurety.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            removeFlat(flatSurety.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void getIdFlat() throws Exception {

        //Add to database directly with DAO
        FlatSurety flatSurety = createFlat(new FlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 50));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(flatSurety.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(flatSurety.getPremium())))
                    .andExpect(jsonPath("$.suretyType", equalTo(flatSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))));
        } catch (Exception e) {
            removeFlat(flatSurety.getUuid());
            throw e;
        }

        //Clean up database for other tests
        removeFlat(flatSurety.getUuid());
    }

    /**
     * PUT /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void putIdFlat() throws Exception {

        //Add to database directly with DAO
        FlatSurety flatSurety = createFlat(new FlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 50));

        //Change a field of the object that has to be updated
        flatSurety.setPremium(75);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            RESTSurety restSurety = new RESTSurety(flatSurety);
            mvc.perform(MockMvcRequestBuilders.put("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(flatSurety.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restSurety))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(restSurety.getPremium())))
                    .andExpect(jsonPath("$.suretyType", equalTo(restSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(restSurety.getInsuranceCompany())));
        } catch (AssertionError e) {
            removeFlat(flatSurety.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            flatSurety = getFlat(flatSurety.getUuid());
            try {
                assertEquals("insuranceCompany field not updated correctly", insuranceCompany, flatSurety.getInsuranceCompany());
                assertEquals("premium field not updated correctly", 75, flatSurety.getPremium());
                assertEquals("suretyType field not updated correctly", SuretyType.CIVIL_LIABILITY, flatSurety.getSuretyType());
            } finally {
                //Clean up database for other tests
                removeFlat(flatSurety.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    /**
     * GET /companies/{companyId}/sureties
     *
     * @throws Exception
     */
    @Test
    public void getNonFlat() throws Exception {

        //Add to database directly with DAO
        NonFlatSurety nonFlatSurety = createNonFlat(new NonFlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 25, 50));

        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/sureties", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.data", hasSize(equalTo(1))))
                    .andExpect(jsonPath("$.total", equalTo(1)));
        } catch (Exception e) {
            removeNonFlat(nonFlatSurety.getUuid());
            throw e;
        }

        //Clean up database for other tests
        removeNonFlat(nonFlatSurety.getUuid());
    }

    /**
     * POST /companies/{companyId}/sureties
     *
     * @throws Exception
     */
    @Test
    public void postNonFlat() throws Exception {

        RESTSurety restSurety = new RESTSurety(new NonFlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY, 25, 50));

        //Perform the post request
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/companies/{companyId}/sureties", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))
                .header("Content-Type", "application/json")
                .header("Authorization", authPair[0])
                .header("Function", authPair[1])
                .content(TestUtil.convertObjectToJsonBytes(restSurety)));
        MvcResult result = resultActions.andExpect(status().isOk()).andReturn();
        UUID restId = UUIDUtil.toUUID(TestUtil.convertJsonBytesToObject(result.getResponse().getContentAsByteArray(), RESTSurety.class).getId());

        //Test if response object fields are equal to posted data
        try {
            resultActions
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(restSurety.getPremium())))
                    .andExpect(jsonPath("$.premiumPercentage", equalTo(restSurety.getPremiumPercentage())))
                    .andExpect(jsonPath("$.suretyType", equalTo(restSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(restSurety.getInsuranceCompany())));
        } catch (AssertionError e) {
            removeNonFlat(restId);
            throw e;
        }

        //Test if posted object was actually added correctly to the database
        try {
            NonFlatSurety nonFlatSurety = getNonFlat(restId);
            try {
                assertEquals("insuranceCompany field not created correctly", insuranceCompany, nonFlatSurety.getInsuranceCompany());
                assertEquals("minPremium field not created correctly", 50, nonFlatSurety.getMinPremium());
                assertTrue("premiumPercentage field not created correctly", 25.0 == nonFlatSurety.getPremiumPercentage());
                assertEquals("suretyType field not created correctly", SuretyType.CIVIL_LIABILITY, nonFlatSurety.getSuretyType());
            } finally {
                removeNonFlat(restId);
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the posted object from the actual database");
        }
    }

    /**
     * DELETE /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void deleteIdNonFlat() throws Exception {

        //Add to database directly with DAO
        NonFlatSurety nonFlatSurety = createNonFlat(new NonFlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY,25, 50));

        //Attempt to remove from the database with delete request
        try {
            mvc.perform(MockMvcRequestBuilders.delete("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(nonFlatSurety.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk());
        } catch (Exception e) {
            removeNonFlat(nonFlatSurety.getUuid());
            throw e;
        }

        //Check if successfully removed from database
        try {
            //Remove from database (above get function should have thrown an error if the object was no longer in the database)
            removeNonFlat(nonFlatSurety.getUuid());
            fail("DELETE request did not succesfully delete the object from the database");
        } catch (ObjectNotFoundException e) {
            //Nothing because the object is no longer present in the database which is expected
        }
    }

    /**
     * GET /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void getIdNonFlat() throws Exception {

        //Add to database directly with DAO
        NonFlatSurety nonFlatSurety = createNonFlat(new NonFlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY,25, 50));

        //Attempt to retrieve the object with the given id
        try {
            mvc.perform(MockMvcRequestBuilders.get("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(nonFlatSurety.getUuid()))
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(nonFlatSurety.getMinPremium())))
                    .andExpect(jsonPath("$.premiumPercentage", equalTo(nonFlatSurety.getPremiumPercentage())))
                    .andExpect(jsonPath("$.suretyType", equalTo(nonFlatSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()))));
        } catch (Exception e) {
            removeNonFlat(nonFlatSurety.getUuid());
            throw e;
        }

        //Clean up database for other tests
        removeNonFlat(nonFlatSurety.getUuid());
    }

    /**
     * PUT /companies/{companyId}/sureties/{id}
     *
     * @throws Exception
     */
    @Test
    public void putIdNonFlat() throws Exception {

        //Add to database directly with DAO
        NonFlatSurety nonFlatSurety = createNonFlat(new NonFlatSurety(insuranceCompany, SuretyType.CIVIL_LIABILITY,25, 50));

        //Change a field of the object that has to be updated
        nonFlatSurety.setMinPremium(75);

        //Perform the put request to update the object and check the fields of the returned object
        try {
            RESTSurety restSurety = new RESTSurety(nonFlatSurety);
            mvc.perform(MockMvcRequestBuilders.put("/companies/{companyId}/sureties/{id}", UUIDUtil.UUIDToNumberString(insuranceCompany.getUuid()), UUIDUtil.UUIDToNumberString(nonFlatSurety.getUuid()))
                    .header("Content-Type", "application/json")
                    .header("Authorization", authPair[0])
                    .header("Function", authPair[1])
                    .content(TestUtil.convertObjectToJsonBytes(restSurety))
            )
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.premium", equalTo(restSurety.getPremium())))
                    .andExpect(jsonPath("$.premiumPercentage", equalTo(restSurety.getPremiumPercentage())))
                    .andExpect(jsonPath("$.suretyType", equalTo(restSurety.getSuretyType().toString())))
                    .andExpect(jsonPath("$.insuranceCompany", equalTo(restSurety.getInsuranceCompany())));
        } catch (AssertionError e) {
            removeNonFlat(nonFlatSurety.getUuid());
            throw e;
        }

        //Test if changes actually went in effect in the database
        try {
            nonFlatSurety = getNonFlat(nonFlatSurety.getUuid());
            try {
                assertEquals("insuranceCompany field not updated correctly", insuranceCompany, nonFlatSurety.getInsuranceCompany());
                assertEquals("minPremium field not updated correctly", 75, nonFlatSurety.getMinPremium());
                assertTrue("premiumPercentage field not updated correctly", 25.0 == nonFlatSurety.getPremiumPercentage());
                assertEquals("suretyType field not updated correctly", SuretyType.CIVIL_LIABILITY, nonFlatSurety.getSuretyType());
            } finally {
                //Clean up database for other tests
                removeNonFlat(nonFlatSurety.getUuid());
            }
        } catch (ObjectNotFoundException e) {
            fail("Could not retrieve the put object from the actual database");
        }
    }

    private void removeFlat(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFlatSuretyDao().remove(uuid);
        }
    }

    private FlatSurety createFlat(FlatSurety flatSurety) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFlatSuretyDao().create(flatSurety);
        }
    }

    private FlatSurety getFlat(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getFlatSuretyDao().get(uuid);
        }
    }

    private void removeNonFlat(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getNonFlatSuretyDao().remove(uuid);
        }
    }

    private NonFlatSurety createNonFlat(NonFlatSurety nonFlatSurety) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getNonFlatSuretyDao().create(nonFlatSurety);
        }
    }

    private NonFlatSurety getNonFlat(UUID uuid) throws Exception {
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            return manager.getNonFlatSuretyDao().get(uuid);
        }
    }
}
