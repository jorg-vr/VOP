package model.fleet;


import model.history.EditableObject;
import model.insurance.Surety;
import model.insurance.SuretyType;
import spring.exceptions.InvalidInputException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.DoubleAccumulator;

public class VehicleType implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private String type;


    private Map<SuretyType,Double> taxes;
    private Map<SuretyType,Double> commissions;

    public VehicleType() {
        taxes = new HashMap<>();
        commissions = new HashMap<>();
    }

    @Deprecated
    public VehicleType(String type, double tax){

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<SuretyType, Double> getTaxes() {
        return taxes;
    }

    public void setTaxes(Map<SuretyType, Double> taxes) {
        this.taxes = taxes;
    }

    public Map<SuretyType, Double> getCommissions() {
        return commissions;
    }

    public void setCommissions(Map<SuretyType, Double> commissions) {
        this.commissions = commissions;
    }

    public double getTax(SuretyType suretyType){
        return getTaxes().get(suretyType);
    }

    public void setTax(SuretyType suretyType, double tax) {
        taxes.put(suretyType, tax);
    }

    public double getCommission(SuretyType suretyType){
        return commissions.get(suretyType);
    }

    public void setCommission(SuretyType suretyType, double commission) {
        commissions.put(suretyType, commission);
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof VehicleType)) {
            return false;
        }

        VehicleType that = (VehicleType) o;

        return uuid.equals(that.getUuid());

    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
