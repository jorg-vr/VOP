package controller;

import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Resource;
import model.identity.Customer;

/**
 * For more information of what this class does, see AbstractController
 */
public class CustomerController extends AbstractController<Customer>{

    public CustomerController(Function function, DAOManager manager) {
        super(manager, manager.getCustomerDAO(), Resource.COMPANY,function);
    }


    @Override
    public boolean isOwner(Customer customer, Function function) {
        return function.getCompany().equals(customer);
    }
}
