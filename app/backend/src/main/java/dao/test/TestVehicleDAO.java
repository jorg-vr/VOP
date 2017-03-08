package dao.test;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.*;

public class TestVehicleDAO implements VehicleDAO{

    private static Map<UUID, Vehicle> vehicles = new HashMap<>();
    static {
        UUID one=UUID.randomUUID();
        UUID two=UUID.randomUUID();
        vehicles.put(one, new Vehicle(one, "Volkswagen", "Beetle", "ABC-123", LocalDate.now(), "abcdefhijk", 1000, 123));
        vehicles.put(two, new Vehicle(two, "Lamborghini", "Diablo GT", "IAM-007", LocalDate.now(), "abcdefhijk", 3, 123));
    }

    private int counter = 2;


    @Override
    public Vehicle get(UUID id) throws DataAccessException {
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

    @Override
    public void close() {

    }

    @Override
    public Filter<Vehicle> byBrand(String brandName) {
        return null;
    }

    @Override
    public Filter<Vehicle> byModel(String model) {
        return null;
    }

    @Override
    public Filter<Vehicle> byLicensePlate(String licensePlate) {
        return null;
    }

    @Override
    public Filter<Vehicle> atProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> beforeProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> afterProductionDate(LocalDate productionDate) {
        return null;
    }

    @Override
    public Filter<Vehicle> atLeastMileage(int mileage) {
        return null;
    }

    @Override
    public Filter<Vehicle> maxMileage(int mileage) {
        return null;
    }

    @Override
    public Filter<Vehicle> byType(VehicleType type) {
        return null;
    }
}
