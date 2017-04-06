package dao.interfaces;

import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;

import java.util.Collection;
import java.util.UUID;

/**
 * DAO for the bean Customer which is a Company
 * Created by sam on 3/7/17.
 */
public interface CustomerDAO extends CompanyDAO<Customer> {


    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all customers (Can only be one) who the given fleet.
     *
     * @param fleet The fleet to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Customer> containsFleet(Fleet fleet);
}
