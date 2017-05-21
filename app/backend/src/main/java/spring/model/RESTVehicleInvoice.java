package spring.model;

import model.billing.VehicleInvoice;
import model.insurance.SuretyType;

/**
 * Created by jorg on 5/17/17.
 */
public class RESTVehicleInvoice {

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

    private SuretyType suretyType;

    public RESTVehicleInvoice(VehicleInvoice vehicleInvoice) {
        setFranchise(vehicleInvoice.getFranchise());
        setInsuredValue(vehicleInvoice.getInsuredValue());
        setLicensePlate(vehicleInvoice.getLicensePlate());
        setTotalCost(vehicleInvoice.getTotalCost());
        setTotalTax(vehicleInvoice.getTotalTax());
        setVin(vehicleInvoice.getVin());
        setSuretyType(vehicleInvoice.getSurety().getSuretyType());
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }

    public int getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(int totalTax) {
        this.totalTax = totalTax;
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

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
