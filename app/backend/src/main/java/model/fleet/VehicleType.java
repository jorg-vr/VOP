package model.fleet;


import model.CommissionContainer;
import model.history.EditableObject;
import model.history.LogResource;
import model.insurance.SuretyType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static util.UUIDUtil.UUIDToNumberString;


/**
 * Class representing the SubFleet, each vehicle has a VehicleType and subfleets can be retrieved this way
 */
public class VehicleType implements EditableObject, java.io.Serializable, CommissionContainer {

    /**
     * The unique id
     */
    private UUID uuid;

    /**
     * Name of the VehicleType, should be unique
     */
    private String type;


    /**
     * Taxes for each suretytype
     */
    private Map<SuretyType, Double> taxes;

    /**
     * Commissions for each surety type, can also be configured in Vehicle or Customer
     */
    private Map<SuretyType, Double> commissions;

    /**
     * Default constructor
     */
    public VehicleType() {
        taxes = new HashMap<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     *
     * @param type name of the VehicleType
     */
    public VehicleType(String type) {
        this.type = type;
        taxes = new HashMap<>();
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     *
     * @param type        the type
     * @param taxes       the taxes
     * @param commissions the commissions
     */
    public VehicleType(String type, Map<SuretyType, Double> taxes, Map<SuretyType, Double> commissions) {
        this.type = type;
        this.taxes = taxes;
        this.commissions = commissions;
    }

    /**
     * Gets the id
     *
     * @return the id
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the id
     *
     * @param uuid the id
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the name of the VehicleType
     *
     * @return the name
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the name of the VehicleType
     *
     * @param type the name
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the taxes
     *
     * @return the taxes
     */
    public Map<SuretyType, Double> getTaxes() {
        return taxes;
    }

    /**
     * Sets the taxes
     *
     * @param taxes the taxes
     */
    public void setTaxes(Map<SuretyType, Double> taxes) {
        this.taxes = taxes;
    }

    /**
     * Gets the commissions
     *
     * @return the commissions
     */
    public Map<SuretyType, Double> getCommissions() {
        return commissions;
    }

    /**
     * Sets the commissions
     *
     * @param commissions
     */
    public void setCommissions(Map<SuretyType, Double> commissions) {
        this.commissions = commissions;
    }

    /**
     * Gets a specific tax
     *
     * @param suretyType the surety type
     * @return the tax percentage
     */
    public double getTax(SuretyType suretyType) {
        return getTaxes().get(suretyType);
    }

    /**
     * Sets a specific tax
     *
     * @param suretyType the Surety type
     * @param tax        the tax
     */
    public void setTax(SuretyType suretyType, double tax) {
        taxes.put(suretyType, tax);
    }

    /**
     * Gets a specific commission
     *
     * @param suretyType the suretytype
     * @return the commission percentage
     */
    public double getCommission(SuretyType suretyType) {
        return commissions.get(suretyType);
    }

    /**
     * Sets a specific commission
     *
     * @param suretyType the Surety type
     * @param commission the commission percentage
     */
    public void setCommission(SuretyType suretyType, double commission) {
        commissions.put(suretyType, commission);
    }

    @Override
    public LogResource getLogResource() {
        return LogResource.VEHICLE_TYPE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof VehicleType)) {
            return false;
        }
        VehicleType that = (VehicleType) o;
        return uuid != null && uuid.equals(that.getUuid());
    }

    @Override
    public int hashCode() {
        if (uuid != null) {
            return uuid.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public String toString() {
        return UUIDToNumberString(uuid);
    }

    /**
     * Copies the object
     * @return the copy
     */
    @Override
    public EditableObject copy() {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(getType());
        vehicleType.setUuid(getUuid());
        vehicleType.setTaxes(new HashMap<>(taxes));
        vehicleType.setCommissions(new HashMap<>(commissions));
        return vehicleType;
    }
}
