import dao.database.*;
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
        ProductionVehicleTypeDAOFiltersTest.class,
        ProductionCustomerDAOTest.class,
        ProductionAddressDAOTest.class,
        ProductionAddressDAOFiltersTest.class
})

public class DAOsTestSuite {
}
