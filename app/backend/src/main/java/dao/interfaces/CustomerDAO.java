package dao.interfaces;

import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;

import java.util.Collection;
import java.util.UUID;


public interface CustomerDAO extends CompanyDAO<Customer> {

    Customer create(String name, Address address, String phonenumber, String btwNumber) throws DataAccessException;

    Customer update(UUID id,String name, Address address, String phonenumber, String btwNumber) throws DataAccessException;


    Filter<Customer> containsFleet(Fleet fleet);
}
