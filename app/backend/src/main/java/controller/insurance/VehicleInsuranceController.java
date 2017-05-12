package controller.insurance;

import controller.AbstractController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.VehicleInsuranceDAO;
import model.account.Function;
import model.account.Resource;
<<<<<<< HEAD
import model.fleet.Vehicle;
import model.insurance.VehicleInsurance;

import java.util.Collection;
=======
import model.insurance.Contract;
import model.insurance.VehicleInsurance;

import java.util.Collection;
import java.util.stream.Collectors;
>>>>>>> master

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
        return vehicle == null ? getAll() : getAll(((VehicleInsuranceDAO) getDao()).byVehicle(vehicle));
    }
    public Collection<VehicleInsurance> getFiltered(Contract contract,Vehicle vehicle) throws DataAccessException, UnAuthorizedException {

        // Filter vehicles on criteria that are supported by the database
        Collection<VehicleInsurance> result = getBy(vehicle);

        // Filter vehicles on criteria that are not supported by the database
        return result.stream()
                .filter(c -> contract == null || c.getContract().equals(contract))
                .collect(Collectors.toList());

    }
}
