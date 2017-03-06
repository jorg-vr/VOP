package src.main.java.dao;

import src.main.java.model.fleet.Subfleet;
import src.main.java.model.fleet.Vehicle;
import src.main.java.model.fleet.VehicleType;

import java.util.Collection;

public interface SubfleetDAO extends DAO<Subfleet> {

    Subfleet create(VehicleType type);

    Subfleet create(VehicleType type, Collection<Vehicle> vehicles);
}
