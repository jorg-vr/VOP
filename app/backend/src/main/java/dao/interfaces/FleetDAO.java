package dao.interfaces;

import model.fleet.Vehicle;
import model.identity.Customer;
import model.fleet.Fleet;

import java.util.Collection;
import java.util.UUID;

public interface FleetDAO extends DAO<Fleet> {

    /**
     * @param name name of the fleet, can not be null
     * @param customer owner of the fleet
     * @return the Fleet object with valid fields
     * @throws DataAccessException
     */
    Fleet create(String name, Customer customer) throws DataAccessException;

    /**
     * @param id id of the fleet that should be updated
     * @param name name of the fleet, can not be null
     * @param customer the customer that this fleet belongs to
     * @return the Fleet object with updated values
     * @throws DataAccessException name has been taken or no Fleet associated with that id
     */
    Fleet update(UUID id, String name, Customer customer) throws DataAccessException;

}
