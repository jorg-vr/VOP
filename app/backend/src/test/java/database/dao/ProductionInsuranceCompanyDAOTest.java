package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.InsuranceCompany;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/19/17.
 */
public class ProductionInsuranceCompanyDAOTest {
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        ProductionProvider.getInstance().close();
    }

    @Test
    public void crud() throws Exception {
        Address address1 = new Address();
        address1.setPostalCode("9000");
        address1.setStreetNumber("1");
        address1.setStreet("Teststraat");
        address1.setTown("TestTown");
        address1.setCountry("TestCountry");
        InsuranceCompany insuranceCompany1 = new InsuranceCompany();
        insuranceCompany1.setName("Verzekeringsmaatschappij1");
        insuranceCompany1.setAddress(address1);

        Address address2 = new Address();
        address2.setPostalCode("9000");
        address2.setStreetNumber("2");
        address2.setStreet("Teststraat");
        address2.setTown("TestTown");
        address2.setCountry("TestCountry");
        InsuranceCompany insuranceCompany2 = new InsuranceCompany();
        insuranceCompany2.setName("Verzekeringsmaatschappij2");
        insuranceCompany2.setAddress(address2);

        Address address3 = new Address();
        address3.setPostalCode("9000");
        address3.setStreetNumber("3");
        address3.setStreet("Teststraat");
        address3.setTown("TestTown");
        address3.setCountry("TestCountry");
        InsuranceCompany insuranceCompany3 = new InsuranceCompany();
        insuranceCompany3.setName("Verzekeringsmaatschappij3");
        insuranceCompany3.setAddress(address3);

        Address address4 = new Address();
        address4.setPostalCode("9000");
        address4.setStreetNumber("4");
        address4.setStreet("Teststraat");
        address4.setTown("TestTown");
        address4.setCountry("TestCountry");
        InsuranceCompany insuranceCompany4 = new InsuranceCompany();
        insuranceCompany4.setName("Verzekeringsmaatschappij4");
        insuranceCompany4.setAddress(address4);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getInsuranceCompanyDao().create(insuranceCompany3);
            manager.getInsuranceCompanyDao().create(insuranceCompany4);
        }
        insuranceCompany1.setAddress(address4);
        insuranceCompany2.setAddress(address3);
        insuranceCompany3.setAddress(address2);
        insuranceCompany4.setAddress(address1);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().update(insuranceCompany1);
            manager.getInsuranceCompanyDao().update(insuranceCompany2);
            manager.getInsuranceCompanyDao().update(insuranceCompany3);
            manager.getInsuranceCompanyDao().update(insuranceCompany4);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(address4.equals(manager.getInsuranceCompanyDao().get(insuranceCompany1.getUuid()).getAddress()));
            assertTrue(manager.getInsuranceCompanyDao().get(insuranceCompany2.getUuid()).getAddress().equals(address3));
            assertTrue(manager.getInsuranceCompanyDao().get(insuranceCompany3.getUuid()).getAddress().equals(address2));
            assertTrue(manager.getInsuranceCompanyDao().get(insuranceCompany4.getUuid()).getAddress().equals(address1));
            manager.getInsuranceCompanyDao().remove(insuranceCompany1.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany2.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany3.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany4.getUuid());
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            try {
                manager.getInsuranceCompanyDao().get(insuranceCompany1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {
            }
            try {
                manager.getInsuranceCompanyDao().get(insuranceCompany2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {
            }
            try {
                manager.getInsuranceCompanyDao().get(insuranceCompany3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {
            }
        }

    }

}