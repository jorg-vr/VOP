package controller;

import dao.database.ProductionProvider;
import dao.interfaces.CompanyDAO;
import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.test.TestCustomerDAO;
import main.BackendApplication;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import model.identity.InsuranceCompany;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/9/17.
 */
public class CustomerController extends AbstractController<Customer>{

    public CustomerController() {
        super(BackendApplication.PROVIDER.getCustomerDAO());
    }

    public Customer create(Address address, String phoneNumber, String name, String btwNumber) throws DataAccessException {
        Customer customer=new Customer();
        setCustomer(customer,address,phoneNumber,name, btwNumber);
        return getDao().create(customer);
    }

    public void update(UUID id, Address address, String phoneNumber, String name, String btwNumber) throws DataAccessException {
        Customer customer=getDao().get(id);
        setCustomer(customer,address,phoneNumber,name, btwNumber);
        getDao().update(customer);
    }

    private void setCustomer(Customer customer, Address address, String phoneNumber, String name, String btwNumber){
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer.setName(name);
        customer.setBtwNumber(btwNumber);
        //TODO add other setValues
    }
}
