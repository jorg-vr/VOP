package dao;

import model.fleet.Vehicle;

import java.time.LocalDate;

public interface VehicleDAO extends DAO<Vehicle> {

    Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate,
                   String chassisNumber, int value, int mileage);
}
