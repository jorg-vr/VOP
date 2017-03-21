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
     * Creates a new Customer
     * @param name the name of the customer
     * @param address the address of the customer
     * @param phonenumber the phonenumber of the customer
     * @param btwNumber the vat number of the customer
     * @return A new customer with a unique id
     * @throws DataAccessException Thrown when the object can't be created or invalid arguments are given
     */
    Customer create(String name, Address address, String phonenumber, String btwNumber) throws DataAccessException;

    /**
     * Updates an existing Customer
     * @param id the id of the customer
     * @param name the name of the customer
     * @param address the address of the customer
     * @param phonenumber the phonenumber of the customer
     * @param btwNumber the vat number of the customer
     * @return The updated customer
     * @throws DataAccessException Thrown when the object can't be created or invalid arguments are given
     */
    Customer update(UUID id, String name, Address address, String phonenumber, String btwNumber) throws DataAccessException;

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all customers (Can only be one) who the given fleet.
     *
     * @param fleet The fleet to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Customer> containsFleet(Fleet fleet);
}
