package model.fleet;

import model.history.EditableObject;
import model.identity.LeasingCompany;
import spring.Exceptions.InvalidInputException;

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

    private VehicleType type;

    private LeasingCompany leasingCompany;

    public Vehicle() {

    }

    public Vehicle(UUID uuid, String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage, VehicleType type) {
        this.uuid = uuid;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.productionDate = productionDate;
        this.chassisNumber = chassisNumber;
        this.value = value;
        this.mileage = mileage;
        this.type = type;
    }

    public Vehicle(UUID uuid, String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage, VehicleType type, LeasingCompany leasingCompany) {
        this.uuid = uuid;
        this.brand = brand;
        this.model = model;
        this.licensePlate = licensePlate;
        this.productionDate = productionDate;
        this.chassisNumber = chassisNumber;
        this.value = value;
        this.mileage = mileage;
        this.type = type;
        this.leasingCompany = leasingCompany;
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

    /**
     * sets the licensplate of the vehicle
     *
     * @param licensePlate string representing a licensplate
     * @throws InvalidInputException throws an exception if the license plate is invalid (contains 0 characters or uses characters that are not alphanumeric)
     */
    public void setLicensePlate(String licensePlate) throws InvalidInputException {
        if (!licensePlate.matches("^[a-zA-Z0-9]+$")) {
            throw new InvalidInputException("License Plate can only use alphanumeric symbols");
        }
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

    /**
     * Checks if the given chassis number (VIN-code) has the correct format. Following requirements need to be met:
     * - length has to be 17 characters
     * - characters can only be alphanumeric
     * - the code can not contain the characters I, O, and Q
     * - the 10th character can not be U, Z or the digit 0
     * Additionally lowercase characters are converted to uppercase before storing the code.
     *
     * @param chassisNumber chassinumber or VIN-code
     * @throws InvalidInputException when the code has the wrong format.
     */
    public void setChassisNumber(String chassisNumber) throws InvalidInputException {
        String VIN = chassisNumber.toUpperCase();
        if (!VIN.matches("^[A-HJ-NPR-Z0-9]{9}[A-HJ-NPR-TV-Y1-9][A-HJ-NPR-Z0-9]{7}$")) {
            throw new InvalidInputException("VIN code has to be 17 characters long, cannot contain character I, O or Q and the 10th character cannot be U, Z or the digit 0");
        }
        this.chassisNumber = VIN;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException("Value can not be a negative value");
        }
        this.value = value;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) throws InvalidInputException {
        if (mileage < 0) {
            throw new InvalidInputException("Mileage can not be a negative value");
        }
        this.mileage = mileage;
    }

    public VehicleType getType() {
        return type;
    }

    public void setType(VehicleType type) {
        this.type = type;
    }

    public LeasingCompany getLeasingCompany() {
        return leasingCompany;
    }

    public void setLeasingCompany(LeasingCompany leasingCompany) {
        this.leasingCompany = leasingCompany;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return uuid == ((Vehicle) o).getUuid();
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
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
                ", type=" + type.getType() +
                '}';
    }

    @Override
    public EditableObject copy() {
        return new Vehicle(uuid, brand, model, licensePlate, productionDate, chassisNumber, value, mileage, (VehicleType) type.copy());
    }
}
