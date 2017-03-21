package dao.test;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.identity.Customer;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/13/17.
 */
public class TESTFleetDAO  implements FleetDAO{
    @Override
    public Fleet create(String name, Customer customer) throws DataAccessException {
        return null;
    }

    @Override
    public Fleet update(UUID id, String name, Customer customer) throws DataAccessException {
        return null;
    }

    @Override
    public Fleet get(UUID id) throws DataAccessException {
        return null;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {

    }

    @Override
    public Collection<Fleet> listFiltered(Filter<Fleet>... filters) throws DataAccessException {
        return null;
    }


}
