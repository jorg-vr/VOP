import dao.database.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by tjupo on 13/03/2017.
 */

@RunWith(Suite.class)

//Add DAO test classes whenever a new test class is made.
@Suite.SuiteClasses({
        ProductionAccountDAOFiltersTest.class,
        ProductionAccountDAOTest.class,
        ProductionAddressDAOFiltersTest.class,
        ProductionAddressDAOTest.class,
        ProductionCustomerDAOFiltersTest.class,
        ProductionCustomerDAOTest.class,
        ProductionFleetDAOTest.class,
        ProductionFunctionDAOTest.class,
        ProductionRoleDAOTest.class,
        ProductionVehicleDAOFiltersTest.class,
        ProductionVehicleDAOTest.class,
        ProductionVehicleTypeDAOFiltersTest.class,
        ProductionVehicleTypeDAOTest.class
})

public class DAOsTestSuite {
}
