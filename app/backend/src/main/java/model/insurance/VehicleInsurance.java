package model.insurance;

import model.account.User;
import model.fleet.Vehicle;
import model.history.EditableObject;
import model.history.LogEntry;
import model.history.LogResource;

import java.time.LocalDateTime;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;

/**
 * Class representing the relation between vehicle and surety for a specific contract
 * Created by jorg on 4/9/17.
 */
public class VehicleInsurance implements EditableObject {

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The vehicle
     */
    private Vehicle vehicle;

    /**
     * The surety
     */
    private Surety surety;

    /**
     * The start date
     */
    private LocalDateTime startDate;

    /**
     * The end date
     */
    private LocalDateTime endDate;

    /**
     * The contract
     */
    private Contract contract;

    /**
     * Franchise of the surety. This is the amount that has to be paid by the one who holds the surety.
     * This is a fixed amount.
     */
    private int franchise;

    /**
     * Insured value in cents
     */
    private int insuredValue;

    /**
     * Default Constructor
     */
    public VehicleInsurance() {
    }

    /**
     * Constructor
     *
     * @param vehicle
     * @param surety
     * @param startDate
     * @param endDate
     * @param contract
     * @param franchise
     * @param insuredValue
     */
    public VehicleInsurance(Vehicle vehicle, Surety surety, LocalDateTime startDate, LocalDateTime endDate, Contract contract, int franchise, int insuredValue) {
        this.vehicle = vehicle;
        this.surety = surety;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contract = contract;
        this.franchise = franchise;
        this.insuredValue = insuredValue;
    }

    /**
     * Calculates the netto-premium of this insurance in eurocents
     * netto-premium = risk-premium + commission
     *
     * @return the cost of this insurance in eurocents
     */
    public int calculateCost() {
        int premium = surety.calculatePremium(insuredValue);
        SuretyType suretyType = surety.getSuretyType();
        int commission = (int) Math.round(premium * vehicle.getCommission(suretyType));
        return premium + commission;
    }

    /**
     * Calculates the tax of this insurance in eurocents
     *
     * @return the tax of this insurance in eurocents
     */
    public int calculateTax() {
        int premium = calculateCost();
        return (int) Math.round(premium * vehicle.getType().getTax(surety.getSuretyType()));
    }

    /**
     * Sets the uuid
     *
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the vehicle
     *
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Sets the vehicle
     *
     * @param vehicle the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Gets the surety
     *
     * @return the surety
     */
    public Surety getSurety() {
        return surety;
    }

    /**
     * Sets the surety
     *
     * @param surety
     */
    public void setSurety(Surety surety) {
        this.surety = surety;
    }

    /**
     * Gets the start date
     *
     * @return the start date
     */
    public LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date
     *
     * @param startDate the start date
     */
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    /**
     * Gets the end date
     *
     * @return the end date
     */
    public LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date
     *
     * @param endDate the end date
     */
    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    /**
     * Gets the franchise
     *
     * @return the franchise
     */
    public int getFranchise() {
        return franchise;
    }

    /**
     * Sets the franchise
     *
     * @param franchise the franchise
     */
    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    /**
     * Gets the insured value
     *
     * @return the insured value
     */
    public int getInsuredValue() {
        return insuredValue;
    }

    /**
     * Sets the insured value
     *
     * @param insuredValue the insured value
     */
    public void setInsuredValue(int insuredValue) {
        this.insuredValue = insuredValue;
    }

    /**
     * Gets the contract
     *
     * @return the contract
     */
    public Contract getContract() {
        return contract;
    }

    /**
     * Sets the contract
     *
     * @param contract the contract
     */
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Copies the object
     *
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        VehicleInsurance vehicleInsurance = new VehicleInsurance();
        vehicleInsurance.setFranchise(getFranchise());
        vehicleInsurance.setInsuredValue(getInsuredValue());
        vehicleInsurance.setUuid(getUuid());
        vehicleInsurance.setEndDate(getEndDate());
        vehicleInsurance.setStartDate(getStartDate());
        vehicleInsurance.setContract(getContract());
        vehicleInsurance.setSurety(getSurety());
        vehicleInsurance.setVehicle(getVehicle());
        return vehicleInsurance;
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.VEHICLE_INSURANCE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof VehicleInsurance)) return false;

        VehicleInsurance that = (VehicleInsurance) o;

        return getUuid() != null && getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        if (getUuid() != null) {
            return getUuid().hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return UUIDToNumberString(uuid);
    }

    @Override
    public LogEntry logCreate(User user) {
        LogEntry entry = EditableObject.super.logCreate(user);
        entry.addInterestedObject(vehicle);
        return entry;
    }

    @Override
    public LogEntry logUpdate(User user, EditableObject old) {
        LogEntry entry = EditableObject.super.logUpdate(user, old);
        entry.addInterestedObject(vehicle);
        return entry;
    }
}
