package model.fleet;

import model.history.EditableObject;
import model.identity.Address;
import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Fleet implements EditableObject, java.io.Serializable {

    private UUID uuid;
    private String name;
    private Customer owner;
    private Address address;

    private Collection<Vehicle> vehicles;

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

    public Fleet(UUID uuid, String name, Customer owner, Address address, Collection<Vehicle> vehicles) {
        this.uuid = uuid;
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.vehicles = vehicles;
    }

    public Fleet(UUID uuid, Collection<Vehicle> vehicles) {
        this.uuid = uuid;

        this.vehicles = vehicles;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
        if (o == null || !(o instanceof Fleet)) return false;

        return uuid.equals(((Fleet) o).getUuid());

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
