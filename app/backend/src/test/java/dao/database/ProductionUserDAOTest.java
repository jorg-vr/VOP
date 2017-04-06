package dao.database;

import dao.interfaces.DAOProvider;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

public class ProductionUserDAOTest {

    private static DAOProvider daoProvider;

    @BeforeClass
    public static void init() throws Exception {

        daoProvider = ProductionProvider.getInstance();
    }

}