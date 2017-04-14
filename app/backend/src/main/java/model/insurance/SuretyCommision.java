package model.insurance;

import model.fleet.VehicleType;
import model.history.EditableObject;
import model.identity.Company;
import model.identity.Customer;

import java.util.UUID;

/**
 * Created by jorg on 4/12/17.
 */
public class SuretyCommision implements EditableObject {

    private UUID uuid;

    private double commission;

    /**
     * Type of the surety  as defined in SuretyType.
     */
    private SuretyType suretyType;


    private VehicleType vehicleType;

    private Customer customer;

    public UUID getUuid() {
        return uuid;
    }

    @Override
    public EditableObject copy() {
        return null;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public SuretyType getSuretyType() {
        return suretyType;
    }

    public void setSuretyType(SuretyType suretyType) {
        this.suretyType = suretyType;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof SuretyCommision)) return false;

        SuretyCommision that = (SuretyCommision) o;

        return getUuid().equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return getUuid().hashCode();
    }
}
