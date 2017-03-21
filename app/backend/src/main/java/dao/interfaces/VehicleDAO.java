package dao.interfaces;

import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.UUID;


/**
 * DAO for the bean Vehicle
 * Created by sam on 3/7/17.
 */
public interface VehicleDAO extends DAO<Vehicle> {

    /**
     * Creates a new vehicle
     *
     * @param brand          the brand
     * @param model          the model
     * @param chassisNumber  the vin number
     * @param licenseplate   the license plate
     * @param value          the value of the vehicle
     * @param mileage        the mileage
     * @param type           the VehicleType of the vehicle
     * @param productionDate the production date
     * @param fleet          the fleet which the vehicle is in
     * @return A new Vehicle
     * @throws DataAccessException Thrown when invalid values are given or when the object cannot be created
     */
    Vehicle create(String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate, Fleet fleet) throws DataAccessException;

    /**
     * Updates an existing vehicle
     *
     * @param uuid           the id of the vehicle
     * @param brand          the brand
     * @param model          the model
     * @param chassisNumber  the vin number
     * @param licenseplate   the license plate
     * @param value          the value of the vehicle
     * @param mileage        the mileage
     * @param type           the VehicleType of the vehicle
     * @param productionDate the production date
     * @param fleet          the fleet which the vehicle is in
     * @return A new Vehicle
     * @throws DataAccessException Thrown when invalid values are given or when the object cannot be created
     */
    Vehicle update(UUID uuid, String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate, Fleet fleet) throws DataAccessException;

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which have the given brand.
     *
     * @param brandName The brand to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> byBrand(String brandName);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which have the given model.
     *
     * @param model The model to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> byModel(String model);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which have the given licensePlate.
     *
     * @param licensePlate The licensePlate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> byLicensePlate(String licensePlate);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which are produced at the given date.
     *
     * @param productionDate The productionDate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> atProductionDate(LocalDate productionDate);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which are produced before the given date.
     *
     * @param productionDate The productionDate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> beforeProductionDate(LocalDate productionDate);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which are produced after the given date.
     *
     * @param productionDate The productionDate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> afterProductionDate(LocalDate productionDate);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which have at least the given mileage.
     *
     * @param mileage The productionDate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> atLeastMileage(int mileage);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which have the given mileage or less.
     *
     * @param mileage The productionDate to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> maxMileage(int mileage);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles which are the given type.
     *
     * @param type The type to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> byType(VehicleType type);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Vehicles in the given fleet.
     *
     * @param fleet The fleet to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Vehicle> byFleet(Fleet fleet);
}
