package model.fleet;

import model.history.EditableObject;

import java.time.LocalDate;
import java.util.UUID;

public class Vehicle implements EditableObject, java.io.Serializable {

    private UUID uuid;

    private String brand;

    private String model;

    private String licensePlate;

    private LocalDate productionDate;

    private String chassisNumber;

    // The value of the car in euros
    private int value;

    // Also known as "kilometerstand" in Dutch
    private int mileage;

    public Vehicle() {
        
    }

    public Vehicle(UUID uuid, String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage) {
        this.uuid = uuid;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.productionDate = productionDate;
        this.chassisNumber = chassisNumber;
        this.value = value;
        this.mileage = mileage;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

        return uuid == vehicle.uuid;

    }


    @Override
    public String toString() {
        return "Vehicle{" +
                "uuid=" + uuid +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", productionDate=" + productionDate +
                ", chassisNumber='" + chassisNumber + '\'' +
                ", value=" + value +
                ", mileage=" + mileage +
                '}';
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
