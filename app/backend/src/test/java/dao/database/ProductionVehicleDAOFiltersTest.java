package dao.database;

import dao.interfaces.*;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertTrue;


public class ProductionVehicleDAOFiltersTest {
    private static DAOProvider daoProvider;
    private static Vehicle v1, v2, v3;
    private static VehicleType t1, t2;
    private static Fleet fleet1, fleet2;
    private static Customer cust1;
    private static Address a1;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");
        daoProvider = ProductionProvider.getInstance();
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             FleetDAO fleetDAO = daoProvider.getFleetDAO();
             VehicleTypeDAO vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
             VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {

            a1 = addressDAO.create(new Address("streettest n1", "59", "town 1", "9999", "country 1"));
            t1 = vehicleTypeDAO.create(new VehicleType("type 1"));
            t2 = vehicleTypeDAO.create(new VehicleType("type 2"));
            cust1 = customerDAO.create(new Customer(a1, "123", "customer 1", "456"));
            fleet1 = fleetDAO.create(new Fleet("name 1", cust1, a1));
            fleet2 = fleetDAO.create(new Fleet("name 2", cust1, a1));
            v1 = vehicleDAO.create(new Vehicle("brand 1", "model 1", "AAAAAAAAAAAAAAAAA", "ABC-123", 500, 3000, t1, LocalDate.of(2016, 7, 15), fleet1, null));
            v2 = vehicleDAO.create(new Vehicle("brand 1", "model 2", "BBBBBBBBBBBBBBBBB", "DEF-123", 1000, 3500, t2, LocalDate.of(2016, 7, 26), fleet2, null));
            v3 = vehicleDAO.create(new Vehicle("brand 2", "model 2", "CCCCCCCCCCCCCCCCC", "DEF-456", 1500, 4000, t1, LocalDate.of(2016, 9, 26), fleet2, null));
        }
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        try (AddressDAO addressDAO = daoProvider.getAddressDao();
             CustomerDAO customerDAO = daoProvider.getCustomerDAO();
             FleetDAO fleetDAO = daoProvider.getFleetDAO();
             VehicleTypeDAO vehicleTypeDAO = daoProvider.getVehicleTypeDAO();
             VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            vehicleDAO.remove(v1.getUuid());
            vehicleDAO.remove(v2.getUuid());
            vehicleDAO.remove(v3.getUuid());
            fleetDAO.remove(fleet1.getUuid());
            fleetDAO.remove(fleet2.getUuid());
            customerDAO.remove(cust1.getUuid());
            vehicleTypeDAO.remove(t1.getUuid());
            vehicleTypeDAO.remove(t2.getUuid());
            addressDAO.remove(a1.getUuid());
        }
        daoProvider.close();
    }


    @Test
    public void byBrand() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byBrand("brand 1"));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byBrand("brand 2"));
            assertTrue("byBrand filter werkt niet", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
            assertTrue("byBrand filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void byModel() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byModel("model 1"));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byModel("model 2"));
            assertTrue("byModel filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("byModel filter werkt niet", !c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void byLicensePlate() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byLicensePlate("ABC-123"));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byLicensePlate("ABC-456"));
            assertTrue("byLicensePlate filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("byLicensePlate filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && !c2.contains(v3));
        }
    }

    @Test
    public void atProductionDate() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.atProductionDate(LocalDate.of(2016, 7, 15)));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.atProductionDate(LocalDate.of(2016, 9, 26)));
            assertTrue("atProductionDate filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("atProductionDate filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void beforeProductionDate() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.beforeProductionDate(LocalDate.of(2016, 7, 27)));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.beforeProductionDate(LocalDate.of(2017, 7, 15)));
            assertTrue("beforeProductionDate filter werkt niet", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
            assertTrue("beforeProductionDate filter werkt niet", c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }


    @Test
    public void afterProductionDate() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.afterProductionDate(LocalDate.of(2016, 7, 16)));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.afterProductionDate(LocalDate.of(2017, 7, 15)));
            assertTrue("afterProductionDate filter werkt niet", !c1.contains(v1) && c1.contains(v2) && c1.contains(v3));
            assertTrue("afterProductionDate filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && !c2.contains(v3));
        }
    }

    @Test
    public void atLeastMileage() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.atLeastMileage(3500));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.atLeastMileage(4500));
            assertTrue("atLeastMileage filter werkt niet", !c1.contains(v1) && c1.contains(v2) && c1.contains(v3));
            assertTrue("atLeastMileage filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && !c2.contains(v3));
        }
    }

    @Test
    public void maxMileage() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.maxMileage(3000));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.maxMileage(4500));
            assertTrue("maxMileage filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("maxMileage filter werkt niet", c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void byType() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byType(t1));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byType(t2));
            assertTrue("byType filter werkt niet", c1.contains(v1) && !c1.contains(v2) && c1.contains(v3));
            assertTrue("byType filter werkt niet", !c2.contains(v1) && c2.contains(v2) && !c2.contains(v3));
        }
    }

    @Test
    public void byFleet() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byFleet(fleet1));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byFleet(fleet2));
            assertTrue("byFleet filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
            assertTrue("byFleet filter werkt niet", !c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
        }
    }

    @Test
    public void multipleFilters() throws Exception {
        try (VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();) {
            Vehicle v4 = new Vehicle("brand 1", "model 2", "FAAAAAAAAAAAAAAAA", "ABC-123", 500, 4000, t2, LocalDate.of(2016, 9, 28), fleet2, null);
            Vehicle v5 = new Vehicle("brand 2", "model 1", "FBBBBBBBBBBBBBBBB", "DEF-123", 1000, 4500, t1, LocalDate.of(2017, 7, 26), fleet1, null);
            Vehicle v6 = new Vehicle("brand 1", "model 2", "FCCCCCCCCCCCCCCCC", "DEF-456", 1500, 5000, t2, LocalDate.of(2017, 9, 26), fleet1, null);
            v4 = vehicleDAO.create(v4);
            v5 = vehicleDAO.create(v5);
            v6 = vehicleDAO.create(v6);
            Collection<Vehicle> c1 = vehicleDAO.listFiltered(vehicleDAO.byType(t1), vehicleDAO.byModel("model 1"));
            Collection<Vehicle> c2 = vehicleDAO.listFiltered(vehicleDAO.byBrand("brand 1"), vehicleDAO.byLicensePlate("DEF-123"));
            Collection<Vehicle> c3 = vehicleDAO.listFiltered(vehicleDAO.atLeastMileage(4000), vehicleDAO.beforeProductionDate(LocalDate.of(2016, 9, 28)));
            Collection<Vehicle> c4 = vehicleDAO.listFiltered();
            assertTrue("Applying multiple filters simultaneously doesn't work correctly", c1.contains(v1) && c1.contains(v5));
            assertTrue("Applying multiple filters simultaneously doesn't work correctly", c2.contains(v2));
            assertTrue("Applying multiple filters simultaneously doesn't work correctly", c3.contains(v3));
            assertTrue("Empty filter", c4.contains(v1) && c4.contains(v2) && c4.contains(v3) && c4.contains(v4) && c4.contains(v5) && c4.contains(v6));
            vehicleDAO.remove(v4.getUuid());
            vehicleDAO.remove(v5.getUuid());
            vehicleDAO.remove(v6.getUuid());
        }
    }

}