package src.main.java.dao.test;

import src.main.java.dao.DataAccessException;
import src.main.java.dao.Filter;
import src.main.java.dao.VehicleDAO;
import src.main.java.model.fleet.Vehicle;

import java.time.LocalDate;
import java.util.*;

public class TestVehicleDAO implements VehicleDAO{

    private static Map<Integer, Vehicle> vehicles = new HashMap<>();
    static {
        vehicles.put(0, new Vehicle(1, "Volkswagen", "Beetle", "ABC-123", LocalDate.now(), "abcdefhijk", 1000, 123));
        vehicles.put(1, new Vehicle(2, "Lamborghini", "Diablo GT", "IAM-007", LocalDate.now(), "abcdefhijk", 3, 123));
    }

    private int counter = 2;

    @Override
    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage) {
        Vehicle vehicle = new Vehicle(counter, brand, model, licensePlate, productionDate, chassisNumber, value, mileage);
        vehicles.put(counter, vehicle);
        counter++;
        return vehicle;
    }

    @Override
    public Vehicle get(int id) throws DataAccessException {
        if (!vehicles.containsKey(id)) {
            throw new DataAccessException();
        }
        return vehicles.get(id);
    }

    @Override
    public void update(Vehicle vehicle) throws DataAccessException {
        if (!vehicles.containsValue(vehicle)) {
            throw new DataAccessException();
        }
        vehicles.put(vehicle.getId(), vehicle);
    }

    @Override
    public void remove(Vehicle vehicle) throws DataAccessException {
        vehicles.remove(vehicle.getId());
    }

    @Override
    public Collection<Vehicle> listFiltered(Filter... filters) {
        // TODO actually use filters
        return new HashSet<>(vehicles.values());
    }
}
