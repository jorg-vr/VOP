import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import spring.TestPagination;
import spring.controller.*;


public class TestRunner {
    public static void main(String[] args) {
        Result modelResults = null;
        Result daoResults = null;
        Result controllerResults1 = null;
        Result controllerResults2 = null;
        Result controllerResults3 = null;
        Result controllerResults4 = null;
        Result controllerResults5 = null;
        Result controllerResults6 = null;
        //ProductionProvider.initializeProvider("unittest");

        if (args.length == 0 || args[0].equals("model")) {
            modelResults = JUnitCore.runClasses(ModelsTestSuite.class);
        }
        if (args.length == 0 || args[0].equals("dao")) {
            daoResults = JUnitCore.runClasses(DAOsTestSuite.class);
        }
        if (args.length == 0 || args[0].equals("controller")) {
            controllerResults1 = JUnitCore.runClasses(RESTCompanyControllerTest.class);
            controllerResults2 = JUnitCore.runClasses(RESTFleetControllerTest.class);
            controllerResults3 = JUnitCore.runClasses(RESTRoleControllerTest.class);
            controllerResults4 = JUnitCore.runClasses(RESTUserControllerTest.class);
            controllerResults5 = JUnitCore.runClasses(UUIDUtilTest.class);
            controllerResults6 = JUnitCore.runClasses(TestPagination.class);
        }

        if (args.length == 0 || args[0].equals("model")) {
            System.out.println("Models unit tests:");
            for (Failure failure : modelResults.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(modelResults.wasSuccessful());
        }
        if (args.length == 0 || args[0].equals("dao")) {
            System.out.println("DAOs integration tests:");
            for (Failure failure : daoResults.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(daoResults.wasSuccessful());
        }
        if (args.length == 0 || args[0].equals("controller")) {
            System.out.println("RESTCompanyController integration tests:");
            for (Failure failure : controllerResults1.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults1.wasSuccessful());

            System.out.println("RESTFleetController integration tests:");
            for (Failure failure : controllerResults2.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults2.wasSuccessful());

            System.out.println("RESTRoleController integration tests:");
            for (Failure failure : controllerResults3.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults3.wasSuccessful());

            System.out.println("RESTUserController integration tests:");
            for (Failure failure : controllerResults4.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults4.wasSuccessful());

            System.out.println("UUIDUtil integration tests:");
            for (Failure failure : controllerResults5.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults5.wasSuccessful());

            System.out.println("Pagination integration tests:");
            for (Failure failure : controllerResults6.getFailures()) {
                System.out.println(failure.toString());
            }
            System.out.println(controllerResults6.wasSuccessful());
        }


    }
}
