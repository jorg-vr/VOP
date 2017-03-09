package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleDAO implements VehicleDAO {

    @Override
    public Vehicle create(Vehicle vehicle) throws DataAccessException {
        return null;
    }

    @Override
    public Vehicle get(UUID id) throws DataAccessException {
        return null;
    }

    @Override
    public void update(Vehicle vehicle) throws DataAccessException {

    }

    @Override
    public void remove(Vehicle vehicle) throws DataAccessException {

    }

    @Override
    public Collection<Vehicle> listFiltered(Filter[] filters) throws DataAccessException {
        return null;
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
