package dao.database;

import dao.interfaces.CustomerDAO;
import dao.interfaces.Filter;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import org.hibernate.Session;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionCustomerDAO extends ProductionDAO<Customer> implements CustomerDAO {


    public ProductionCustomerDAO(Session session) {
        super(session, Customer.class);
    }


    @Override
    public Filter<Customer> containsFleet(Fleet fleet) {
        return null;
    }

    @Override
    public Filter<Customer> byName(String name) {
        return filterEqual("name", name);
    }

    @Override
    public Filter<Customer> containsName(String name) {
        return filterContains("name", name);
    }

    @Override
    public Filter<Customer> byVatNumber(String vatNumber) {
        return filterContains("btwNumber",vatNumber);
    }

    @Override
    public Filter<Customer> byPhoneNumber(String phoneNumber) {
        return filterContains("phoneNumber",phoneNumber);
    }

    @Override
    public Filter<Customer> byAddress(Address address) {
        return filterEqual("address",address);
    }

    @Override
    public Filter<Customer> byBankAccountNummber(String bankAccountNumber) {
        return filterContains("bankAccountNumber",bankAccountNumber);
    }

    @Override
    public Filter<Customer> byEmail(String email) {
        return filterContains("email",email);
    }

}
