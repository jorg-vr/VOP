package model.identity;

import model.fleet.Vehicle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public class LeasingCompany extends Company implements java.io.Serializable {

    //collection of all vehicles owned by this leasing company
    private Collection<Vehicle> vehicles;

    public LeasingCompany() {
    }

    public LeasingCompany(UUID id, Address address, String email, String phoneNumber, String name, String btwNumber, String bankAccountNumber, CompanyType companyType) {
        super(id, address, email, phoneNumber, name, btwNumber, bankAccountNumber, companyType);
    }

    /**
     * Adds the given vehicle to the list of vehicles the leasing company owns.
     * Nothing will happen if the vehicle is already in the list.
     *
     * @param vehicle vehicle who's ownership has to be assigned to this leasingcompany
     * @return true when the vehicle has been succesfully added
     */
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicles == null) {
            vehicles = new ArrayList<Vehicle>();
        } else if (vehicle == null || vehicles.contains(vehicle)) {
            return false;
        }
        return vehicles.add(vehicle);
    }

    /**
     * removes the vehicle from the list of owned vehicles (if it is in the list)
     *
     * @param vehicle vehicle that needs to be removed
     * @return true if the vehicle was succesfully removed
     */
    public boolean removeVehicle(Vehicle vehicle) {
        if(vehicles == null){
            return false;
        }
        return vehicles.remove(vehicle);
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }
}