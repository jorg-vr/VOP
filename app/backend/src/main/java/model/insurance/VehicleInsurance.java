package model.insurance;

import model.fleet.Vehicle;
import model.history.EditableObject;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 4/9/17.
 */
public class VehicleInsurance implements EditableObject {

    private UUID uuid;
    private Vehicle vehicle;
    private Collection<Surety> sureties;

    public VehicleInsurance() {
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Collection<Surety> getSureties() {
        return sureties;
    }

    public void setSureties(Collection<Surety> sureties) {
        this.sureties = sureties;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
