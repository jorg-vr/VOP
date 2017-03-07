package model.identity;

import model.fleet.Fleet;

import java.util.Collection;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    public Customer() {
    }

    public Customer(String id, Address address, String email, int phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
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
