package dao;

import model.fleet.Subfleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.util.Collection;

public interface SubfleetDAO extends DAO<Subfleet> {

    Subfleet create(VehicleType type);

    Subfleet create(VehicleType type, Collection<Vehicle> vehicles);
}
