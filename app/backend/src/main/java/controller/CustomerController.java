package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Customer;

/**
 * For more information of what this class does, see AbstractController
 */
public class CustomerController extends AbstractController<Customer>{

    public CustomerController(Function function, DAOManager manager) {
        super(manager.getCustomerDAO(), Resource.COMPANY,function);
    }


    @Override
    public Customer update(Customer customer) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        Customer old = get(customer.getUuid());
        customer.setCommissions(old.getCommissions());
        return super.update(customer);
    }

    @Override
    public boolean isOwner(Customer customer, Function function) {
        return function.getCompany().equals(customer);
    }
}
