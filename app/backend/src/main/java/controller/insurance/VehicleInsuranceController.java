package controller.insurance;

import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.VehicleInsuranceDAO;
import model.account.Function;
import model.account.Resource;
import model.fleet.Vehicle;
import model.insurance.VehicleInsurance;

import java.util.Collection;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class VehicleInsuranceController extends AbstractController<VehicleInsurance> {

    public VehicleInsuranceController(Function function, DAOManager manager) {
        super(manager, manager.getVehicleInsuranceDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(VehicleInsurance insurance, Function function) {
        return insurance.getVehicle().getFleet().getOwner().equals(function.getCompany());
    }

    public Collection<VehicleInsurance> getBy(Vehicle vehicle) throws DataAccessException, UnAuthorizedException {
        return vehicle==null?getAll():getAll(((VehicleInsuranceDAO)getDao()).byVehicle(vehicle));
    }
}
