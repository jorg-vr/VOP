package model.insurance;

import model.fleet.VehicleType;
import model.identity.Company;

import java.util.UUID;

/**
 * Created by jorg on 4/12/17.
 */
public class SuretyCommision {

    private UUID uuid;

    private double commission;

    /**
     * Type of the surety  as defined in SuretyType.
     */
    private SuretyType suretyType;


    private VehicleType vehicleType;

    private Company company;

    public UUID getUuid() {
        return uuid;
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
