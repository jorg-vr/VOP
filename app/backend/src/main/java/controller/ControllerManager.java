package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.SuretyController;
import controller.insurance.VehicleInsuranceController;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.*;
import main.BackendApplication;
import model.account.*;
import spring.exceptions.InvalidInputException;

import java.util.*;

import static main.BackendApplication.DISABLE_AUTH;

/**
 * This class acts a provider for all the controllers. Every controller except AuthController should be retrieved from this class.
 * @author Billie Devolder
 */
public class ControllerManager implements AutoCloseable {

    private DAOManager daoManager;
    private Function function;

    /**
     * @param userId     id of the user that wants to use the controller
     * @param functionId id of the function that the user wants to use
     * @throws InvalidInputException there is no function/user with that id or the user does not have that function
     */
    public ControllerManager(UUID userId, UUID functionId) throws UnAuthorizedException, DataAccessException, InvalidInputException {
        daoManager = BackendApplication.getProvider().getDaoManager();
        if (DISABLE_AUTH) {
            function = new Function();
            Role role = new Role();
            Map<Resource, Permission> rights = new HashMap<>();
            for (Resource resource : Resource.values()) {
                Permission permission = new Permission();
                Set<Action> actionSet = new HashSet<>();
                Collections.addAll(actionSet, Action.values());
                permission.setActions(actionSet);
                rights.put(resource, permission);
            }
            role.setRights(rights);
            function.setRole(role);
            return;
        }
        try {
            FunctionDAO functionDAO = daoManager.getFunctionDAO();
            function = functionDAO.get(functionId);
            if (!function.getUser().getUuid().equals(userId)) {
                throw new InvalidInputException();
            }

//            if (1 == 2) throw new DataAccessException();
//            function = new Function();
//            Role role = new Role();
//            Map<Resource, Permission> rights = new HashMap<>();
//            for (Resource resource : Resource.values()) {
//                Permission permission = new Permission();
//                Set<Action> actionSet = new HashSet<>();
//                Collections.addAll(actionSet, Action.values());
//                permission.setActions(actionSet);
//                rights.put(resource, permission);
//            }
//            role.setRights(rights);
//            function.setRole(role);
        } catch (ObjectNotFoundException e) {

        } catch (DataAccessException e) {

            throw new InvalidInputException("User/Function does not exist or user has no function with that id");
        }
    }

    public CompanyController getCompanyController() {
        return new CompanyController(function, daoManager);
    }

    public CustomerController getCustomerController() {
        return new CustomerController(function, daoManager);

    }

    public FleetController getFleetController() {
        return new FleetController(function, daoManager);
    }

    public FunctionController getFunctionController() {
        return new FunctionController(function, daoManager);
    }

    public InsuranceCompanyController getInsuranceCompanyController() {
        return new InsuranceCompanyController(function, daoManager);
    }

    public InvoiceController getInvoiceController() {
        return new InvoiceController(function, daoManager);
    }

    public RoleController getRoleController() {
        return new RoleController(function, daoManager);
    }

    public UserController getUserController() {
        return new UserController(function, daoManager);
    }

    public VehicleController getVehicleController() {
        return new VehicleController(function, daoManager);
    }

    public VehicleTypeController getVehicleTypeController() {
        return new VehicleTypeController(function, daoManager);
    }

    public ContractController getContractController() {
        return new ContractController(function, daoManager);
    }

    public SuretyController getSuretyController() {
        return new SuretyController(function, daoManager);
    }

    public VehicleInsuranceController getVehicleInsuranceController() {
        return new VehicleInsuranceController(function, daoManager);
    }

    public void close() {
        daoManager.close();
    }
}
