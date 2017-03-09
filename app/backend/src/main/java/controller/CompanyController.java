package controller;

import dao.database.ProductionProvider;
import dao.interfaces.CompanyDAO;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/9/17.
 */
public class CompanyController {
    private CustomerDAO customerDAO;
    private CompanyDAO<InsuranceCompany> insuranceCompanyDAO;//not for milestone1

    public CompanyController() {
        customerDAO= ProductionProvider.getInstance().getCustomerDAO();
    }

    public Customer create(Customer customer) throws DataAccessException {
        
    }

    public Customer get(String id) throws DataAccessException {
        return null;
    }

    public void update(String id, Customer customer) throws DataAccessException {

    }

    public void remove(String id) throws DataAccessException {

    }


    public Collection<Customer> listFiltered(Filter... filters) throws DataAccessException {
        return null;
    }

}
