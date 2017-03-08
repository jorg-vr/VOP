package model.fleet;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Subfleet implements java.io.Serializable {

    private UUID uuid;

    private VehicleType type;

    private Collection<Vehicle> vehicles;

    public Subfleet() {
    }

    public Subfleet(UUID uuid, VehicleType type, Collection<Vehicle> vehicles) {
        this.uuid = uuid;
        this.type = type;
        this.vehicles = vehicles;
    }

    public Subfleet(UUID uuid, VehicleType type) {
        this.uuid = uuid;
        this.type = type;
        this.vehicles = new HashSet<>();
    }

    /**
     * Adds the vehicle to the Subfleet.
     * If the vehicle is already present in the Subfleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Vehicle was added
     */
    public boolean add(Vehicle vehicle) {
        if (vehicles.contains(vehicle)) {
            return false;
        }
        vehicles.add(vehicle);
        return true;
    }

    /**
     * Removes the Vehicle from the Subfleet.
     * If the Vehicle is not present in the Subfleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Vehicle was removed
     */
    public boolean remove(Vehicle vehicle) {
        if (!vehicles.contains(vehicle)) {
            return false;
        }
        vehicles.remove(vehicle);
        return true;
    }

    /**
     * @return The amount of vehicles in this subfleet
     */
    public int size() {
        return vehicles.size();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    //public Collection<Vehicle> getVehicles() {
    //    return new HashSet<>(vehicles);
    //}

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subfleet subfleet = (Subfleet) o;

        return uuid == subfleet.uuid;

    }
}
