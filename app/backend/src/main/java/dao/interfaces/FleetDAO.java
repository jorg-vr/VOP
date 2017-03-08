package dao.interfaces;

import model.identity.Customer;
import model.fleet.Fleet;

public interface FleetDAO extends DAO<Fleet> {
    Filter<Fleet> byOwner(Customer customer);
}
