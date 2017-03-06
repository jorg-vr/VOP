package src.main.java.dao;

import src.main.java.model.fleet.Vehicle;

import java.time.LocalDate;

public interface VehicleDAO extends DAO<Vehicle> {

    Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate,
                   String chassisNumber, int value, int mileage);
}
