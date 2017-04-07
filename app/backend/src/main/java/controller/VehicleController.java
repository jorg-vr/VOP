package controller;

import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Vehicle;

/**
 * For more information of what this class does, see AbstractController
 */
public class VehicleController extends AbstractController<Vehicle>{

    public VehicleController(Function function) {
        super(BackendApplication.getProvider().getVehicleDAO(), Resource.VEHICLE,function);
    }


    @Override
    public boolean isOwner(Vehicle vehicle, Function function) {
        return vehicle.getFleet().getOwner().equals(function.getCompany());
    }
}
