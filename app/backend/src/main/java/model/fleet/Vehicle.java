package model.fleet;

import model.history.EditableObject;
import model.insurance.SuretyType;
import spring.exceptions.InvalidInputException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Class representing a vehicle
 */
public class Vehicle implements EditableObject, java.io.Serializable {

    /**
     * The unique id of the object
     */
    private UUID uuid;

    /**
     * The brand of the vehicle
     */
    private String brand;

    /**
     * Model of the vehicle
     */
    private String model;

    /**
     * License plate of the vehicle, should be unique
     */
    private String licensePlate;

    /**
     * Production date of the vehicle, should be in the past
     */
    private LocalDate productionDate;

    /**
     * VIN-number of the vehicle, should be unique and the right format (e.g. 17 chars)
     */
    private String chassisNumber;

    // The value of the vehicle in cents
    private int value;

    // The mileage of the vehicle
    private int mileage;

    /**
     * Type of the vehicle, representing a subfleet
     */
    private VehicleType type;

    /**
     * The fleet containing the vehicle
     */
    private Fleet fleet;

    /**
     * Commissions can be determined by VehicleType, by Customer and By vehicle
     */
    private Map<SuretyType, Double> commissions;

    /**
     * Default constructor
     */
    public Vehicle() {
        commissions = new HashMap<>();
    }

    /**
     * Constructor
     *
     * @param brand          the brand
     * @param model          the model
     * @param chassisNumber  the VIN-number
     * @param licensePlate   the license plate
     * @param value          the value (in cents)
     * @param mileage        the mileage
     * @param type           the VehicleType
     * @param productionDate the production date
     * @param fleet          the fleet containing this vehicle
     * @throws InvalidInputException When VIN-number is not formatted right or mileage/value is negative
     */
    public Vehicle(String brand, String model, String chassisNumber, String licensePlate, int value, int mileage, VehicleType type, LocalDate productionDate, Fleet fleet) throws InvalidInputException {
        this.brand = brand;
        this.model = model;
        this.productionDate = productionDate;
        setChassisNumber(chassisNumber);
        this.licensePlate = licensePlate;
        setValue(value);
        setMileage(mileage);
        this.type = type;
        this.fleet = fleet;
        commissions = new HashMap<>();
    }

    /**
     * Gets the fleet
     *
     * @return the fleet
     */
    public Fleet getFleet() {
        return fleet;
    }

    /**
     * Sets the fleet
     *
     * @param fleet the fleet
     */
    public void setFleet(Fleet fleet) {
        this.fleet = fleet;
    }

    /**
     * Gets the uuid
     *
     * @return the uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Sets the uuid
     *
     * @param uuid the uuid
     */
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets the brand
     *
     * @return the brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the brand
     *
     * @param brand the brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * Gets the model
     *
     * @return the model
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model
     *
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Gets the license plate
     *
     * @return the license plate
     */
    public String getLicensePlate() {
        return licensePlate;
    }

    /**
     * Sets the license plate of the vehicle
     *
     * @param licensePlate string representing a licenseplate
     */
    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    /**
     * Gets the production date
     *
     * @return the production date
     */
    public LocalDate getProductionDate() {
        return productionDate;
    }

    /**
     * Sets the production date
     *
     * @param productionDate the production date
     */
    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    /**
     * Gets the VIN-number
     *
     * @return the VIN-number
     */
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
        if (chassisNumber != null) {
            String VIN = chassisNumber.toUpperCase();
            if (!VIN.matches("^[A-HJ-NPR-Z0-9]{9}[A-HJ-NPR-TV-Y1-9][A-HJ-NPR-Z0-9]{7}$")) {
                throw new InvalidInputException("VIN code has to be 17 characters long, cannot contain character I, O or Q and the 10th character cannot be U, Z or the digit 0");
            }
            this.chassisNumber = VIN;
        }
    }

    /**
     * Gets the value
     *
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the Value
     *
     * @param value the value
     * @throws InvalidInputException when trying to set a negative value
     */
    public void setValue(int value) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException("Value can not be a negative value");
        }
        this.value = value;
    }

    /**
     * Gets the mileage
     *
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the mileage
     *
     * @param mileage the mileage
     * @throws InvalidInputException when trying to set a negative value
     */
    public void setMileage(int mileage) throws InvalidInputException {
        if (mileage < 0) {
            throw new InvalidInputException("Mileage can not be a negative value");
        }
        this.mileage = mileage;
    }

    /**
     * Gets the type
     *
     * @return the VehicleType
     */
    public VehicleType getType() {
        return type;
    }

    /**
     * Sets the VehicleType
     *
     * @param type the VehicleType
     */
    public void setType(VehicleType type) {
        this.type = type;
    }

    /**
     * @param suretyType type of surety of which the commission should be retrieved
     * @return the commission that should be used for this vehicle.
     * If there is no specific commission set for this vehicle, the specific commission of the owner will be retrieved.
     * If there is no specific commission set for the owner, the default of commission of the type of this vehicle will be returned.
     */
    public double getCommission(SuretyType suretyType) {
        Double vehicleCommission = getSpecificCommission(suretyType);
        if (vehicleCommission != null) {
            return vehicleCommission;
        }
        if (fleet != null) {
            Double ownerCommission = fleet.getOwner().getSpecificCommission(suretyType);
            if (ownerCommission != null) {
                return ownerCommission;
            }
        }

        return type.getCommission(suretyType);
    }

    /**
     * @param suretyType suretyType of which the commission should be returned
     * @return the specific commission of this vehicle for given SuretyType.
     * If the vehicle does not have a specific commission for that type, null will be returned
     */
    public Double getSpecificCommission(SuretyType suretyType) {
        return commissions.get(suretyType);
    }

    /**
     * Sets an specific commission, commissions can be configured for a vehicle but will not always be,
     * depending on the customer
     *
     * @param suretyType the surety type
     * @param commission the commission percentage
     */
    public void setSpecificCommission(SuretyType suretyType, double commission) {
        if (commissions == null) {
            commissions = new HashMap<>();
        }
        commissions.put(suretyType, commission);
    }

    /**
     * Removes a specific commissions, set with setSpecificCommission
     *
     * @param suretyType the surety type
     */
    public void removeSpecificCommission(SuretyType suretyType) {
        commissions.remove(suretyType);
    }

    /**
     * Gets the commissions of this vehicle (not his owner)
     *
     * @return the the commissions
     */
    public Map<SuretyType, Double> getCommissions() {
        return commissions;
    }

    /**
     * sets the commissions of this vehicle (not his owner)
     *
     * @param commissions the commissions
     */

    public void setCommissions(Map<SuretyType, Double> commissions) {
        this.commissions = commissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        return uuid != null && uuid.equals(((Vehicle) o).getUuid());
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
        if(type != null){
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
        else{
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
    }

    /**
     * Copies the vehicle
     * @return the copy of the vehicle
     */
    @Override
    public EditableObject copy() {
        Vehicle vehicle = new Vehicle();
        vehicle.setModel(getModel());
        vehicle.setProductionDate(getProductionDate());
        vehicle.setBrand(getBrand());
        vehicle.setUuid(getUuid());
        vehicle.setFleet(getFleet());
        vehicle.setChassisNumber(getChassisNumber());
        vehicle.setType((VehicleType) getType().copy());
        vehicle.setLicensePlate(getLicensePlate());
        vehicle.setValue(getValue());
        vehicle.setMileage(getMileage());
        vehicle.setCommissions(new HashMap<>(getCommissions()));
        return vehicle;
    }
}
