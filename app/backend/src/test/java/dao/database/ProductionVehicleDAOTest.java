package dao.database;

import dao.interfaces.DAOProvider;
import model.fleet.Vehicle;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.Assert.*;

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

    @Test
    public void createWithVehicle() throws Exception {
        try{
            Vehicle vehicle = new Vehicle();

        }
        catch (Exception e){
            fail();
        }
    }

    @Test
    public void createWithParams() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void update1() throws Exception {

    }

    @Test
    public void remove() throws Exception {

    }

    @Test
    public void remove1() throws Exception {

    }

    @Test
    public void listFiltered() throws Exception {

    }

    @Test
    public void close() throws Exception {

    }

    @Test
    public void byBrand() throws Exception {

    }

    @Test
    public void byModel() throws Exception {

    }

    @Test
    public void byLicensePlate() throws Exception {

    }

    @Test
    public void atProductionDate() throws Exception {

    }

    @Test
    public void beforeProductionDate() throws Exception {

    }

    @Test
    public void afterProductionDate() throws Exception {

    }

    @Test
    public void atLeastMileage() throws Exception {

    }

    @Test
    public void maxMileage() throws Exception {

    }

    @Test
    public void byType() throws Exception {

    }

}