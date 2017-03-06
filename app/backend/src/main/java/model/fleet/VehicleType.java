package model.fleet;


public class VehicleType {

    private int id;

    private String type;

    // The tax in %
    private double tax;

    public VehicleType(int id, String type, double tax) {
        this.id = id;
        this.type = type;
        this.tax = tax;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VehicleType that = (VehicleType) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return id;
    }
}
