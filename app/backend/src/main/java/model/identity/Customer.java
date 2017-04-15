package model.identity;

import model.fleet.Fleet;
import model.insurance.Contract;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public class Customer extends Company implements java.io.Serializable {

    private Collection<Fleet> fleets;

    private Collection<Contract> contracts;

    public Customer() {
        this.fleets = new ArrayList<>();
        super.setCompanyType(CompanyType.CUSTOMER);
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER);
        this.fleets = new ArrayList<Fleet>();
    }

    public Customer(Address address, String phoneNumber, String name, String btwNumber, Periodicity invoicePeriodicity, Periodicity statementPeriodicity) {
        super(address, phoneNumber, name, btwNumber, CompanyType.CUSTOMER, invoicePeriodicity, statementPeriodicity);
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

    public Collection<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Collection<Contract> contracts) {
        this.contracts = contracts;
    }
}
