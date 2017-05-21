package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.identity.Address;
import model.identity.InsuranceCompany;
import model.insurance.FlatSurety;
import model.insurance.NonFlatSurety;
import model.insurance.Surety;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/20/17.
 */
public class ProductionSuretyDAOTest {
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
        
        Address address3 = new Address();
        address3.setPostalCode("9000");
        address3.setStreetNumber("10T");
        address3.setStreet("Teststraat");
        address3.setTown("TestTown");
        address3.setCountry("TestCountry");
        InsuranceCompany insuranceCompany3 = new InsuranceCompany();
        insuranceCompany3.setName("Verzekeringsmaatschappij3");
        insuranceCompany3.setAddress(address3);

        Address address4 = new Address();
        address4.setPostalCode("9000");
        address4.setStreetNumber("10T");
        address4.setStreet("Teststraat");
        address4.setTown("TestTown");
        address4.setCountry("TestCountry");
        InsuranceCompany insuranceCompany4 = new InsuranceCompany();
        insuranceCompany4.setName("Verzekeringsmaatschappij4");
        insuranceCompany4.setAddress(address4);

        FlatSurety flatSurety1 = new FlatSurety();
        flatSurety1.setPremium(10);
        flatSurety1.setInsuranceCompany(insuranceCompany3);
        FlatSurety flatSurety2 = new FlatSurety();
        flatSurety2.setPremium(20);
        flatSurety2.setInsuranceCompany(insuranceCompany4);
        FlatSurety flatSurety3 = new FlatSurety();
        flatSurety3.setInsuranceCompany(insuranceCompany4);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getInsuranceCompanyDao().create(insuranceCompany3);
            manager.getInsuranceCompanyDao().create(insuranceCompany4);
            manager.getSuretyDao().create(nonFlatSurety1);
            manager.getSuretyDao().create(nonFlatSurety2);
            manager.getSuretyDao().create(nonFlatSurety3);

            manager.getSuretyDao().create(flatSurety1);
            manager.getSuretyDao().create(flatSurety2);
            manager.getSuretyDao().create(flatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<Surety> sureties = manager.getSuretyDao().listFiltered(manager.getSuretyDao().byOwner(insuranceCompany1));
            assertTrue(sureties.contains(nonFlatSurety1));
            assertTrue(!sureties.contains(nonFlatSurety2));
            assertTrue(sureties.contains(nonFlatSurety3));

            sureties = manager.getSuretyDao().listFiltered(manager.getSuretyDao().byOwner(insuranceCompany2));
            assertTrue(!sureties.contains(nonFlatSurety1));
            assertTrue(sureties.contains(nonFlatSurety2));
            assertTrue(!sureties.contains(nonFlatSurety3));
            Collection<Surety> flatSureties = manager.getSuretyDao().listFiltered(manager.getSuretyDao().byOwner(insuranceCompany3));
            assertTrue(flatSureties.contains(flatSurety1));
            assertTrue(!flatSureties.contains(flatSurety2));
            assertTrue(!flatSureties.contains(flatSurety3));

            flatSureties = manager.getSuretyDao().listFiltered(manager.getSuretyDao().byOwner(insuranceCompany4));
            assertTrue(!flatSureties.contains(flatSurety1));
            assertTrue(flatSureties.contains(flatSurety2));
            assertTrue(flatSureties.contains(flatSurety3));
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
        insuranceCompany2.setName("Verzekeringsmaatschappij2");
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

        Address address3 = new Address();
        address3.setPostalCode("9000");
        address3.setStreetNumber("10T");
        address3.setStreet("Teststraat");
        address3.setTown("TestTown");
        address3.setCountry("TestCountry");
        InsuranceCompany insuranceCompany3 = new InsuranceCompany();
        insuranceCompany3.setName("Verzekeringsmaatschappij3");
        insuranceCompany3.setAddress(address3);

        Address address4 = new Address();
        address4.setPostalCode("9000");
        address4.setStreetNumber("10T");
        address4.setStreet("Teststraat");
        address4.setTown("TestTown");
        address4.setCountry("TestCountry");
        InsuranceCompany insuranceCompany4 = new InsuranceCompany();
        insuranceCompany4.setName("Verzekeringsmaatschappij4");
        insuranceCompany4.setAddress(address4);

        FlatSurety flatSurety1 = new FlatSurety();
        flatSurety1.setPremium(10);
        flatSurety1.setInsuranceCompany(insuranceCompany3);
        FlatSurety flatSurety2 = new FlatSurety();
        flatSurety2.setPremium(20);
        flatSurety2.setInsuranceCompany(insuranceCompany4);
        FlatSurety flatSurety3 = new FlatSurety();
        flatSurety3.setInsuranceCompany(insuranceCompany4);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getInsuranceCompanyDao().create(insuranceCompany1);
            manager.getInsuranceCompanyDao().create(insuranceCompany2);
            manager.getInsuranceCompanyDao().create(insuranceCompany3);
            manager.getInsuranceCompanyDao().create(insuranceCompany4);
            manager.getSuretyDao().create(nonFlatSurety1);
            manager.getSuretyDao().create(nonFlatSurety2);
            manager.getSuretyDao().create(nonFlatSurety3);
            manager.getSuretyDao().create(flatSurety1);
            manager.getSuretyDao().create(flatSurety2);
            manager.getSuretyDao().create(flatSurety3);
        }
        nonFlatSurety1.setMinPremium(20);
        nonFlatSurety2.setPremiumPercentage(0.1);
        nonFlatSurety3.setInsuranceCompany(insuranceCompany4);
        flatSurety1.setPremium(110);
        flatSurety2.setInsuranceCompany(insuranceCompany1);
        flatSurety3.setPremium(1);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSuretyDao().update(nonFlatSurety1);
            manager.getSuretyDao().update(nonFlatSurety2);
            manager.getSuretyDao().update(nonFlatSurety3);
            manager.getSuretyDao().update(flatSurety1);
            manager.getSuretyDao().update(flatSurety2);
            manager.getSuretyDao().update(flatSurety3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            assertTrue(((NonFlatSurety)manager.getSuretyDao().get(nonFlatSurety1.getUuid())).getMinPremium() == 20);
            assertTrue(((NonFlatSurety)manager.getSuretyDao().get(nonFlatSurety2.getUuid())).getPremiumPercentage() == 0.1);
            assertTrue(manager.getSuretyDao().get(nonFlatSurety3.getUuid()).getInsuranceCompany().equals(insuranceCompany4));
            assertTrue(((FlatSurety)manager.getSuretyDao().get(flatSurety1.getUuid())).getPremium()==110);
            assertTrue(((FlatSurety)manager.getSuretyDao().get(flatSurety3.getUuid())).getPremium()==1 );
            assertTrue(((FlatSurety)manager.getSuretyDao().get(flatSurety2.getUuid())).getInsuranceCompany().equals(insuranceCompany1) );
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSuretyDao().remove(nonFlatSurety1.getUuid());
            manager.getSuretyDao().remove(nonFlatSurety2.getUuid());
            manager.getSuretyDao().remove(nonFlatSurety3.getUuid());
            manager.getSuretyDao().remove(flatSurety1.getUuid());
            manager.getSuretyDao().remove(flatSurety2.getUuid());
            manager.getSuretyDao().remove(flatSurety3.getUuid());
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
                manager.getSuretyDao().get(nonFlatSurety1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSuretyDao().get(nonFlatSurety2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSuretyDao().get(nonFlatSurety3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSuretyDao().get(flatSurety1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSuretyDao().get(flatSurety2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getSuretyDao().get(flatSurety3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }

        }
    }

}