package controller;

import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.VehicleDAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

import java.time.LocalDate;
import java.util.Collection;
import java.util.UUID;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends AbstractController<Vehicle>{

    public VehicleController(Function function) {
        super(BackendApplication.getProvider().getVehicleDAO(), Resource.VEHCLE,function);
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

    @Override
    public boolean isOwner(Vehicle vehicle, Function function) {
        return vehicle.getFleet().getOwner().equals(function.getCompany());
    }
}
