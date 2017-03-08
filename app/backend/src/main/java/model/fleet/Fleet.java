package model.fleet;

import model.history.EditableObject;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Fleet implements EditableObject, java.io.Serializable {

    private UUID uuid;
    private Customer owner;
    private Collection<Vehicle> vehicles;

    public Fleet() {
    }

    public Fleet(UUID uuid, Customer owner, Collection<Vehicle> vehicles) {
        this.uuid = uuid;
        this.owner = owner;
        this.vehicles = vehicles;
    }

    public Fleet(UUID uuid, Customer owner) {
        this.uuid = uuid;
        this.owner = owner;
        this.vehicles = new HashSet<Vehicle>();
    }

    /**
     * Adds the Vehicle to the Fleet.
     * If the Vehicle is already present in the Fleet, nothing will happen.
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
     * Removes the Vehicle from the Fleet.
     * If the Vehicle is not present in the Fleet, nothing will happen.
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
     * @return The amount of vehicles in this fleet
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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public Collection<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        return uuid == ((Fleet)o).getUuid();

    }

    @Override
    public EditableObject copy() {
        Collection<Vehicle> newList =  new ArrayList<Vehicle>();
        for(Vehicle v : vehicles){
            newList.add((Vehicle)v.copy());
        }
        return new Fleet(uuid,owner, newList);
    }
}
