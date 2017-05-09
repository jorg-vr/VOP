package model.fleet;

import model.history.EditableObject;
import model.history.LogResource;
import model.identity.Address;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Fleet class that contains all Vehicles a Customer owns
 * Subfleets are represented by VehicleTypes in the vehicle collection
 */
public class Fleet implements EditableObject, java.io.Serializable {

    /**
     * The unique id of this object
     */
    private UUID uuid;

    /**
     * The name of the fleet
     */
    private String name;

    /**
     * Owner of the fleet
     */
    private Customer owner;

    /**
     * Address of the fleet, a company can have more than one fleet, but not all fleets have to be on the same address
     * (e.g. in case of subsidiaries)
     */
    private Address address;

    /**
     * The vehicles in the fleet
     */
    private Collection<Vehicle> vehicles;

    /**
     * Default constructor
     */
    public Fleet() {
    }

    public Fleet(UUID uuid) {
        this.uuid = uuid;
    }

    public Fleet(String name, Customer owner, Address address) {
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.vehicles = new ArrayList<Vehicle>();
    }

    /**
     * @return The amount of vehicles in this fleet
     */
    public int size() {
        if (vehicles == null) {
            return 0;
        }
        return vehicles.size();
    }

    /**
     * Gets the address of the fleet
     *
     * @return the address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets the address of the fleet
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Gets the owner of the fleet
     *
     * @return the owner
     */
    public Customer getOwner() {
        return owner;
    }

    /**
     * Sets the owner of the fleet
     *
     * @param owner the owner
     */
    public void setOwner(Customer owner) {
        this.owner = owner;
    }

    /**
     * Gets the name of the fleet
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the fleet
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the unique id
     *
     * @return the id
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     *
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the vehicles in the fleet
     *
     * @return the vehicles
     */
    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Sets a collection of vehicle as all vehicles in the fleet
     *
     * @param vehicles the vehicles
     */
    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.FLEET;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Fleet)) return false;

        return uuid != null && uuid.equals(((Fleet) o).getUuid());

    }

    /**
     * Copies the object (and all vehicles)
     *
     * @return the copied object
     */
    @Override
    public EditableObject copy() {
        Collection<Vehicle> vehicles = new ArrayList<>();
        for (Vehicle v : this.vehicles) {
            vehicles.add((Vehicle) v.copy());
        }
        Fleet fleet = new Fleet();
        fleet.setUuid(getUuid());
        fleet.setAddress(getAddress());
        fleet.setOwner(getOwner());
        fleet.setVehicles(vehicles);
        fleet.setName(getName());
        return fleet;
    }

    @Override
    public int hashCode() {
        if (this.uuid != null) {
            return uuid.hashCode();
        }
        return super.hashCode();
    }

}
