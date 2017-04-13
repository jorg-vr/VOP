package database;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.identity.Address;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.fail;

//TODO: add insuranceCompany once it has a DAO, test unusable without insuranceCmp DAO
@Ignore
public class ContractParametersTest {

    private static DAOProvider daoProvider;
    private static Address address;
    private static InsuranceCompany insuranceCompany;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             InsuranceCompanyDAO insuranceCompanyDAO = daoProvider.getInsuranceCompanyDao()) {
            address = addressDAO.create(new Address("Street", "55", "Town", "9000", "Country"));
            insuranceCompany = insuranceCompanyDAO.create(new InsuranceCompany(address, "123456789", "customerName", "BE123123123B01"));
        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             InsuranceCompanyDAO insuranceCompanyDAO = daoProvider.getInsuranceCompanyDao()) {
            insuranceCompanyDAO.remove(insuranceCompany.getUuid());
            addressDAO.remove(address.getUuid());
        }
        daoProvider.close();
    }

    @Test
    public void allFields() throws Exception {
        Contract contract = null;
        try (ContractDAO contractDAO = daoProvider.getContractDao()) {
            contract = contractDAO.create(new Contract(insuranceCompany, null, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            contractDAO.remove(contract.getUuid());
        } catch (DataAccessException d) {
            if (contract == null) {
                fail("Contract failed to create despite all required fields being filled in");
            }
        }
    }

    @Test
    public void insuranceCompanyField() throws Exception {
        Contract contract = null;
        try (ContractDAO contractDAO = daoProvider.getContractDao()) {
            contract = contractDAO.create(new Contract(null, null, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            contractDAO.remove(contract.getUuid());
            fail("Contract succesfully created with insuranceCompany field null when an exception was expected");
        } catch (DataAccessException d) {
            if (contract != null) {
                fail("Contract succesfully created with insuranceCompany field null when an exception was expected");
            }
        }
    }

    @Test
    public void startDateField() throws Exception {
        Contract contract = null;
        try (ContractDAO contractDAO = daoProvider.getContractDao()) {
            contract = contractDAO.create(new Contract(insuranceCompany, null, null, LocalDateTime.of(2017, 9, 15, 0, 0)));
            contractDAO.remove(contract.getUuid());
            fail("Contract succesfully created with startDate field null when an exception was expected");
        } catch (DataAccessException d) {
            if (contract != null) {
                fail("Contract succesfully created with startDate field null when an exception was expected");
            }
        }
    }

    @Test
    public void endDateField() throws Exception {
        Contract contract = null;
        try (ContractDAO contractDAO = daoProvider.getContractDao()) {
            contract = contractDAO.create(new Contract(insuranceCompany, null, LocalDateTime.of(2017, 7, 15, 0, 0), null));
            contractDAO.remove(contract.getUuid());
            fail("Contract succesfully created with endDate field null when an exception was expected");
        } catch (DataAccessException d) {
            if (contract != null) {
                fail("Contract succesfully created with endDate field null when an exception was expected");
            }
        }
    }
}
