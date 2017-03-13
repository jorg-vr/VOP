package model.fleet;


import model.history.EditableObject;
import spring.Exceptions.InvalidInputException;

import java.util.UUID;

public class VehicleType implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private String type;

    // The tax in %
    private double tax;

    public VehicleType() {
    }

    public VehicleType(UUID uuid, String type, double tax) {
        this.uuid = uuid;
        this.type = type;
        this.tax = tax;
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

    public double getTax() {
        return tax;
    }

    /**
     * set the tax value and checks if it is a valid percentage.
     *
     * @param tax road tax for the vehicle category
     * @throws InvalidInputException if the given tax value is a negative value
     */
    public void setTax(double tax) throws InvalidInputException {
        if (tax < 0) {
            throw new InvalidInputException("Tax value has to be a positive percentage");
        }
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleType that = (VehicleType) o;

        return uuid == that.uuid;

    }

    @Override
    public int hashCode() {
        return uuid .hashCode();
    }

    @Override
    public EditableObject copy() {
        return new VehicleType(uuid, type, tax);
    }
}
