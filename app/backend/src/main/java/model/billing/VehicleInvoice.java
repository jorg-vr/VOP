package model.billing;

import model.insurance.VehicleInsurance;

import java.util.UUID;

/**
 * Copy of VehicleInsurance, so when a VehicleInsurance is edited, we stil have the original values
 * Created by sam on 5/15/17.
 */
public class VehicleInvoice {
    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * The VehicleInsurance of the invoice
     */
    private VehicleInsurance vehicleInsurance;

    /**
     * The total cost of a VehicleInsurance or the new calculated cost of a statement or correction
     */
    private int totalCost;

    /**
     * The total tax of a VehicleInsurance or the new calculated tax of a statement or correction
     */
    private int totalTax;

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
     * the VIN-number
     */
    private String vin;

    /**
     * The license plate
     */
    private String licensePlate;


    /**
     * Default Constructor
     */
    public VehicleInvoice() {
    }

    /**
     * Gets the uuid
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid
     * @param uuid te uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the VehicleInsurance
     * @return
     */
    public VehicleInsurance getVehicleInsurance() {
        return vehicleInsurance;
    }

    /**
     * Sets the VehicleInsurance
     * @param vehicleInsurance the VehicleInsurance
     */
    public void setVehicleInsurance(VehicleInsurance vehicleInsurance) {
        this.vehicleInsurance = vehicleInsurance;
    }

    /**
     * Gets the totalcost
     * @return the totalcost
     */
    public int getTotalCost() {
        return totalCost;
    }

    /**
     * Sets the totalCost
     * @param totalCost the totalCost
     */
    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }


    /**
     * Gets the total tax
     * @return the total tax
     */
    public int getTotalTax() {
        return totalTax;
    }

    /**
     * Sets the total tax
     * @param totalTax the total tax
     */
    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
    }

    /**
     * Gets the franchise
     * @return the franchise
     */
    public int getFranchise() {
        return franchise;
    }

    /**
     * Sets the franchise
     * @param franchise the franchise
     */
    public void setFranchise(int franchise) {
        this.franchise = franchise;
    }

    /**
     * Gets the insured value
     * @return the insured value
     */
    public int getInsuredValue() {
        return insuredValue;
    }

    /**
     * Sets the insured value
     * @param insuredValue Sets the insured value
     */
    public void setInsuredValue(int insuredValue) {
        this.insuredValue = insuredValue;
    }

    /**
     * Gets VIN-number
     * @return the VIN-number
     */
    public String getVin() {
        return vin;
    }

    /**
     * Sets the VIN-number
     * @param vin the VIN-number
     */
    public void setVin(String vin) {
        this.vin = vin;
    }

    /**
     * Gets the license plate
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Sets the license plate
     * @param licensePlate the license plate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
