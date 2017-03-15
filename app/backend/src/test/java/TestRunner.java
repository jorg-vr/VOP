import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


public class TestRunner {
    public static void main(String[] args) {
        //Change to 'false' for local testing. Change to true before creating a pull request.
        //ProductionProvider.initializeProvider(true);
        Result modelResults = JUnitCore.runClasses(ModelsTestSuite.class);
        Result daoResults = JUnitCore.runClasses(DAOsTestSuite.class);
        //Result controllerResults = JUnitCore.runClasses(ControllersTestSuite.class);

        System.out.println("Models unit tests:");
        for (Failure failure : modelResults.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(modelResults.wasSuccessful());

        System.out.println("DAOs unit tests:");
        for (Failure failure : daoResults.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(daoResults.wasSuccessful());

        /*System.out.println("Controllers unit tests:");
        for (Failure failure : controllerResults.getFailures()){
            System.out.println(failure.toString());
        }

        System.out.println(controllerResults.wasSuccessful());*/
    }
}
