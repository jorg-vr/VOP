package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.SpecialConditionController;
import controller.insurance.SuretyController;
import controller.insurance.VehicleInsuranceController;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.FunctionDAO;
import main.BackendApplication;
import model.account.*;
import spring.exceptions.InvalidInputException;

import java.util.*;


/**
 * This class acts a provider for all the controllers. Every controller except AuthController should be retrieved from this class.
 *
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
        try {
            FunctionDAO functionDAO = daoManager.getFunctionDAO();
            function = functionDAO.get(functionId);
            if (!function.getUser().getUuid().equals(userId)) {
                throw new InvalidInputException();
            }
        } catch (ObjectNotFoundException e) {
            throw new InvalidInputException("User or function does not exist");
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
        return new UserController(function, daoManager,this);
    }

    public VehicleController getVehicleController() {
        return new VehicleController(function, daoManager,this);
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

    public SpecialConditionController getSpecialConditionController() {
        return new SpecialConditionController(function, daoManager);
    }

    public LogEntryController getLogEntryController() {
        return new LogEntryController(function, daoManager);
    }

    public void close() {
        daoManager.close();
    }
}
