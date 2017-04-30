package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.ContractController;
import controller.insurance.SuretyController;
import controller.insurance.VehicleInsuranceController;
import dao.interfaces.*;
import main.BackendApplication;
import model.account.Function;
import model.identity.Company;
import model.insurance.Surety;
import spring.exceptions.InvalidInputException;

import java.util.UUID;

/**
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
    public ControllerManager(UUID userId, UUID functionId) throws UnAuthorizedException, InvalidInputException {
        daoManager = BackendApplication.getProvider().getDaoManager();
        try {
            FunctionDAO functionDAO = daoManager.getFunctionDAO();
            function = functionDAO.get(functionId);
            if (!function.getUser().getUuid().equals(userId)) {
                throw new InvalidInputException();
            }
        } catch (DataAccessException e) {
            throw new InvalidInputException();
        }
    }

    public CompanyController getCompanyController() {
        return new CompanyController(function, daoManager);
    }

    public CustomerController getCustomerController() {
        return null;
    }

    public FleetController getFleetController() {
        return null;
    }

    public FunctionController getFunctionController() {
        return null;
    }

    public InsuranceCompanyController getInsuranceCompanyController() {
        return null;
    }

    public InvoiceController getInvoiceController() {
        return null;
    }

    public RoleController getRoleController() {
        return null;
    }

    public UserController getUserController() {
        return null;
    }

    public VehicleController getVehicleController() {
        return null;
    }

    public VehicleTypeController getVehicleTypeController() {
        return null;
    }

    public ContractController getContractController() {
        return null;
    }

    public SuretyController getSuretyController() {
        return null;
    }

    public VehicleInsuranceController getVehicleInsuranceController() {
        return null;
    }

    public void close() {
        daoManager.close();
    }
}
