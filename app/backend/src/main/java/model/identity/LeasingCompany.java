package model.identity;

import model.fleet.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class LeasingCompany extends Company implements java.io.Serializable {

    //collection of all vehicles owned by this leasing company
    private Collection<Vehicle> vehicles;

    public LeasingCompany() {
        this.vehicles = new ArrayList<>();
        super.setCompanyType(CompanyType.LEASING_COMPANY);
    }

    public LeasingCompany(Address address, String phoneNumber, String name, String btwNumber, Collection<Vehicle> vehicles) {
        super(address, phoneNumber, name, btwNumber, CompanyType.LEASING_COMPANY);
        this.vehicles = vehicles;
    }

    public LeasingCompany(Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType, Collection<Vehicle> vehicles) {
        super(address, phoneNumber, name, btwNumber, companyType);
        this.vehicles = vehicles;
    }

    public LeasingCompany(UUID id, Address address, String phoneNumber, String name, String btwNumber, CompanyType companyType) {
        super(id, address, phoneNumber, name, btwNumber, companyType);
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}