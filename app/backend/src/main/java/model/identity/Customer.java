package model.identity;

import model.fleet.Fleet;

import java.util.Collection;
import java.util.UUID;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    public Customer() {
    }

    public Customer(UUID uuid, Address address, String email, int phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(uuid, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    public boolean addFleet(Fleet fleet){
        if(fleets.contains(fleet)){
            return false;
        }
        fleets.add(fleet);
        return true;
    }

    public Collection<Fleet> getFleets() {
        return fleets;
    }

    public void setFleets(Collection<Fleet> fleets) {
        this.fleets = fleets;
    }
}
