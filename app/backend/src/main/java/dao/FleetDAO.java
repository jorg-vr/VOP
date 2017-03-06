package src.main.java.dao;

import src.main.java.model.identity.Customer;
import src.main.java.model.fleet.Fleet;
import src.main.java.model.fleet.Subfleet;

import java.util.Collection;

public interface FleetDAO extends DAO<Fleet> {

    Fleet create(Collection<Subfleet> subfleets, Customer owner);

    Fleet create(Customer owner);
}
