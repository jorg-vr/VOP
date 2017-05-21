package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.InsuranceCompany;
import model.insurance.FlatSurety;
import model.insurance.NonFlatSurety;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/20/17.
 */
public class ProductionNonFlatSuretyDAOTest {
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
    public void byOwner() throws Exception {
        Address address1 = new Address();
        address1.setPostalCode("9000");
        address1.setStreetNumber("10T");
        address1.setStreet("Teststraat");
        address1.setTown("TestTown");
        address1.setCountry("TestCountry");
        InsuranceCompany insuranceCompany1 = new InsuranceCompany();
        insuranceCompany1.setName("Verzekeringsmaatschappij1");
        insuranceCompany1.setAddress(address1);

        Address address2 = new Address();
        address2.setPostalCode("9000");
        address2.setStreetNumber("10T");
        address2.setStreet("Teststraat");
        address2.setTown("TestTown");
        address2.setCountry("TestCountry");
        InsuranceCompany insuranceCompany2 = new InsuranceCompany();
        insuranceCompany2.setName("Verzekeringsmaatschappij1");
        insuranceCompany2.setAddress(address2);

        NonFlatSurety nonFlatSurety1 = new NonFlatSurety();
        nonFlatSurety1.setMinPremium(10);
        nonFlatSurety1.setPremiumPercentage(0.2);
        nonFlatSurety1.setInsuranceCompany(insuranceCompany1);
        NonFlatSurety nonFlatSurety2 = new NonFlatSurety();
        nonFlatSurety2.setMinPremium(10);
        nonFlatSurety2.setPremiumPercentage(0.2);
        nonFlatSurety2.setInsuranceCompany(insuranceCompany2);
        NonFlatSurety nonFlatSurety3 = new NonFlatSurety();
        nonFlatSurety3.setMinPremium(10);
        nonFlatSurety3.setPremiumPercentage(0.2);
        nonFlatSurety3.setInsuranceCompany(insuranceCompany1);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getNonFlatSuretyDao().create(nonFlatSurety1);
            manager.getNonFlatSuretyDao().create(nonFlatSurety2);
            manager.getNonFlatSuretyDao().create(nonFlatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<NonFlatSurety> sureties = manager.getNonFlatSuretyDao().listFiltered(manager.getNonFlatSuretyDao().byOwner(insuranceCompany1));
            assertTrue(sureties.contains(nonFlatSurety1));
            assertTrue(!sureties.contains(nonFlatSurety2));
            assertTrue(sureties.contains(nonFlatSurety3));

            sureties = manager.getNonFlatSuretyDao().listFiltered(manager.getNonFlatSuretyDao().byOwner(insuranceCompany2));
            assertTrue(!sureties.contains(nonFlatSurety1));
            assertTrue(sureties.contains(nonFlatSurety2));
            assertTrue(!sureties.contains(nonFlatSurety3));
        }
    }

    @Test
    public void crud() throws Exception {
        Address address1 = new Address();
        address1.setPostalCode("9000");
        address1.setStreetNumber("10T");
        address1.setStreet("Teststraat");
        address1.setTown("TestTown");
        address1.setCountry("TestCountry");
        InsuranceCompany insuranceCompany1 = new InsuranceCompany();
        insuranceCompany1.setName("Verzekeringsmaatschappij1");
        insuranceCompany1.setAddress(address1);

        Address address2 = new Address();
        address2.setPostalCode("9000");
        address2.setStreetNumber("10T");
        address2.setStreet("Teststraat");
        address2.setTown("TestTown");
        address2.setCountry("TestCountry");
        InsuranceCompany insuranceCompany2 = new InsuranceCompany();
        insuranceCompany2.setName("Verzekeringsmaatschappij1");
        insuranceCompany2.setAddress(address2);

        NonFlatSurety nonFlatSurety1 = new NonFlatSurety();
        nonFlatSurety1.setMinPremium(10);
        nonFlatSurety1.setPremiumPercentage(0.4);
        nonFlatSurety1.setInsuranceCompany(insuranceCompany1);

        NonFlatSurety nonFlatSurety2 = new NonFlatSurety();
        nonFlatSurety2.setMinPremium(20);
        nonFlatSurety2.setPremiumPercentage(0.2);
        nonFlatSurety2.setInsuranceCompany(insuranceCompany2);

        NonFlatSurety nonFlatSurety3 = new NonFlatSurety();
        nonFlatSurety3.setMinPremium(20);
        nonFlatSurety3.setPremiumPercentage(0.2);
        nonFlatSurety3.setInsuranceCompany(insuranceCompany2);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getNonFlatSuretyDao().create(nonFlatSurety1);
            manager.getNonFlatSuretyDao().create(nonFlatSurety2);
            manager.getNonFlatSuretyDao().create(nonFlatSurety3);
        }
        nonFlatSurety1.setMinPremium(20);
        nonFlatSurety2.setPremiumPercentage(0.1);
        nonFlatSurety3.setInsuranceCompany(insuranceCompany1);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getNonFlatSuretyDao().update(nonFlatSurety1);
            manager.getNonFlatSuretyDao().update(nonFlatSurety2);
            manager.getNonFlatSuretyDao().update(nonFlatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(manager.getNonFlatSuretyDao().get(nonFlatSurety1.getUuid()).getMinPremium() == 20);
            assertTrue(manager.getNonFlatSuretyDao().get(nonFlatSurety2.getUuid()).getPremiumPercentage() == 0.1);
            assertTrue(manager.getNonFlatSuretyDao().get(nonFlatSurety3.getUuid()).getInsuranceCompany().equals(insuranceCompany1));
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getNonFlatSuretyDao().remove(nonFlatSurety1.getUuid());
            manager.getNonFlatSuretyDao().remove(nonFlatSurety2.getUuid());
            manager.getNonFlatSuretyDao().remove(nonFlatSurety3.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany1.getUuid());
            manager.getInsuranceCompanyDao().remove(insuranceCompany2.getUuid());
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
                manager.getNonFlatSuretyDao().get(nonFlatSurety1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getNonFlatSuretyDao().get(nonFlatSurety2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getNonFlatSuretyDao().get(nonFlatSurety3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

        }
    }

}