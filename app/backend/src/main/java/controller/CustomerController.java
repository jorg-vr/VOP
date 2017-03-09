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
public class CustomerController extends AbstractController<Customer>{

    public CustomerController() {
        super(ProductionProvider.getInstance().getCustomerDAO());
    }

    public Customer create(Address address, String phoneNumber, String name, String btwNumber) throws DataAccessException {
        Customer customer=new Customer();
        customer.setAddress(address);
        customer.setPhoneNumber(phoneNumber);
        customer.setName(name);
        customer.setBtwNumber(btwNumber);
        //TODO add other setValues
        return getDao().create(customer);
    }
    public void update(Customer customer) {

    }
}
