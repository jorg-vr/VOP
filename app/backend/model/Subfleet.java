package model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Subfleet {

    private int id;

    private VehicleType type;

    private Collection<Vehicle> vehicles;

    public Subfleet(int id, VehicleType type, Collection<Vehicle> vehicles) {
        this.id = id;
        this.type = type;
        this.vehicles = vehicles;
    }

    public Subfleet(int id, VehicleType type) {
        this.id = id;
        this.type = type;
        this.vehicles = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public VehicleType getType() {
        return type;
    }

    /**
     * Adds the vehicle to the Subfleet.
     * If the vehicle is already present in the Subfleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     *                  so we can't shorten this function to 1 line.
     * @return true if the Vehicle was added
     */
    public boolean add(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    /**
     * Removes the Vehicle from the Subfleet.
     * If the Vehicle is not present in the Subfleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     *                  so we can't shorten this function to 1 line.
     * @return true if the Vehicle was removed
     */
    public boolean remove(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            return false;
        }
        vehicles.remove(vehicle);
        return true;
    }

    public Collection<Vehicle> getVehicles() {
        return new HashSet<>(vehicles);
    }

    /**
     * @return The amount of vehicles in this subfleet
     */
    public int size() {
        return vehicles.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subfleet subfleet = (Subfleet) o;

        return id == subfleet.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
