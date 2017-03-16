package controller;

import dao.database.ProductionProvider;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import main.BackendApplication;
import model.identity.Address;
import model.identity.Customer;

import java.util.UUID;

/**
 * Created by jorg on 3/9/17.
 */
public class CustomerController extends AbstractController<Customer>{

    private CustomerDAO dao;
    public CustomerController() {
        super(BackendApplication.getProvider().getCustomerDAO());
        dao = BackendApplication.getProvider().getCustomerDAO();
    }

    public Customer create(Address address, String phoneNumber, String name, String btwNumber) throws DataAccessException {
        return dao.create(name, address, phoneNumber, btwNumber);
    }

    public Customer update(UUID id, Address address, String phoneNumber, String name, String btwNumber) throws DataAccessException {
        return dao.update(id, name, address, phoneNumber, btwNumber);
    }
}
