package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.InsuranceCompany;
import model.insurance.FlatSurety;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/19/17.
 */
public class ProductionFlatSuretyDAOTest {

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

        FlatSurety flatSurety1 = new FlatSurety();
        flatSurety1.setPremium(10);
        flatSurety1.setInsuranceCompany(insuranceCompany1);
        FlatSurety flatSurety2 = new FlatSurety();
        flatSurety2.setPremium(20);
        flatSurety2.setInsuranceCompany(insuranceCompany2);
        FlatSurety flatSurety3 = new FlatSurety();
        flatSurety3.setInsuranceCompany(insuranceCompany2);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getFlatSuretyDao().create(flatSurety1);
            manager.getFlatSuretyDao().create(flatSurety2);
            manager.getFlatSuretyDao().create(flatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<FlatSurety> sureties = manager.getFlatSuretyDao().listFiltered(manager.getFlatSuretyDao().byOwner(insuranceCompany1));
            assertTrue(sureties.contains(flatSurety1));
            assertTrue(!sureties.contains(flatSurety2));
            assertTrue(!sureties.contains(flatSurety3));

            sureties = manager.getFlatSuretyDao().listFiltered(manager.getFlatSuretyDao().byOwner(insuranceCompany2));
            assertTrue(!sureties.contains(flatSurety1));
            assertTrue(sureties.contains(flatSurety2));
            assertTrue(sureties.contains(flatSurety3));
        }
    }

    @Test
    public void createUpdateRemove() throws Exception {
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

        FlatSurety flatSurety1 = new FlatSurety();
        flatSurety1.setPremium(10);
        flatSurety1.setInsuranceCompany(insuranceCompany1);
        FlatSurety flatSurety2 = new FlatSurety();
        flatSurety2.setPremium(20);
        flatSurety2.setInsuranceCompany(insuranceCompany2);
        FlatSurety flatSurety3 = new FlatSurety();
        flatSurety3.setPremium(40);
        flatSurety3.setInsuranceCompany(insuranceCompany2);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getFlatSuretyDao().create(flatSurety1);
            manager.getFlatSuretyDao().create(flatSurety2);
            manager.getFlatSuretyDao().create(flatSurety3);
        }
        flatSurety1.setPremium(20);
        flatSurety3.setPremium(20);
        flatSurety3.setInsuranceCompany(insuranceCompany1);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFlatSuretyDao().update(flatSurety1);
            manager.getFlatSuretyDao().update(flatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(manager.getFlatSuretyDao().get(flatSurety1.getUuid()).getPremium() == 20);
            assertTrue(manager.getFlatSuretyDao().get(flatSurety2.getUuid()).getPremium() == 20);
            assertTrue(manager.getFlatSuretyDao().get(flatSurety3.getUuid()).getPremium() == 20);
            assertTrue(manager.getFlatSuretyDao().get(flatSurety3.getUuid()).getInsuranceCompany().equals(insuranceCompany1));
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getFlatSuretyDao().remove(flatSurety1.getUuid());
            manager.getFlatSuretyDao().remove(flatSurety2.getUuid());
            manager.getFlatSuretyDao().remove(flatSurety3.getUuid());
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
                manager.getFlatSuretyDao().get(flatSurety1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getFlatSuretyDao().get(flatSurety2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getFlatSuretyDao().get(flatSurety3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

        }
    }

}