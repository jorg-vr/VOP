package dao.interfaces;

import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;

public interface VehicleDAO extends DAO<Vehicle> {
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
