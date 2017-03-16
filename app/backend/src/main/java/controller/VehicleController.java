package controller;

import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleDAO;
import dao.test.TestVehicleDAO;
import main.BackendApplication;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends AbstractController<Vehicle>{

    public VehicleController() {
        super(BackendApplication.getProvider().getVehicleDAO());
    }

    /***
     *
     * @throws DataAccessException
     */
    public Vehicle update(UUID uuid,String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value,int mileage,  UUID vehicleType,UUID fleet ) throws DataAccessException {
        return ((VehicleDAO) getDao()).update(uuid,brand,model,chassisNumber,licensePlate,value,mileage,getVehicleType(vehicleType),productionDate,new FleetController().get(fleet));
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
    public Vehicle create(String brand, String model, String licensePlate, LocalDate productionDate, String chassisNumber, int value,int mileage,  UUID vehicleType,UUID fleet ) throws DataAccessException {

        return ((VehicleDAO) getDao()).create(brand,model,chassisNumber,licensePlate,value,mileage,getVehicleType(vehicleType),productionDate,new FleetController().get(fleet));
    }

    public VehicleType getVehicleType(UUID vehicleType) throws DataAccessException {
        return ProductionProvider.getInstance().getVehicleTypeDAO().get(vehicleType);
    }

    /***
     * Gives a collection of all vehicletypes in the database.
     * @return
     * @throws DataAccessException
     */
    public Collection<VehicleType> getAllVehicleTypes() throws DataAccessException {
        return ProductionProvider.getInstance().getVehicleTypeDAO().listFiltered();
    }

}
