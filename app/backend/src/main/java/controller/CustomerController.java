package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.CommissionContainerController;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.identity.Customer;

/**
 * For more information of what this class does, see AbstractController
 */
public class CustomerController extends CommissionContainerController<Customer> {

    public CustomerController(Function function, DAOManager manager) {
        super(manager, manager.getCustomerDAO(), Resource.COMPANY,function);
    }

    @Override
    public boolean isOwner(Customer customer, Function function) {
        return function.getCompany().equals(customer);
    }
}
