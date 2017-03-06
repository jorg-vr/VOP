package model.fleet;

import java.time.LocalDate;

public class Vehicle {

    private int id;

    private String brand;

    private String model;

    private String licensePlate;

    private LocalDate productionDate;

    private String chassisNumber;

    // The value of the car in euros
    private int value;

    // Also known as "kilometerstand" in Dutch
    private int mileage;

    public Vehicle(int id, String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.productionDate = productionDate;
        this.chassisNumber = chassisNumber;
        this.value = value;
        this.mileage = mileage;
    }

    public int getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public String getChassisNumber() {
        return chassisNumber;
    }

    public void setChassisNumber(String chassisNumber) {
        this.chassisNumber = chassisNumber;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vehicle vehicle = (Vehicle) o;

        return id == vehicle.id;

    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", productionDate=" + productionDate +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", value=" + value +
                ", mileage=" + mileage +
                '}';
    }
}
