package model.fleet;

import model.history.EditableObject;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Fleet implements EditableObject, java.io.Serializable {

    private UUID uuid;
    private String name;
    private Customer owner;

    private Collection<Vehicle> vehicles;

    public Fleet() {
    }

    public Fleet(UUID uuid, Collection<Vehicle> vehicles) {
        this.uuid= uuid;

        this.vehicles = vehicles;
    }




    /**
     * Adds the Vehicle to the Fleet.
     * If the Vehicle is already present in the Fleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Vehicle was added
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicles == null) {
            vehicles = new ArrayList<Vehicle>();
        } else if (vehicle == null || vehicles.contains(vehicle)) {
            return false;
        }
        return vehicles.add(vehicle);
    }

    /**
     * Removes the Vehicle from the Fleet.
     * If the Vehicle is not present in the Fleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Vehicle was removed
     */
    public boolean removeVehicle(Vehicle vehicle) {
        if(vehicles == null){
            return false;
        }
        if (vehicle == null || !vehicles.contains(vehicle)) {
            return false;
        }
        return vehicles.remove(vehicle);
    }

    /**
     * @return The amount of vehicles in this fleet
     */
    public int size() {
        return vehicles.size();
    }

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

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

        return uuid == ((Fleet) o).getUuid();

    }

    @Override
    public EditableObject copy() {
        Collection<Vehicle> newList = new ArrayList<Vehicle>();
        for (Vehicle v : vehicles) {
            newList.add((Vehicle) v.copy());
        }
        return new Fleet(uuid, newList);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

}
