package database.dao;

import dao.database.ProductionProvider;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import model.insurance.FlatSurety;
import model.insurance.Surety;
import model.insurance.VehicleInsurance;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Created by sam on 5/21/17.
 */
public class ProductionVehicleInsuranceDAOTest {

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
    public void byVehicle() throws Exception {
        FlatSurety surety1 = new FlatSurety();
        surety1.setPremium(20);

        FlatSurety surety2 = new FlatSurety();
        surety2.setPremium(10);

        VehicleType type = new VehicleType();
        type.setType("test");

        Address address = new Address();
        address.setCountry("tttt");
        address.setPostalCode("9000");
        address.setTown("town");
        address.setStreet("straat");
        address.setStreetNumber("10");

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName("klant");

        Fleet fleet = new Fleet();
        fleet.setName("fleet1");
        fleet.setOwner(customer);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("123-123");
        vehicle1.setFleet(fleet);
        vehicle1.setValue(10);
        vehicle1.setVin("AAAAAAAAAAAAAAAAA");
        vehicle1.setYear(LocalDate.now());
        vehicle1.setType(type);
        vehicle1.setModel("model");
        vehicle1.setBrand("brand");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("123-1C3");
        vehicle2.setFleet(fleet);
        vehicle2.setValue(10);
        vehicle2.setVin("AAAAAAAAAAAAAAAAB");
        vehicle2.setYear(LocalDate.now());
        vehicle2.setType(type);
        vehicle2.setModel("model");
        vehicle2.setBrand("brand");

        VehicleInsurance insurance1 = new VehicleInsurance();
        insurance1.setSurety(surety1);
        insurance1.setFranchise(10);
        insurance1.setVehicle(vehicle1);
        VehicleInsurance insurance2 = new VehicleInsurance();
        insurance2.setSurety(surety2);
        insurance2.setFranchise(10);
        insurance2.setVehicle(vehicle2);
        VehicleInsurance insurance3 = new VehicleInsurance();
        insurance3.setSurety(surety2);
        insurance3.setFranchise(10);
        insurance3.setVehicle(vehicle1);

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSuretyDao().create(surety1);
            manager.getSuretyDao().create(surety2);
            manager.getCustomerDAO().create(customer);
            manager.getFleetDAO().create(fleet);
            manager.getVehicleTypeDAO().create(type);
            manager.getVehicleDAO().create(vehicle1);
            manager.getVehicleDAO().create(vehicle2);
            manager.getVehicleInsuranceDao().create(insurance1);
            manager.getVehicleInsuranceDao().create(insurance2);
            manager.getVehicleInsuranceDao().create(insurance3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<VehicleInsurance> insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().byVehicle(vehicle1));
            assertTrue(insurances.contains(insurance1));
            assertTrue(!insurances.contains(insurance2));
            assertTrue(insurances.contains(insurance3));
            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().byVehicle(vehicle2));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(!insurances.contains(insurance3));
        }

    }

    @Test
    public void timeFilter() throws Exception {
        FlatSurety surety1 = new FlatSurety();
        surety1.setPremium(20);

        FlatSurety surety2 = new FlatSurety();
        surety2.setPremium(10);

        VehicleType type = new VehicleType();
        type.setType("test");

        Address address = new Address();
        address.setCountry("tttt");
        address.setPostalCode("9000");
        address.setTown("town");
        address.setStreet("straat");
        address.setStreetNumber("10");

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName("klant");

        Fleet fleet = new Fleet();
        fleet.setName("fleet1");
        fleet.setOwner(customer);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("123-123");
        vehicle1.setFleet(fleet);
        vehicle1.setValue(10);
        vehicle1.setVin("AAAAAAAAAAAAAAAAA");
        vehicle1.setYear(LocalDate.now());
        vehicle1.setType(type);
        vehicle1.setModel("model");
        vehicle1.setBrand("brand");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("123-1C3");
        vehicle2.setFleet(fleet);
        vehicle2.setValue(10);
        vehicle2.setVin("AAAAAAAAAAAAAAAAB");
        vehicle2.setYear(LocalDate.now());
        vehicle2.setType(type);
        vehicle2.setModel("model");
        vehicle2.setBrand("brand");

        VehicleInsurance insurance1 = new VehicleInsurance();
        insurance1.setSurety(surety1);
        insurance1.setFranchise(10);
        insurance1.setVehicle(vehicle1);
        insurance1.setStartDate(LocalDateTime.of(2017, 5, 6, 1, 1));
        insurance1.setEndDate(LocalDateTime.of(2017, 6, 6, 1, 1));
        VehicleInsurance insurance2 = new VehicleInsurance();
        insurance2.setSurety(surety2);
        insurance2.setFranchise(10);
        insurance2.setVehicle(vehicle2);
        insurance2.setStartDate(LocalDateTime.of(2015, 5, 6, 1, 1));
        insurance2.setEndDate(LocalDateTime.of(2016, 6, 6, 1, 1));
        VehicleInsurance insurance3 = new VehicleInsurance();
        insurance3.setSurety(surety2);
        insurance3.setFranchise(10);
        insurance3.setVehicle(vehicle1);
        insurance3.setStartDate(LocalDateTime.of(2014, 5, 6, 1, 1));
        insurance3.setEndDate(LocalDateTime.of(2018, 6, 6, 1, 1));

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSuretyDao().create(surety1);
            manager.getSuretyDao().create(surety2);
            manager.getCustomerDAO().create(customer);
            manager.getFleetDAO().create(fleet);
            manager.getVehicleTypeDAO().create(type);
            manager.getVehicleDAO().create(vehicle1);
            manager.getVehicleDAO().create(vehicle2);
            manager.getVehicleInsuranceDao().create(insurance1);
            manager.getVehicleInsuranceDao().create(insurance2);
            manager.getVehicleInsuranceDao().create(insurance3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            Collection<VehicleInsurance> insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().startsBefore(LocalDateTime.of(2018, 1, 1, 1, 1)));
            assertTrue(insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().startsBefore(LocalDateTime.of(2016, 1, 1, 1, 1)));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().startsBefore(LocalDateTime.of(2015, 1, 1, 1, 1)));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(!insurances.contains(insurance2));
            assertTrue(insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().startsBefore(LocalDateTime.of(2014, 1, 1, 1, 1)));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(!insurances.contains(insurance2));
            assertTrue(!insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().endsAfter(LocalDateTime.of(2016, 1, 1, 1, 1)));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(!insurances.contains(insurance2));
            assertTrue(!insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().endsAfter(LocalDateTime.of(2017, 1, 1, 1, 1)));
            assertTrue(!insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(!insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().endsAfter(LocalDateTime.of(2018, 1, 1, 1, 1)));
            assertTrue(insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(!insurances.contains(insurance3));

            insurances = manager.getVehicleInsuranceDao().listFiltered(manager.getVehicleInsuranceDao().endsAfter(LocalDateTime.of(2019, 1, 1, 1, 1)));
            assertTrue(insurances.contains(insurance1));
            assertTrue(insurances.contains(insurance2));
            assertTrue(insurances.contains(insurance3));
        }

    }

    @Test
    public void create() throws Exception {
        FlatSurety surety1 = new FlatSurety();
        surety1.setPremium(20);

        FlatSurety surety2 = new FlatSurety();
        surety2.setPremium(10);

        VehicleType type = new VehicleType();
        type.setType("test");

        Address address = new Address();
        address.setCountry("tttt");
        address.setPostalCode("9000");
        address.setTown("town");
        address.setStreet("straat");
        address.setStreetNumber("10");

        Customer customer = new Customer();
        customer.setAddress(address);
        customer.setName("klant");

        Fleet fleet = new Fleet();
        fleet.setName("fleet1");
        fleet.setOwner(customer);

        Vehicle vehicle1 = new Vehicle();
        vehicle1.setLicensePlate("123-123");
        vehicle1.setFleet(fleet);
        vehicle1.setValue(10);
        vehicle1.setVin("AAAAAAAAAAAAAAAAA");
        vehicle1.setYear(LocalDate.now());
        vehicle1.setType(type);
        vehicle1.setModel("model");
        vehicle1.setBrand("brand");

        Vehicle vehicle2 = new Vehicle();
        vehicle2.setLicensePlate("123-1C3");
        vehicle2.setFleet(fleet);
        vehicle2.setValue(10);
        vehicle2.setVin("AAAAAAAAAAAAAAAAB");
        vehicle2.setYear(LocalDate.now());
        vehicle2.setType(type);
        vehicle2.setModel("model");
        vehicle2.setBrand("brand");

        VehicleInsurance insurance1 = new VehicleInsurance();
        insurance1.setSurety(surety1);
        insurance1.setFranchise(10);
        insurance1.setVehicle(vehicle1);
        insurance1.setStartDate(LocalDateTime.of(2017, 5, 6, 1, 1));
        insurance1.setEndDate(LocalDateTime.of(2017, 6, 6, 1, 1));
        VehicleInsurance insurance2 = new VehicleInsurance();
        insurance2.setSurety(surety2);
        insurance2.setFranchise(10);
        insurance2.setVehicle(vehicle2);
        insurance2.setStartDate(LocalDateTime.of(2015, 5, 6, 1, 1));
        insurance2.setEndDate(LocalDateTime.of(2016, 6, 6, 1, 1));
        VehicleInsurance insurance3 = new VehicleInsurance();
        insurance3.setSurety(surety2);
        insurance3.setFranchise(10);
        insurance3.setVehicle(vehicle1);
        insurance3.setStartDate(LocalDateTime.of(2014, 5, 6, 1, 1));
        insurance3.setEndDate(LocalDateTime.of(2018, 6, 6, 1, 1));

        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getSuretyDao().create(surety1);
            manager.getSuretyDao().create(surety2);
            manager.getCustomerDAO().create(customer);
            manager.getFleetDAO().create(fleet);
            manager.getVehicleTypeDAO().create(type);
            manager.getVehicleDAO().create(vehicle1);
            manager.getVehicleDAO().create(vehicle2);
            manager.getVehicleInsuranceDao().create(insurance1);
            manager.getVehicleInsuranceDao().create(insurance2);
            manager.getVehicleInsuranceDao().create(insurance3);
        }
        insurance1.setFranchise(1000000);
        insurance1.setVehicle(vehicle2);
        insurance2.setSurety(surety1);
        insurance2.setEndDate(LocalDateTime.of(2000, 1, 1, 1, 1));
        insurance3.setStartDate(LocalDateTime.of(2222, 1, 1, 1, 1));
        insurance3.setInsuredValue(500000);
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleInsuranceDao().update(insurance1);
            manager.getVehicleInsuranceDao().update(insurance2);
            manager.getVehicleInsuranceDao().update(insurance3);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            VehicleInsurance tmp1 = manager.getVehicleInsuranceDao().update(insurance1);
            VehicleInsurance tmp2 = manager.getVehicleInsuranceDao().update(insurance2);
            VehicleInsurance tmp3 = manager.getVehicleInsuranceDao().update(insurance3);
            assertTrue(tmp1.getFranchise() == 1000000);
            assertTrue(tmp1.getVehicle().equals(vehicle2));
            assertTrue(tmp2.getSurety().equals(surety1));
            assertTrue(tmp2.getEndDate().equals(LocalDateTime.of(2000, 1, 1, 1, 1)));
            assertTrue(tmp3.getStartDate().equals(LocalDateTime.of(2222, 1, 1, 1, 1)));
            assertTrue(tmp3.getInsuredValue() == 500000);
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            manager.getVehicleInsuranceDao().remove(insurance1.getUuid());
            manager.getVehicleInsuranceDao().remove(insurance2.getUuid());
            manager.getVehicleInsuranceDao().remove(insurance3.getUuid());
        }
        try (DAOManager manager = ProductionProvider.getInstance().getDaoManager()) {
            try {
                manager.getVehicleInsuranceDao().get(insurance1.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getVehicleInsuranceDao().get(insurance2.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
            try {
                manager.getVehicleInsuranceDao().get(insurance3.getUuid());
                Assert.fail();
            } catch (ObjectNotFoundException e) {

            }
        }

    }

}