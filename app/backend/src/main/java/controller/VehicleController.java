package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import dao.interfaces.VehicleDAO;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.identity.LeasingCompany;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends AbstractController<Vehicle> {

    public VehicleController(Function function) {
        super(BackendApplication.getProvider().getVehicleDAO(), Resource.VEHICLE, function);
    }


    @Override
    public boolean isOwner(Vehicle vehicle, Function function) {
        return vehicle.getFleet().getOwner().equals(function.getCompany());
    }

    public Collection<Vehicle> getFiltered(String licensePlate, String vin, LeasingCompany leasingCompany,
                                           Integer year, Fleet fleet, String type) throws DataAccessException, UnAuthorizedException {
        VehicleDAO dao = (VehicleDAO) getDao();
        return getAll(
                dao.byFleet(fleet),
                dao.byLicensePlate(licensePlate)
        );
    }
}
