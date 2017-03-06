package dao;

import model.identity.Customer;
import model.fleet.Fleet;
import model.fleet.Subfleet;

import java.util.Collection;

public interface FleetDAO extends DAO<Fleet> {

    Fleet create(Collection<Subfleet> subfleets, Customer owner);

    Fleet create(Customer owner);
}
