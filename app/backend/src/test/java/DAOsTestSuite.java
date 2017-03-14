import dao.database.ProductionVehicleDAOFiltersTest;
import dao.database.ProductionVehicleDAOTest;
import dao.database.ProductionVehicleTypeDAOFiltersTest;
import dao.database.ProductionVehicleTypeDAOTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by tjupo on 13/03/2017.
 */

@RunWith(Suite.class)

//Add DAO test classes whenever a new test class is made.
@Suite.SuiteClasses({
        ProductionVehicleDAOTest.class,
        ProductionVehicleDAOFiltersTest.class,
        ProductionVehicleTypeDAOTest.class,
        ProductionVehicleTypeDAOFiltersTest.class
})

public class DAOsTestSuite {
}
