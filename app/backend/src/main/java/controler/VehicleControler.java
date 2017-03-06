package controler;

import dao.DataAccessException;
import dao.Filter;
import dao.VehicleDAO;
import dao.test.TestVehicleDAO;
import model.fleet.Vehicle;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/6/17.
 */
public class VehicleControler {

    private VehicleDAO vehicleDAO;

    public VehicleControler() {
        vehicleDAO=new TestVehicleDAO();
    }

    public Vehicle get(String id) throws DataAccessException {
        return vehicleDAO.get(UUID.fromString(id));
    }


    public void update(Vehicle vehicle) throws DataAccessException {
        vehicleDAO.update(vehicle);
    }


    public void remove(Vehicle vehicle) throws DataAccessException {
        vehicleDAO.remove(vehicle);
    }


    public Collection<Vehicle> listFiltered(Filter... filters) throws DataAccessException {
        return vehicleDAO.listFiltered(filters);
    }


    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int mileage) throws DataAccessException {
        Vehicle vehicle=new Vehicle(UUID.randomUUID(),brand,model,licensePlate,productionDate,chassisNumber,0,mileage); //TODO value
        vehicleDAO.update(vehicle);
        return vehicle;
    }
}
