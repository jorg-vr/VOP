package controller;

import dao.database.ProductionProvider;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.identity.Address;
import model.identity.Customer;

import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class CustomerController extends AbstractController<Customer>{

    public CustomerController(Function function) {
        super(BackendApplication.getProvider().getCustomerDAO(), Resource.COMPANY,function);
    }


    @Override
    public boolean isOwner(Customer customer, Function function) {
        return function.getCompany().equals(customer);
    }
}
