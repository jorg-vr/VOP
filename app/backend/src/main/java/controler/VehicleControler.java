package controler;

import dao.DataAccessException;
import dao.Filter;
import dao.VehicleDAO;
import dao.test.TestVehicleDAO;
import model.fleet.Vehicle;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by jorg on 3/6/17.
 */
public class VehicleControler {

    private VehicleDAO vehicleDAO;

    public VehicleControler() {
        vehicleDAO=new TestVehicleDAO();
    }

    public Vehicle get(int id) throws DataAccessException {
        return vehicleDAO.get(id);
    }


    public void update(Vehicle vehicle) throws DataAccessException {

    }


    public void remove(Vehicle vehicle) throws DataAccessException {

    }


    public Collection<Vehicle> listFiltered(Filter... filters) throws DataAccessException {
        return null;
    }


    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value, int mileage) {
        return null;
    }
}
