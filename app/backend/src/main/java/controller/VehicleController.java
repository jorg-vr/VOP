package controller;

import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import dao.test.TestVehicleDAO;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/6/17.
 * class Acts as protecting interface of backend model
 * methods should in final state take care of:
 *      constraint issues
 *      history changes
 *      correct authentication
 */
public class VehicleController{

    private VehicleDAO vehicleDAO;

    public VehicleController() {
        vehicleDAO= new TestVehicleDAO(); // TODO ProductionProvider.getInstance().getVehicleDAO();
    }

    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    /***
     *
     * @param id identifies vehicle
     * @return vehicle identified by id
     * @throws DataAccessException when vehicle can't be found
     */
    public Vehicle get(String id) throws DataAccessException {
        return vehicleDAO.get(UUID.fromString(id));
    }

    /***
     *
     * @param vehicle is inserted or replaces old vehicle with same id
     * @throws DataAccessException
     */
    public void update(String id,Vehicle vehicle) throws DataAccessException {
        Vehicle modelVehicle = get(id);
        modelVehicle.setBrand(vehicle.getBrand());
        modelVehicle.setModel(vehicle.getModel());
        modelVehicle.setLicensePlate(vehicle.getLicensePlate());
        modelVehicle.setProductionDate(vehicle.getProductionDate());
        modelVehicle.setChassisNumber(vehicle.getChassisNumber());
        modelVehicle.setMileage(vehicle.getMileage());
        modelVehicle.setType(vehicle.getType());
        vehicleDAO.update(modelVehicle);
        //TODO update history
    }

    /***
     *
     * @throws DataAccessException
     */
    public void remove(String id) throws DataAccessException {
        vehicleDAO.remove(get(id));
    }

    /***
     * TODO implement this properly
     * @param filters
     * @return
     * @throws DataAccessException
     */
    public Collection<Vehicle> listFiltered(Filter... filters) throws DataAccessException {
        return vehicleDAO.listFiltered(filters);
    }

    /***
     * TODO convert type field to corresponding VehicleType object
     * Create new vehicle and generate id for given parameters
     * @param brand
     * @param model
     * @param licensePlate
     * @param productionDate
     * @param chassisNumber
     * @param mileage
     * @return
     * @throws DataAccessException
     */
    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int mileage, String type) throws DataAccessException {
        Vehicle vehicle=new Vehicle(UUID.randomUUID(),brand,model,licensePlate,productionDate,chassisNumber,0,mileage,null); //TODO value
        vehicleDAO.create(vehicle);
        return vehicle;
    }

}
