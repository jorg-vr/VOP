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
import spring.controller.insurance.RESTContractControllerTest;
import spring.controller.insurance.RESTSpecialConditionControllerTest;
import spring.controller.insurance.RESTSuretyControllerTest;
import spring.controller.insurance.RESTVehicleInsuranceControllerTest;


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
        Result daoResult13 = JUnitCore.runClasses(ProductionContractDAOTest.class);
        Result daoResult14 = JUnitCore.runClasses(ProductionFlatSuretyDAOTest.class);
        Result daoResult15 = JUnitCore.runClasses(ProductionInsuranceCompanyDAOTest.class);
        Result daoResult16 = JUnitCore.runClasses(ProductionInvoiceDAOTest.class);
        Result daoResult17 = JUnitCore.runClasses(ProductionLogEntryDAOTest.class);
        Result daoResult18 = JUnitCore.runClasses(ProductionNonFlatSuretyDAOTest.class);
        Result daoResult19 = JUnitCore.runClasses(ProductionSpecialConditionDAOTest.class);
        Result daoResult20 = JUnitCore.runClasses(ProductionSuretyDAOTest.class);
        Result daoResult21 = JUnitCore.runClasses(ProductionVehicleInsuranceDAOTest.class);
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
        System.out.printf("%-25s %s\n", "ContractDAO:", String.valueOf(daoResult13.wasSuccessful()));
        for (Failure failure : daoResult13.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FlatSuretyDAO:", String.valueOf(daoResult14.wasSuccessful()));
        for (Failure failure : daoResult14.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "InsuranceCompanyDAO:", String.valueOf(daoResult15.wasSuccessful()));
        for (Failure failure : daoResult15.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "InvoiceDAO:", String.valueOf(daoResult16.wasSuccessful()));
        for (Failure failure : daoResult16.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "LogEntryDAO:", String.valueOf(daoResult17.wasSuccessful()));
        for (Failure failure : daoResult17.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "NonFlatSuretyDAO:", String.valueOf(daoResult18.wasSuccessful()));
        for (Failure failure : daoResult18.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "SpecialConditionDAO:", String.valueOf(daoResult19.wasSuccessful()));
        for (Failure failure : daoResult19.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "SuretyDAO:", String.valueOf(daoResult20.wasSuccessful()));
        for (Failure failure : daoResult20.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleInsuranceDAO:", String.valueOf(daoResult21.wasSuccessful()));
        for (Failure failure : daoResult21.getFailures()) {
            System.out.println(failure.getMessage());
        }
    }

    private static void runRESTControllerTests(){
        Result controllerResults1 = JUnitCore.runClasses(RESTCompanyControllerTest.class);
        Result controllerResults2 = JUnitCore.runClasses(RESTFleetControllerTest.class);
        Result controllerResults3 = JUnitCore.runClasses(RESTRoleControllerTest.class);
        Result controllerResults4 = JUnitCore.runClasses(RESTUserControllerTest.class);
        Result controllerResults5 = JUnitCore.runClasses(UUIDUtilTest.class);
        Result controllerResults6 = JUnitCore.runClasses(TestPagination.class);
        Result controllerResults7 = JUnitCore.runClasses(RESTFunctionControllerTest.class);
        Result controllerResults8 = JUnitCore.runClasses(RESTLogEntryControllerTest.class);
        Result controllerResults9 = JUnitCore.runClasses(RESTPermissionControllerTest.class);
        Result controllerResults10 = JUnitCore.runClasses(RESTUserMeControllerTest.class);
        Result controllerResults11 = JUnitCore.runClasses(RESTVehicleControllerTest.class);
        Result controllerResults12 = JUnitCore.runClasses(RESTVehicleTypeControllerTest.class);
        Result controllerResults13 = JUnitCore.runClasses(RESTContractControllerTest.class);
        Result controllerResults14 = JUnitCore.runClasses(RESTSpecialConditionControllerTest.class);
        Result controllerResults15 = JUnitCore.runClasses(RESTSuretyControllerTest.class);
        Result controllerResults16 = JUnitCore.runClasses(RESTVehicleInsuranceControllerTest.class);
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
        System.out.printf("%-25s %s\n", "RESTUserController:", String.valueOf(controllerResults4.wasSuccessful()));
        for (Failure failure : controllerResults4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UUIDUtil:", String.valueOf(controllerResults5.wasSuccessful()));
        for (Failure failure : controllerResults5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "Pagination:", String.valueOf(controllerResults6.wasSuccessful()));
        for (Failure failure : controllerResults6.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTFunctionController:", String.valueOf(controllerResults7.wasSuccessful()));
        for (Failure failure : controllerResults7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTLogEntryController:", String.valueOf(controllerResults8.wasSuccessful()));
        for (Failure failure : controllerResults8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTPermissionController:", String.valueOf(controllerResults9.wasSuccessful()));
        for (Failure failure : controllerResults9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTUserMeController:", String.valueOf(controllerResults10.wasSuccessful()));
        for (Failure failure : controllerResults10.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTvehicleController:", String.valueOf(controllerResults11.wasSuccessful()));
        for (Failure failure : controllerResults11.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTVehicleTypeController:", String.valueOf(controllerResults12.wasSuccessful()));
        for (Failure failure : controllerResults12.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTContractController:", String.valueOf(controllerResults13.wasSuccessful()));
        for (Failure failure : controllerResults13.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTSpecialConditionController:", String.valueOf(controllerResults14.wasSuccessful()));
        for (Failure failure : controllerResults14.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTSuretyController:", String.valueOf(controllerResults15.wasSuccessful()));
        for (Failure failure : controllerResults15.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTVehicleInsuranceController:", String.valueOf(controllerResults16.wasSuccessful()));
        for (Failure failure : controllerResults16.getFailures()) {
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
        Result daoResult13 = JUnitCore.runClasses(ProductionContractDAOTest.class);
        Result daoResult14 = JUnitCore.runClasses(ProductionFlatSuretyDAOTest.class);
        Result daoResult15 = JUnitCore.runClasses(ProductionInsuranceCompanyDAOTest.class);
        Result daoResult16 = JUnitCore.runClasses(ProductionInvoiceDAOTest.class);
        Result daoResult17 = JUnitCore.runClasses(ProductionLogEntryDAOTest.class);
        Result daoResult18 = JUnitCore.runClasses(ProductionNonFlatSuretyDAOTest.class);
        Result daoResult19 = JUnitCore.runClasses(ProductionSpecialConditionDAOTest.class);
        Result daoResult20 = JUnitCore.runClasses(ProductionSuretyDAOTest.class);
        Result daoResult21 = JUnitCore.runClasses(ProductionVehicleInsuranceDAOTest.class);
        Result controllerResults1 = JUnitCore.runClasses(RESTCompanyControllerTest.class);
        Result controllerResults2 = JUnitCore.runClasses(RESTFleetControllerTest.class);
        Result controllerResults3 = JUnitCore.runClasses(RESTRoleControllerTest.class);
        Result controllerResults4 = JUnitCore.runClasses(RESTUserControllerTest.class);
        Result controllerResults5 = JUnitCore.runClasses(UUIDUtilTest.class);
        Result controllerResults6 = JUnitCore.runClasses(TestPagination.class);
        Result controllerResults7 = JUnitCore.runClasses(RESTFunctionControllerTest.class);
        Result controllerResults8 = JUnitCore.runClasses(RESTLogEntryControllerTest.class);
        Result controllerResults9 = JUnitCore.runClasses(RESTPermissionControllerTest.class);
        Result controllerResults10 = JUnitCore.runClasses(RESTUserMeControllerTest.class);
        Result controllerResults11 = JUnitCore.runClasses(RESTVehicleControllerTest.class);
        Result controllerResults12 = JUnitCore.runClasses(RESTVehicleTypeControllerTest.class);
        Result controllerResults13 = JUnitCore.runClasses(RESTContractControllerTest.class);
        Result controllerResults14 = JUnitCore.runClasses(RESTSpecialConditionControllerTest.class);
        Result controllerResults15 = JUnitCore.runClasses(RESTSuretyControllerTest.class);
        Result controllerResults16 = JUnitCore.runClasses(RESTVehicleInsuranceControllerTest.class);
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
        System.out.printf("%-25s %s\n", "ContractDAO:", String.valueOf(daoResult13.wasSuccessful()));
        for (Failure failure : daoResult13.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "FlatSuretyDAO:", String.valueOf(daoResult14.wasSuccessful()));
        for (Failure failure : daoResult14.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "InsuranceCompanyDAO:", String.valueOf(daoResult15.wasSuccessful()));
        for (Failure failure : daoResult15.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "InvoiceDAO:", String.valueOf(daoResult16.wasSuccessful()));
        for (Failure failure : daoResult16.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "LogEntryDAO:", String.valueOf(daoResult17.wasSuccessful()));
        for (Failure failure : daoResult17.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "NonFlatSuretyDAO:", String.valueOf(daoResult18.wasSuccessful()));
        for (Failure failure : daoResult18.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "SpecialConditionDAO:", String.valueOf(daoResult19.wasSuccessful()));
        for (Failure failure : daoResult19.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "SuretyDAO:", String.valueOf(daoResult20.wasSuccessful()));
        for (Failure failure : daoResult20.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "VehicleInsuranceDAO:", String.valueOf(daoResult21.wasSuccessful()));
        for (Failure failure : daoResult21.getFailures()) {
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
        System.out.printf("%-25s %s\n", "RESTUserController:", String.valueOf(controllerResults4.wasSuccessful()));
        for (Failure failure : controllerResults4.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "UUIDUtil:", String.valueOf(controllerResults5.wasSuccessful()));
        for (Failure failure : controllerResults5.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "Pagination:", String.valueOf(controllerResults6.wasSuccessful()));
        for (Failure failure : controllerResults6.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTFunctionController:", String.valueOf(controllerResults7.wasSuccessful()));
        for (Failure failure : controllerResults7.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTLogEntryController:", String.valueOf(controllerResults8.wasSuccessful()));
        for (Failure failure : controllerResults8.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTPermissionController:", String.valueOf(controllerResults9.wasSuccessful()));
        for (Failure failure : controllerResults9.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTUserMeController:", String.valueOf(controllerResults10.wasSuccessful()));
        for (Failure failure : controllerResults10.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTvehicleController:", String.valueOf(controllerResults11.wasSuccessful()));
        for (Failure failure : controllerResults11.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTVehicleTypeController:", String.valueOf(controllerResults12.wasSuccessful()));
        for (Failure failure : controllerResults12.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTContractController:", String.valueOf(controllerResults13.wasSuccessful()));
        for (Failure failure : controllerResults13.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTSpecialConditionController:", String.valueOf(controllerResults14.wasSuccessful()));
        for (Failure failure : controllerResults14.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTSuretyController:", String.valueOf(controllerResults15.wasSuccessful()));
        for (Failure failure : controllerResults15.getFailures()) {
            System.out.println(failure.getMessage());
        }
        System.out.printf("%-25s %s\n", "RESTVehicleInsuranceController:", String.valueOf(controllerResults16.wasSuccessful()));
        for (Failure failure : controllerResults16.getFailures()) {
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
