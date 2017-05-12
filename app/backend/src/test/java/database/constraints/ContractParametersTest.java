package database.constraints;

import dao.database.ProductionProvider;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import model.identity.Address;
import model.identity.InsuranceCompany;
import model.insurance.Contract;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.time.LocalDateTime;

import static database.DAOTestUtil.*;
import static org.junit.Assert.fail;

@Ignore
public class ContractParametersTest {

    private static InsuranceCompany insuranceCompany;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        Address address = new Address("Street", "55", "Town", "9000", "Country");
        insuranceCompany = createInsuranceCompany(new InsuranceCompany(address, "123456789", "customerName", "BE123123123B01"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeInsuranceCompany(insuranceCompany.getUuid());

        ProductionProvider.getInstance().close();
    }

    @Test
    public void allFields() throws Exception {
        try {
            Contract contract = createContract(new Contract(insuranceCompany, null, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeContract(contract.getUuid());
        } catch (DataAccessException e) {
            e.printStackTrace();
            fail("Contract failed to create despite all required fields being filled in");
        }
    }

    @Test
    public void insuranceCompanyField() throws Exception {
        try {
            Contract contract = createContract(new Contract(null, null, LocalDateTime.of(2017, 7, 15, 0, 0), LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeContract(contract.getUuid());
            fail("Contract succesfully created with insuranceCompany field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void startDateField() throws Exception {
        try {
            Contract contract = createContract(new Contract(insuranceCompany, null, null, LocalDateTime.of(2017, 9, 15, 0, 0)));
            removeContract(contract.getUuid());
            fail("Contract succesfully created with startDate field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }

    @Test
    public void endDateField() throws Exception {
        try {
            Contract contract = createContract(new Contract(insuranceCompany, null, LocalDateTime.of(2017, 7, 15, 0, 0), null));
            removeContract(contract.getUuid());
            fail("Contract succesfully created with endDate field null when an exception was expected");
        } catch (ConstraintViolationException e) {
            System.out.println( e.getMessage());
            //nothing since this is supposed to happen
        }
    }
}
