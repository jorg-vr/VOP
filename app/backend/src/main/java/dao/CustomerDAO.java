package dao;

import model.fleet.Fleet;
import model.identity.Customer;


public interface CustomerDAO extends CompanyDAO<Customer> {

    Filter<Customer> containsFleet(Fleet fleet);

}
