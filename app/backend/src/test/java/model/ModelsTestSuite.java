package model;

import model.fleet.FleetTest;
import model.fleet.VehicleTest;
import model.fleet.VehicleTypeTest;
import model.identity.CustomerTest;
import model.identity.LeasingCompanyTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
        FleetTest.class,
        VehicleTest.class,
        VehicleTypeTest.class,
        CustomerTest.class,
        LeasingCompanyTest.class
})


public class ModelsTestSuite {
}
