package dao;

import model.Insurance;
import model.Vehicle;

import java.util.Collection;
import java.util.Date;

public interface VehicleDAO {

    Vehicle create(String brand, String model, String licensePlate, Date productionDate,
                   String chassisNumber, int value, int mileage);

    Vehicle get(int id);

    boolean remove(int id);

    boolean update(Vehicle vehicle);

    Collection<Vehicle> listFiltered(Filter... filters);
}
