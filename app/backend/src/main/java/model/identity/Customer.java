package model.identity;

import model.fleet.Fleet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    public Customer() {
    }


    public Customer(UUID id, Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    /**
     * Adds the given fleet to the list of fleets the customer owns and changes the fleet's owner to this customer
     *
     * @param fleet fleet who's ownership has to be assigned to this customer
     * @return true when the fleet has been succesfully added
     */
    public boolean addFleet(Fleet fleet) {
        if (fleets == null) {
            fleets = new ArrayList<Fleet>();
        } else if (fleet == null || fleets.contains(fleet)) {
            return false;
        }
        //makes sure the fleet was added before setting it's owner to this customer
        boolean added = fleets.add(fleet);
        if(added){
            fleet.setOwner(this);
        }
        return added;
    }

    /**
     * removes the fleet from the list of fleets the customer owns and changes the fleet's owner to unll upon succesfull removal
     * @param fleet which has to be removed from the fleets this customer owns
     * @return true when the fleet has been succesfully removed
     */
    public boolean removeFleet(Fleet fleet){
        if (fleets == null){
            return false;
        }
        if(fleet == null || !fleets.contains(fleet)){
            return false;
        }
        //makes sure the fleet was succesfully removed before changing it's owner to null
        boolean removed = fleets.remove(fleet);
        return removed;
    }

    public Collection<Fleet> getFleets() {
        return fleets;
    }

    public void setFleets(Collection<Fleet> fleets) {
        this.fleets = fleets;
    }
}
