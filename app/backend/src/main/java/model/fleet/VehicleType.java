package model.fleet;


import model.history.EditableObject;

import java.util.UUID;

public class VehicleType implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private String type;

    // The tax in %
    private double tax;

    // vehicle-type id for use by the api
    private int id;

    public VehicleType() {
    }

    public VehicleType(UUID uuid, String type, double tax, int id) {
        this.uuid = uuid;
        this.type = type;
        this.tax = tax;
        this.id = id;
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

    public void setTax(double tax) {
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return uuid.hashCode();
    }

    @Override
    public EditableObject copy() {
        return new VehicleType(uuid, type, tax, id);
    }
}
