package dao.interfaces;

import model.fleet.Vehicle;
import model.identity.Customer;
import model.fleet.Fleet;

import java.util.Collection;
import java.util.UUID;

/**
 * DAO for the bean Fleet
 * Created by sam on 3/7/17.
 */
public interface FleetDAO extends DAO<Fleet> {

    Filter<Fleet> byOwner(Customer customer);
}
