package dao;

import model.Customer;
import model.Fleet;
import model.Subfleet;

import java.util.Collection;

public interface FleetDAO {

    Fleet create(Collection<Subfleet> subfleets, Customer owner);

    Fleet create(Customer owner);

    Fleet get(int id);

    boolean remove(int id);

    boolean update(Fleet fleet);

    Collection<Fleet> listFiltered(Filter... filters);
}
