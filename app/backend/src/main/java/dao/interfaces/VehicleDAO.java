package dao.interfaces;

import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.UUID;

public interface VehicleDAO extends DAO<Vehicle> {
    Vehicle create(String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException;

    Vehicle update(UUID uuid, String brand, String model, String chassisNumber, String licenseplate, int value, int mileage, VehicleType type, LocalDate productionDate) throws DataAccessException;

    Filter<Vehicle> byBrand(String brandName);

    Filter<Vehicle> byModel(String model);

    Filter<Vehicle> byLicensePlate(String licensePlate);

    Filter<Vehicle> atProductionDate(LocalDate productionDate);

    Filter<Vehicle> beforeProductionDate(LocalDate productionDate);

    Filter<Vehicle> afterProductionDate(LocalDate productionDate);

    Filter<Vehicle> atLeastMileage(int mileage);

    Filter<Vehicle> maxMileage(int mileage);

    Filter<Vehicle> byType(VehicleType type);
}
