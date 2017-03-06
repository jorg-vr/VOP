package dao;

import model.Subfleet;
import model.Vehicle;
import model.VehicleType;

import java.util.Collection;

public interface SubfleetDAO extends DAO<Subfleet> {

    Subfleet create(VehicleType type);

    Subfleet create(VehicleType type, Collection<Vehicle> vehicles);
}
