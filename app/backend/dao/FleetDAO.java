package dao;

import model.Customer;
import model.Fleet;
import model.Subfleet;

import java.util.Collection;

public interface FleetDAO extends DAO<Fleet> {

    Fleet create(Collection<Subfleet> subfleets, Customer owner);

    Fleet create(Customer owner);
}
