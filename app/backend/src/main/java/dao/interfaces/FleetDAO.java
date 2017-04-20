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

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Fleets the given owner has.
     * @param customer the customer to use in the filter
     * @return a usable filter for ListFiltered
     */
    Filter<Fleet> byOwner(Customer customer);
}
