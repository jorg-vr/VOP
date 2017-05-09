package dao.database;

import dao.interfaces.DAOManager;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.VehicleType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Collection;

import static dao.database.DAOTestUtil.createVehicleType;
import static dao.database.DAOTestUtil.removeVehicleType;
import static org.junit.Assert.assertTrue;


public class ProductionVehicleTypeDAOFiltersTest {

    private static VehicleType t1, t2;

    //Setup before any of the tests are started
    @BeforeClass
    public static void initProvider() throws Exception {
        ProductionProvider.initializeProvider("unittest");

        t1 = createVehicleType(new VehicleType("type 1"));
        t2 = createVehicleType(new VehicleType("type 2"));
    }

    //Gets executed after all tests have been run
    @AfterClass
    public static void closeProvider() throws Exception {
        removeVehicleType(t1.getUuid());
        removeVehicleType(t2.getUuid());

        ProductionProvider.getInstance().close();
    }


    @Test
    public void byName() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            Collection<VehicleType> c1 = vehicleTypeDAO.listFiltered(vehicleTypeDAO.byName("type 1"));
            Collection<VehicleType> c2 = vehicleTypeDAO.listFiltered(vehicleTypeDAO.byName("type 2"));
            assertTrue("byName filter werkt niet", c1.contains(t1) && !c1.contains(t2));
            assertTrue("byName filter werkt niet", !c2.contains(t1) && c2.contains(t2));
        }
    }

    @Test
    public void nameContains() throws Exception {
        try (DAOManager daoManager = ProductionProvider.getInstance().getDaoManager()) {
            VehicleTypeDAO vehicleTypeDAO = daoManager.getVehicleTypeDAO();
            Collection<VehicleType> c1 = vehicleTypeDAO.listFiltered(vehicleTypeDAO.nameContains("pe "));
            Collection<VehicleType> c2 = vehicleTypeDAO.listFiltered(vehicleTypeDAO.nameContains("2"));
            assertTrue("nameContains('pe ') filter does not work", c1.contains(t1) && c1.contains(t2));
            for (VehicleType type : c1) {
                assertTrue("nameContains('pe ') filter regex test does not pas", type.getType().matches(".*pe .*"));
            }
            assertTrue("nameContains('2') filter does not work", c2.contains(t2));
            for (VehicleType type : c2) {
                assertTrue("nameContains('2') filter regex test does not pas", type.getType().matches(".*2.*"));
            }
        }
    }

    //TODO: test multiple filters in 1 request
}
