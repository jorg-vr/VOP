package model.identity;

import model.fleet.Fleet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    public Customer() {
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER);
        this.fleets = new ArrayList<Fleet>();
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.fleets = new ArrayList<Fleet>();
    }

    public Customer(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);
    }

    public Collection<Fleet> getFleets() {
        return fleets;
    }

    public void setFleets(Collection<Fleet> fleets) {
        this.fleets = fleets;
    }
}
