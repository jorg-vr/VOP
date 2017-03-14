package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import dao.interfaces.VehicleTypeDao;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import org.junit.*;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by Ponti on 13/03/2017.
 */
public class ProductionVehicleDAOFiltersTest {
    private static ProductionProvider daoProvider;
    private static VehicleDAO vehicleDAO;
    private static VehicleTypeDao vehicleTypeDAO;
    private static Vehicle v1, v2, v3;
    private static VehicleType t1, t2;

    //TODO: production to false, when local
    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        //ProductionProvider.initializeProvider(true);
        //ProductionProvider.initializeProvider(false);
        daoProvider = (ProductionProvider) ProductionProvider.getInstance();
        vehicleDAO = (ProductionVehicleDAO) daoProvider.getVehicleDAO();
        vehicleTypeDAO = (ProductionVehicleTypeDAO) daoProvider.getVehicleTypeDAO();

        t1 = vehicleTypeDAO.create("type 1", 2.5);
        t2 = vehicleTypeDAO.create("type 2", 5.7);
        v1 = vehicleDAO.create("brand 1", "model 1", "AAAAAAAAAAAAAAAAA", "ABC-123", 500, 3000, t1, LocalDate.of(2016, 7, 15));
        v2 = vehicleDAO.create("brand 1", "model 2", "BBBBBBBBBBBBBBBBB", "DEF-123", 1000, 3500, t2, LocalDate.of(2016, 7, 26));
        v3 = vehicleDAO.create("brand 2", "model 2", "CCCCCCCCCCCCCCCCC", "DEF-456", 1500, 4000, t1, LocalDate.of(2016, 9, 26));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        vehicleDAO.remove(v1.getUuid());
        vehicleDAO.remove(v2.getUuid());
        vehicleDAO.remove(v3.getUuid());
        vehicleTypeDAO.remove(t1.getUuid());
        vehicleTypeDAO.remove(t2.getUuid());
    }


    /*@Before
    public void setUp() throws Exception {
        vehicleDAO = (ProductionVehicleDAO) daoProvider.getVehicleDAO();
        vehicleTypeDAO = (ProductionVehicleTypeDAO) daoProvider.getVehicleTypeDAO();

        t1 = vehicleTypeDAO.create("type 1", 2.5);
        t2 = vehicleTypeDAO.create("type 2", 5.7);
        v1 = vehicleDAO.create("brand 1", "model 1", "AAAAAAAAAAAAAAAAA", "ABC-123", 500, 3000, t1, LocalDate.of(2016, 7, 15));
        v2 = vehicleDAO.create("brand 1", "model 2", "BBBBBBBBBBBBBBBBB", "DEF-123", 1000, 3500, t2, LocalDate.of(2016, 7, 26));
        v3 = vehicleDAO.create("brand 2", "model 2", "CCCCCCCCCCCCCCCCC", "DEF-456", 1500, 4000, t1, LocalDate.of(2016, 9, 26));
    }

    @After
    public void tearDown() throws Exception {
        vehicleDAO.remove(v1.getUuid());
        vehicleDAO.remove(v2.getUuid());
        vehicleDAO.remove(v3.getUuid());
        vehicleTypeDAO.remove(t1.getUuid());
        vehicleTypeDAO.remove(t2.getUuid());
    }*/


    @Test
    public void byBrand() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.byBrand("brand 1")};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.byBrand("brand 2")};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);
        c1 = vehicleDAO.listFiltered(f1);
        assertTrue("byBrand filter werkt niet", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
        assertTrue("byBrand filter werkt niet", !c2.contains(v2) && !c2.contains(v1) && c2.contains(v3));
    }

    @Test
    public void byModel() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.byModel("model 1")};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.byModel("model 2")};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("byModel filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
        assertTrue("byModel filter werkt niet", c2.contains(v2) && c2.contains(v3) && !c2.contains(v1));
    }

    @Test
    public void byLicensePlate() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.byLicensePlate("ABC-123")};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.byLicensePlate("ABC-456")};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("byLicensePlate filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
        assertTrue("byLicensePlate filter werkt niet", !c2.contains(v3) && !c2.contains(v1) && !c2.contains(v2));
    }

    @Test
    public void atProductionDate() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.atProductionDate(LocalDate.of(2016, 7, 15))};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.atProductionDate(LocalDate.of(2016, 9, 26))};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("atProductionDate filter werkt niet", c1.contains(v1) && !c1.contains(v2) && !c1.contains(v3));
        assertTrue("atProductionDate filter werkt niet", c2.contains(v3) && !c2.contains(v2) && !c2.contains(v1));
    }

    @Test
    public void beforeProductionDate() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.beforeProductionDate(LocalDate.of(2016, 7, 27))};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.beforeProductionDate(LocalDate.of(2017, 7, 15))};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("beforeProductionDate filter werkt niet", c1.contains(v1) && c1.contains(v2) && !c1.contains(v3));
        assertTrue("beforeProductionDate filter werkt niet", c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
    }


    @Test
    public void afterProductionDate() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.afterProductionDate(LocalDate.of(2016, 7, 16))};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.afterProductionDate(LocalDate.of(2017, 7, 15))};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("afterProductionDate filter werkt niet", c1.contains(v2) && c1.contains(v3) && !c1.contains(v1));
        assertTrue("afterProductionDate filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && !c2.contains(v3));
    }

    @Test
    public void atLeastMileage() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.atLeastMileage(3500)};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.atLeastMileage(4500)};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("atLeastMileage filter werkt niet", c1.contains(v2) && c1.contains(v3) && !c1.contains(v1));
        assertTrue("atLeastMileage filter werkt niet", !c2.contains(v1) && !c2.contains(v2) && !c2.contains(v3));
    }

    @Test
    public void maxMileage() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.maxMileage(3000)};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.maxMileage(4500)};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("maxMileage filter werkt niet", c1.contains(v1));
        assertTrue("maxMileage filter werkt niet", c2.contains(v1) && c2.contains(v2) && c2.contains(v3));
    }

    @Test
    public void byType() throws Exception {
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.byType(t1)};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.byType(t2)};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);

        assertTrue("byType filter werkt niet", c1.contains(v1) && c1.contains(v3) && !c1.contains(v2));
        assertTrue("byType filter werkt niet", c2.contains(v2) && !c2.contains(v1) && !c2.contains(v3));
    }

    @Test
    public void multipleFilters() throws Exception {
        Vehicle v4 = vehicleDAO.create("brand 1", "model 2", "FAAAAAAAAAAAAAAAA", "ABC-123", 500, 4000, t2, LocalDate.of(2016, 9, 28));
        Vehicle v5 = vehicleDAO.create("brand 2", "model 1", "FBBBBBBBBBBBBBBBB", "DEF-123", 1000, 4500, t1, LocalDate.of(2017, 7, 26));
        Vehicle v6 = vehicleDAO.create("brand 1", "model 2", "FCCCCCCCCCCCCCCCC", "DEF-456", 1500, 5000, t2, LocalDate.of(2017, 9, 26));
        Filter<Vehicle>[] f1 = new Filter[]{vehicleDAO.byType(t1), vehicleDAO.byModel("model 1")};
        Filter<Vehicle>[] f2 = new Filter[]{vehicleDAO.byBrand("brand 1"), vehicleDAO.byLicensePlate("DEF-123")};
        Filter<Vehicle>[] f3 = new Filter[]{vehicleDAO.atLeastMileage(4000), vehicleDAO.beforeProductionDate(LocalDate.of(2016, 9, 28))};
        Collection<Vehicle> c1 = vehicleDAO.listFiltered(f1);
        Collection<Vehicle> c2 = vehicleDAO.listFiltered(f2);
        Collection<Vehicle> c3 = vehicleDAO.listFiltered(f3);
        Collection<Vehicle> c4 = vehicleDAO.listFiltered(new Filter[]{});
        assertTrue("Applying multiple filters simultaneously doesn't work correctly", c1.contains(v1) && c1.contains(v5));
        assertTrue("Applying multiple filters simultaneously doesn't work correctly", c2.contains(v2));
        assertTrue("Applying multiple filters simultaneously doesn't work correctly", c3.contains(v3));
        assertTrue("Empty filter",c4.contains(v1) && c4.contains(v2) && c4.contains(v3) && c4.contains(v4) && c4.contains(v5) && c4.contains(v6));
        vehicleDAO.remove(v4.getUuid());
        vehicleDAO.remove(v5.getUuid());
        vehicleDAO.remove(v6.getUuid());
    }

}