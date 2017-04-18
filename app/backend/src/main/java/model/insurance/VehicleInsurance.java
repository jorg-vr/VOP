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

    private Contract contract;

    /**
     * Franchise of the surety. This is the amount that has to be paid by the one who holds the surety.
     * This is a fixed amount.
     */
    private int franchise;

    // Insured value in cents
    private int insuredValue;

    public VehicleInsurance() {
    }

    /**
     * Calculates the cost of this insurance in eurocents
     * @return the cost of this insurance in eurocents
     */
    public int calculateCost() {
        int premium = surety.calculatePremium(insuredValue);
        SuretyType suretyType = surety.getSuretyType();
        int commission = (int) Math.round(premium * vehicle.getType().getCommission(suretyType));
        return premium + commission;
    }

    /**
     * Calculates the tax of this insurance in eurocents
     * @return the tax of this insurance in eurocents
     */
    public int calculateTax() {
        int premium = surety.calculatePremium(insuredValue);
        return (int) Math.round(premium * vehicle.getType().getTax(surety.getSuretyType()));
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

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    @Override
    public UUID getUuid() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof VehicleInsurance)) return false;

        VehicleInsurance that = (VehicleInsurance) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
