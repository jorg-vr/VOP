package dao.interfaces;

import model.fleet.Vehicle;
import model.identity.Customer;
import model.fleet.Fleet;

import java.util.Collection;
import java.util.UUID;

public interface FleetDAO extends DAO<Fleet> {

    public Fleet create(Customer customer, Collection<Vehicle> vehicles) throws DataAccessException;

    public Fleet update(UUID id, Customer customer, Collection<Vehicle> vehicles) throws DataAccessException;

    Filter<Fleet> byOwner(Customer customer);
}
