package model.insurance;

import model.fleet.Vehicle;
import model.history.EditableObject;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 4/9/17.
 */
public class VehicleInsurance implements EditableObject {

    private UUID uuid;
    private Vehicle vehicle;
    private Surety surety;

    private LocalDateTime startDate;
    private LocalDateTime endDate;
    /**
     * Franchise of the surety. This is the amount that has to be paid by the one who holds the surety.
     * This is a fixed amount.
     */
    private int franchise;

    private int insuredValue;

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

    public Surety getSurety() {
        return surety;
    }

    public void setSurety(Surety surety) {
        this.surety = surety;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public int getFranchise() {
        return franchise;
    }

    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    public int getInsuredValue() {
        return insuredValue;
    }

    public void setInsuredValue(int insuredValue) {
        this.insuredValue = insuredValue;
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
