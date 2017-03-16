import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
    public static void main(String[] args) {
        Result modelResults = null;
        Result daoResults = null;
        Result controllerResults = null;

        if (args.length == 0 || args[0].equals("model")) {
            modelResults = JUnitCore.runClasses(ModelsTestSuite.class);
        }
        if (args.length == 0 || args[0].equals("dao")) {
            daoResults = JUnitCore.runClasses(DAOsTestSuite.class);
        }
        if (args.length == 0 || args[0].equals("controller")) {
            controllerResults = JUnitCore.runClasses(ControllersTestSuite.class);
        }

        if (args.length == 0 || args[0].equals("model")) {
            System.out.println("Models unit tests:");
            for (Failure failure : modelResults.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(modelResults.wasSuccessful());
        }
        if (args.length == 0 || args[0].equals("dao")) {
            System.out.println("DAOs unit tests:");
            for (Failure failure : daoResults.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(daoResults.wasSuccessful());
        }
        if (args.length == 0 || args[0].equals("controller")) {
            System.out.println("Controllers unit tests:");
            for (Failure failure : controllerResults.getFailures()) {
                System.out.println(failure.toString());
            }

            System.out.println(controllerResults.wasSuccessful());
        }


    }
}
