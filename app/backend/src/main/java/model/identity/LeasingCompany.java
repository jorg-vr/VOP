package model.identity;

import model.fleet.Vehicle;

import java.util.Collection;
import java.util.UUID;


public class LeasingCompany extends Company implements java.io.Serializable {

    //collection of all vehicles owned by this leasing company
    private Collection<Vehicle> vehicles;

    public LeasingCompany() {
    }

    public LeasingCompany(Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, Collection<Vehicle> vehicles) {
        super(address, email, phoneNumber, name, btwNumber, bankAccountNumber, CompanyType.LEASING_COMPANY);
        this.vehicles = vehicles;
    }

    public LeasingCompany(Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType, Collection<Vehicle> vehicles) {
        super(address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
        this.vehicles = vehicles;
    }

    public LeasingCompany(UUID id, Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}