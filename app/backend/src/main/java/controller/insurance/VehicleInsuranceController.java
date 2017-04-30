package controller.insurance;

import controller.AbstractController;
import dao.interfaces.DAO;
import dao.interfaces.DAOManager;
import main.BackendApplication;
import model.account.Function;
import model.account.Resource;
import model.insurance.VehicleInsurance;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class VehicleInsuranceController extends AbstractController<VehicleInsurance> {

    public VehicleInsuranceController(Function function, DAOManager manager) {
        super(manager.getVehicleInsuranceDao(), Resource.INSURANCE, function);
    }

    @Override
    public boolean isOwner(VehicleInsurance insurance, Function function) {
        return insurance.getVehicle().getFleet().getOwner().equals(function.getCompany());
    }
}
