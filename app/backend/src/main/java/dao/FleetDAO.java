package dao;

import model.identity.Customer;
import model.fleet.Fleet;
import model.fleet.Subfleet;

import java.util.Collection;

public interface FleetDAO extends DAO<Fleet> {
    Filter<Fleet> byOwner(Customer customer);
}
