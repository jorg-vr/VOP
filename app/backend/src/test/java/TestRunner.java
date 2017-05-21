import database.consistency.VehiclesCollectionTest;
import database.constraints.*;
import database.dao.*;
import model.fleet.VehicleTest;
import model.insurance.NonFlatSuretyTest;
import model.insurance.VehicleInsuranceTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import spring.TestPagination;
import spring.controller.*;


public class TestRunner {
    public static void main(String[] args) {
        Result modelResults1 = null, modelResults2 = null, modelResults3 = null;
        Result daoResults = null;
        //ProductionProvider.initializeProvider("unittest");

        if (args.length == 1 && args[0].equals("model")) {
            runModelTests();
        }

        if (args.length == 1 && args[0].equals("dao")) {
            runDAOTests();
        }

        if (args.length == 1 && args[0].equals("controller")) {
            runRESTControllerTests();
        }

        if (args.length == 1 && args[0].equals("database")) {
            runDatabaseTests();
        }

        if (args.length == 0){
            runAllTests();
        }
    }

    private static void runModelTests() {
        Result modelResults1 = JUnitCore.runClasses(VehicleTest.class);
        Result modelResults2 = JUnitCore.runClasses(NonFlatSuretyTest.class);
        Result modelResults3 = JUnitCore.runClasses(VehicleInsuranceTest.class);
        System.out.print("\n\n\n");
        System.out.println("Models unit tests:");

        System.out.printf("%-25s %s\n", "VehicleTest:", String.valueOf(modelResults1.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "NonFlatSuretyTest:", String.valueOf(modelResults2.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleInsuranceTest:", String.valueOf(modelResults3.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }

    private static void runDAOTests() {
        Result daoResult1 = JUnitCore.runClasses(ProductionAddressDAOTest.class);
        Result daoResult2 = JUnitCore.runClasses(ProductionCustomerDAOTest.class);
        Result daoResult3 = JUnitCore.runClasses(ProductionFleetDAOTest.class);
        Result daoResult4 = JUnitCore.runClasses(ProductionFunctionDAOTest.class);
        Result daoResult5 = JUnitCore.runClasses(ProductionRoleDAOTest.class);
        Result daoResult6 = JUnitCore.runClasses(ProductionUserDAOTest.class);
        Result daoResult7 = JUnitCore.runClasses(ProductionVehicleDAOTest.class);
        Result daoResult8 = JUnitCore.runClasses(ProductionVehicleTypeDAOTest.class);
        Result daoResult9 = JUnitCore.runClasses(ProductionAddressDAOFiltersTest.class);
        Result daoResult10 = JUnitCore.runClasses(ProductionCustomerDAOFiltersTest.class);
        Result daoResult11 = JUnitCore.runClasses(ProductionVehicleDAOFiltersTest.class);
        Result daoResult12 = JUnitCore.runClasses(ProductionVehicleTypeDAOFiltersTest.class);
        System.out.print("\n\n\n");
        System.out.println("DAO unit tests:");

        System.out.printf("%-25s %s\n", "AddressDAO:", String.valueOf(daoResult1.wasSuccessful()));
        for (Failure failure : daoResult1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CustomerDAO:", String.valueOf(daoResult2.wasSuccessful()));
        for (Failure failure : daoResult2.getFailures()) {
            System.out.println(failure.getTrace());
        }
        System.out.printf("%-25s %s\n", "FleetDAO:", String.valueOf(daoResult3.wasSuccessful()));
        for (Failure failure : daoResult3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FunctionDAO:", String.valueOf(daoResult4.wasSuccessful()));
        for (Failure failure : daoResult4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RoleDAO:", String.valueOf(daoResult5.wasSuccessful()));
        for (Failure failure : daoResult5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UserDAO:", String.valueOf(daoResult6.wasSuccessful()));
        for (Failure failure : daoResult6.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleDAO:", String.valueOf(daoResult7.wasSuccessful()));
        for (Failure failure : daoResult7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleTypeDAO:", String.valueOf(daoResult8.wasSuccessful()));
        for (Failure failure : daoResult8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "AddressDAOFilters:", String.valueOf(daoResult9.wasSuccessful()));
        for (Failure failure : daoResult9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CustomerDAOFilters:", String.valueOf(daoResult10.wasSuccessful()));
        for (Failure failure : daoResult10.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleDAOFilters:", String.valueOf(daoResult11.wasSuccessful()));
        for (Failure failure : daoResult11.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleTypeDAO:", String.valueOf(daoResult12.wasSuccessful()));
        for (Failure failure : daoResult12.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }

    private static void runRESTControllerTests(){
        Result controllerResults1 = JUnitCore.runClasses(RESTCompanyControllerTest.class);
        Result controllerResults2 = JUnitCore.runClasses(RESTFleetControllerTest.class);
        Result controllerResults3 = JUnitCore.runClasses(RESTRoleControllerTest.class);
        //Result controllerResults4 = JUnitCore.runClasses(RESTUserControllerTest.class);
        Result controllerResults5 = JUnitCore.runClasses(UUIDUtilTest.class);
        Result controllerResults6 = JUnitCore.runClasses(TestPagination.class);
        System.out.print("\n\n\n");
        System.out.println("RESTControllers integration tests:");

        System.out.printf("%-25s %s\n", "RESTCompanyController:", String.valueOf(controllerResults1.wasSuccessful()));
        for (Failure failure : controllerResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTFleetController:", String.valueOf(controllerResults2.wasSuccessful()));
        for (Failure failure : controllerResults2.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTRoleController:", String.valueOf(controllerResults3.wasSuccessful()));
        for (Failure failure : controllerResults3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        /*System.out.printf("%-25s %s\n", "RESTUserController:", String.valueOf(controllerResults4.wasSuccessful()));
        for (Failure failure : controllerResults4.getFailures()) {
            System.out.println(failure.getMessage());
        }*/
        System.out.printf("%-25s %s\n", "UUIDUtil:", String.valueOf(controllerResults5.wasSuccessful()));
        for (Failure failure : controllerResults5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "Pagination:", String.valueOf(controllerResults6.wasSuccessful()));
        for (Failure failure : controllerResults6.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }

    private static void runDatabaseTests(){
        Result databaseResult1 = JUnitCore.runClasses(AddressParametersTest.class);
        Result databaseResult2 = JUnitCore.runClasses(CompanyParametersTest.class);
        Result databaseResult3 = JUnitCore.runClasses(ContractParametersTest.class);
        Result databaseResult4 = JUnitCore.runClasses(FleetParametersTest.class);
        Result databaseResult5 = JUnitCore.runClasses(FunctionParametersTest.class);
//        Result databaseResult6 = JUnitCore.runClasses(InvoiceParametersTest.class);
        Result databaseResult7 = JUnitCore.runClasses(RoleParametersTest.class);
        Result databaseResult8 = JUnitCore.runClasses(UserParametersTest.class);
        Result databaseResult9 = JUnitCore.runClasses(VehicleParametersTest.class);
        Result databaseResult10 = JUnitCore.runClasses(VehiclesCollectionTest.class);
        System.out.print("\n\n\n");
        System.out.println("Database consistency tests:");

        System.out.printf("%-25s %s\n", "AddressParameters:", String.valueOf(databaseResult1.wasSuccessful()));
        for (Failure failure : databaseResult1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CompanyParameters:", String.valueOf(databaseResult2.wasSuccessful()));
        for (Failure failure : databaseResult2.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "ContractParameters:", String.valueOf(databaseResult3.wasSuccessful()));
        for (Failure failure : databaseResult3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FleetParameters:", String.valueOf(databaseResult4.wasSuccessful()));
        for (Failure failure : databaseResult4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FunctionParameters:", String.valueOf(databaseResult5.wasSuccessful()));
        for (Failure failure : databaseResult5.getFailures()) {
            System.out.println(failure.getMessage());
        }
//        System.out.printf("%-25s %s\n", "InvoiceParameters:", String.valueOf(databaseResult6.wasSuccessful()));
//        for (Failure failure : databaseResult6.getFailures()) {
//            System.out.println(failure.getMessage());
//        }
        System.out.printf("%-25s %s\n", "RoleParameters:", String.valueOf(databaseResult7.wasSuccessful()));
        for (Failure failure : databaseResult7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UserParameters:", String.valueOf(databaseResult8.wasSuccessful()));
        for (Failure failure : databaseResult8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleParameters:", String.valueOf(databaseResult9.wasSuccessful()));
        for (Failure failure : databaseResult9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehiclesCollection:", String.valueOf(databaseResult10.wasSuccessful()));
        for (Failure failure : databaseResult10.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }

    private static void runAllTests(){
        Result modelResults1 = JUnitCore.runClasses(VehicleTest.class);
        Result modelResults2 = JUnitCore.runClasses(NonFlatSuretyTest.class);
        Result modelResults3 = JUnitCore.runClasses(VehicleInsuranceTest.class);
        Result daoResult1 = JUnitCore.runClasses(ProductionAddressDAOTest.class);
        Result daoResult2 = JUnitCore.runClasses(ProductionCustomerDAOTest.class);
        Result daoResult3 = JUnitCore.runClasses(ProductionFleetDAOTest.class);
        Result daoResult4 = JUnitCore.runClasses(ProductionFunctionDAOTest.class);
        Result daoResult5 = JUnitCore.runClasses(ProductionRoleDAOTest.class);
        Result daoResult6 = JUnitCore.runClasses(ProductionUserDAOTest.class);
        Result daoResult7 = JUnitCore.runClasses(ProductionVehicleDAOTest.class);
        Result daoResult8 = JUnitCore.runClasses(ProductionVehicleTypeDAOTest.class);
        Result daoResult9 = JUnitCore.runClasses(ProductionAddressDAOFiltersTest.class);
        Result daoResult10 = JUnitCore.runClasses(ProductionCustomerDAOFiltersTest.class);
        Result daoResult11 = JUnitCore.runClasses(ProductionVehicleDAOFiltersTest.class);
        Result daoResult12 = JUnitCore.runClasses(ProductionVehicleTypeDAOFiltersTest.class);
        Result controllerResults1 = JUnitCore.runClasses(RESTCompanyControllerTest.class);
        Result controllerResults2 = JUnitCore.runClasses(RESTFleetControllerTest.class);
        Result controllerResults3 = JUnitCore.runClasses(RESTRoleControllerTest.class);
        //Result controllerResults4 = JUnitCore.runClasses(RESTUserControllerTest.class);
        Result controllerResults5 = JUnitCore.runClasses(UUIDUtilTest.class);
        Result controllerResults6 = JUnitCore.runClasses(TestPagination.class);
        Result databaseResult1 = JUnitCore.runClasses(AddressParametersTest.class);
        Result databaseResult2 = JUnitCore.runClasses(CompanyParametersTest.class);
        Result databaseResult3 = JUnitCore.runClasses(ContractParametersTest.class);
        Result databaseResult4 = JUnitCore.runClasses(FleetParametersTest.class);
        Result databaseResult5 = JUnitCore.runClasses(FunctionParametersTest.class);
//        Result databaseResult6 = JUnitCore.runClasses(InvoiceParametersTest.class);
        Result databaseResult7 = JUnitCore.runClasses(RoleParametersTest.class);
        Result databaseResult8 = JUnitCore.runClasses(UserParametersTest.class);
        Result databaseResult9 = JUnitCore.runClasses(VehicleParametersTest.class);
        Result databaseResult10 = JUnitCore.runClasses(VehiclesCollectionTest.class);

        System.out.print("\n\n\n");
        System.out.println("Models unit tests:");
        System.out.printf("%-25s %s\n", "VehicleTest:", String.valueOf(modelResults1.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "NonFlatSuretyTest:", String.valueOf(modelResults2.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleInsuranceTest:", String.valueOf(modelResults3.wasSuccessful()));
        for (Failure failure : modelResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }


        System.out.println();
        System.out.println("DAO unit tests:");

        System.out.printf("%-25s %s\n", "AddressDAO:", String.valueOf(daoResult1.wasSuccessful()));
        for (Failure failure : daoResult1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CustomerDAO:", String.valueOf(daoResult2.wasSuccessful()));
        for (Failure failure : daoResult2.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FleetDAO:", String.valueOf(daoResult3.wasSuccessful()));
        for (Failure failure : daoResult3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FunctionDAO:", String.valueOf(daoResult4.wasSuccessful()));
        for (Failure failure : daoResult4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RoleDAO:", String.valueOf(daoResult5.wasSuccessful()));
        for (Failure failure : daoResult5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UserDAO:", String.valueOf(daoResult6.wasSuccessful()));
        for (Failure failure : daoResult6.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleDAO:", String.valueOf(daoResult7.wasSuccessful()));
        for (Failure failure : daoResult7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleTypeDAO:", String.valueOf(daoResult8.wasSuccessful()));
        for (Failure failure : daoResult8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "AddressDAOFilters:", String.valueOf(daoResult9.wasSuccessful()));
        for (Failure failure : daoResult9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CustomerDAOFilters:", String.valueOf(daoResult10.wasSuccessful()));
        for (Failure failure : daoResult10.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleDAOFilters:", String.valueOf(daoResult11.wasSuccessful()));
        for (Failure failure : daoResult11.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleTypeDAO:", String.valueOf(daoResult12.wasSuccessful()));
        for (Failure failure : daoResult12.getFailures()) {
            System.out.println(failure.getMessage());
        }


        System.out.println();
        System.out.println("RESTControllers integration tests:");

        System.out.printf("%-25s %s\n", "RESTCompanyController:", String.valueOf(controllerResults1.wasSuccessful()));
        for (Failure failure : controllerResults1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTFleetController:", String.valueOf(controllerResults2.wasSuccessful()));
        for (Failure failure : controllerResults2.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTRoleController:", String.valueOf(controllerResults3.wasSuccessful()));
        for (Failure failure : controllerResults3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        /*System.out.printf("%-25s %s\n", "RESTUserController:", String.valueOf(controllerResults4.wasSuccessful()));
        for (Failure failure : controllerResults4.getFailures()) {
            System.out.println(failure.getMessage());
        }*/
        System.out.printf("%-25s %s\n", "UUIDUtil:", String.valueOf(controllerResults5.wasSuccessful()));
        for (Failure failure : controllerResults5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "Pagination:", String.valueOf(controllerResults6.wasSuccessful()));
        for (Failure failure : controllerResults6.getFailures()) {
            System.out.println(failure.getMessage());
        }


        System.out.println();
        System.out.println("Database consistency tests:");

        System.out.printf("%-25s %s\n", "AddressParameters:", String.valueOf(databaseResult1.wasSuccessful()));
        for (Failure failure : databaseResult1.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "CompanyParameters:", String.valueOf(databaseResult2.wasSuccessful()));
        for (Failure failure : databaseResult2.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "ContractParameters:", String.valueOf(databaseResult3.wasSuccessful()));
        for (Failure failure : databaseResult3.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FleetParameters:", String.valueOf(databaseResult4.wasSuccessful()));
        for (Failure failure : databaseResult4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FunctionParameters:", String.valueOf(databaseResult5.wasSuccessful()));
        for (Failure failure : databaseResult5.getFailures()) {
            System.out.println(failure.getMessage());
        }
//        System.out.printf("%-25s %s\n", "InvoiceParameters:", String.valueOf(databaseResult6.wasSuccessful()));
//        for (Failure failure : databaseResult6.getFailures()) {
//            System.out.println(failure.getMessage());
//        }
        System.out.printf("%-25s %s\n", "RoleParameters:", String.valueOf(databaseResult7.wasSuccessful()));
        for (Failure failure : databaseResult7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UserParameters:", String.valueOf(databaseResult8.wasSuccessful()));
        for (Failure failure : databaseResult8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleParameters:", String.valueOf(databaseResult9.wasSuccessful()));
        for (Failure failure : databaseResult9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehiclesCollection:", String.valueOf(databaseResult10.wasSuccessful()));
        for (Failure failure : databaseResult10.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }
}
