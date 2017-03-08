package model.fleet;

import model.identity.Customer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Fleet implements java.io.Serializable {

    private UUID uuid;

    private Customer owner;

    private Collection<Subfleet> subfleets;

    public Fleet() {
    }

    public Fleet(UUID uuid, Customer owner, Collection<Subfleet> subfleets) {
        this.uuid = uuid;
        this.owner = owner;
        this.subfleets = subfleets;
    }

    public Fleet(UUID uuid, Customer owner) {
        this.uuid = uuid;
        this.owner = owner;
        this.subfleets = new HashSet<>();
    }

    /**
     * Adds the Subfleet to the Fleet.
     * If the Subfleet is already present in the Fleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that subfleets is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Subfleet was added
     */
    public boolean add(Subfleet subfleet) {
        if (subfleets.contains(subfleet)) {
            return false;
        }
        subfleets.add(subfleet);
        return true;
    }

    /**
     * Removes the Subfleet from the Fleet.
     * If the Subfleet is not present in the Fleet, nothing will happen.
     * DEVELOPER NOTE:  We can't be sure that vehicles is a HashSet (because the Constructor accepts a Collection)
     * so we can't shorten this function to 1 line.
     *
     * @return true if the Subfleet was removed
     */
    public boolean remove(Subfleet subfleet) {
        if (!subfleets.contains(subfleet)) {
            return false;
        }
        subfleets.remove(subfleet);
        return true;
    }

    /**
     * @return The amount of subfleets in this fleet
     */
    public int size() {
        return subfleets.size();
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

    public Collection<Subfleet> getSubfleets() {
        return new ArrayList<>(subfleets);
    }

    public void setSubfleets(Collection<Subfleet> subfleets) {
        this.subfleets = subfleets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fleet fleet = (Fleet) o;

        return uuid == fleet.uuid;

    }
}
