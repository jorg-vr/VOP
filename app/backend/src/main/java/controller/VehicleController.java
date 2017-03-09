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
public class VehicleController extends AbstractController<Vehicle>{


    public VehicleController() {
        super(new TestVehicleDAO()); // TODO ProductionProvider.getInstance().getVehicleDAO();
    }




    /***
     *
     * @throws DataAccessException
     */
    public void update(UUID uuid,String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int mileage,  String vehicleType) throws DataAccessException {
        Vehicle modelVehicle = get(uuid);
        setVehicle(modelVehicle,brand,model,licensePlate,productionDate,chassisNumber,mileage,vehicleType);
        getDao().update(modelVehicle);
        //TODO update history
    }



    /***
     *
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
    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int mileage,  String vehicleType) throws DataAccessException {
        Vehicle vehicle=new Vehicle();
        setVehicle(vehicle,brand,model,licensePlate,productionDate,chassisNumber,mileage,vehicleType);//TODO value
        return getDao().create(vehicle);
    }

    public VehicleType getVehicleType(String vehicleType) throws DataAccessException {
        return ProductionProvider.getInstance().getVehicleTypeDAO().get(UUID.fromString(vehicleType));
    }


    private void setVehicle(Vehicle vehicle,String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int mileage,  String vehicleType) throws DataAccessException {
        vehicle.setBrand(brand);
        vehicle.setModel(model);
        vehicle.setLicensePlate(licensePlate);
        vehicle.setProductionDate(productionDate);
        vehicle.setChassisNumber(chassisNumber);
        vehicle.setMileage(mileage);
        vehicle.setType(getVehicleType(vehicleType));
    }
}
