package model.identity;

import model.fleet.Fleet;

import java.util.Collection;

public class Customer extends Company{

    private Collection<Fleet> fleets;

    public Customer(int id, Address address, String email, int phoneNumber, String name, int btwNumber, int bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    /**
     *
     * @param fleet
     */
    public void addFleet(Fleet fleet){

    }
}
