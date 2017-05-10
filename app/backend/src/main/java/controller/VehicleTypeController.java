package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.fleet.Vehicle;
import model.fleet.VehicleType;

/**
 * Created by jorg on 3/30/17.
 */
public class VehicleTypeController extends AbstractController<VehicleType> {


    public VehicleTypeController(Function function, DAOManager manager) {
        super(manager.getVehicleTypeDAO(), Resource.VEHICLETYPE, function);
    }

    @Override
    public VehicleType update(VehicleType vehicleType) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        VehicleType old = get(vehicleType.getUuid());
        vehicleType.setCommissions(old.getCommissions());
        return super.update(vehicleType);
    }

    @Override
    public boolean isOwner(VehicleType vehicleType, Function function) {
        return true;
    }
}
