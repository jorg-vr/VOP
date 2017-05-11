package controller;

import controller.exceptions.UnAuthorizedException;
import controller.insurance.CommissionContainerController;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.VehicleDAO;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.fleet.Vehicle;

import java.util.Collection;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends CommissionContainerController<Vehicle> {

    public VehicleController(Function function, DAOManager manager) {
        super(manager.getVehicleDAO(), Resource.VEHICLE, function);
    }


    @Override
    public boolean isOwner(Vehicle vehicle, Function function) {
        return vehicle.getFleet().getOwner().equals(function.getCompany());
    }

    /**
     * Retrieve all vehicles, filtered on the arguments. If an argument is null, it will be ignored
     *
     * @param licensePlate licensePlate of vehicle contains the pattern licensePlate
     * @param vin          NOT implemented TODO
     * @param year         NOT implemented TODO
     * @param fleet        only return vehicles of this fleet, if the fleet does not exist in the database, an empty list will be returned
     * @param type         NOT implemented TODO
     * @return All Vehicles, filtered on arguments
     * @throws DataAccessException   Something went horribly wrong with the database
     * @throws UnAuthorizedException Function is not authorized to get all the objects.
     */
    public Collection<Vehicle> getFiltered(String licensePlate, String vin,
                                           Integer year, Fleet fleet, String type) throws DataAccessException, UnAuthorizedException {
        VehicleDAO dao = (VehicleDAO) getDao();
        return getAll(
                dao.byFleet(fleet),
                dao.byLicensePlate(licensePlate)
        );
    }
}
