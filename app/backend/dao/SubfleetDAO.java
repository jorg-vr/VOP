package dao;

import model.Subfleet;
import model.Vehicle;
import model.VehicleType;

import java.util.Collection;

public interface SubfleetDAO {

    Subfleet create(VehicleType type);

    Subfleet create(VehicleType type, Collection<Vehicle> vehicles);

    boolean get(int id);

    boolean remove(int id);

    boolean update(Subfleet subfleet);

    Collection<Subfleet> listFiltered(Filter... filters);
}
