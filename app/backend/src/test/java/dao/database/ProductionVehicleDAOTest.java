package dao.database;

import model.fleet.Vehicle;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.fail;

/**
 * Created by tjupo on 13/03/2017.
 */
public class ProductionVehicleDAOTest {

    private ProductionProvider daoProvider;
    private ProductionVehicleDAO vehicleDao;

    @Before
    public void setUp() throws Exception {
        daoProvider = (ProductionProvider) ProductionProvider.getInstance();
        vehicleDao = (ProductionVehicleDAO) daoProvider.getVehicleDAO();
        //TODO: Set up sessionfactory and other recuirements to interact with the database
    }

    @After
    public void tearDown() throws Exception {

    }

    @Ignore
    @Test
    public void createWithVehicle() throws Exception {
        try{
            Vehicle vehicle = new Vehicle();

        }
        catch (Exception e){
            fail();
        }
    }

    @Ignore
    @Test
    public void createWithParams() throws Exception {

    }

    @Ignore
    @Test
    public void get() throws Exception {

    }

    @Ignore
    @Test
    public void update() throws Exception {

    }

    @Ignore
    @Test
    public void update1() throws Exception {

    }

    @Ignore
    @Test
    public void remove() throws Exception {

    }

    @Ignore
    @Test
    public void remove1() throws Exception {

    }

    @Ignore
    @Test
    public void listFiltered() throws Exception {

    }

    @Ignore
    @Test
    public void close() throws Exception {

    }

    @Ignore
    @Test
    public void byBrand() throws Exception {

    }

    @Test
    public void byModel() throws Exception {

    }

    @Ignore
    @Test
    public void byLicensePlate() throws Exception {

    }

    @Ignore
    @Test
    public void atProductionDate() throws Exception {

    }

    @Ignore
    @Test
    public void beforeProductionDate() throws Exception {

    }

    @Ignore
    @Test
    public void afterProductionDate() throws Exception {

    }

    @Ignore
    @Test
    public void atLeastMileage() throws Exception {

    }

    @Ignore
    @Test
    public void maxMileage() throws Exception {

    }

    @Ignore
    @Test
    public void byType() throws Exception {

    }

}