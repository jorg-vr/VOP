package controller;

import dao.database.ProductionProvider;
import model.account.Function;
import model.account.Resource;
import model.fleet.VehicleType;

/**
 * Created by jorg on 3/30/17.
 */
public class VehicleTypeController extends AbstractController<VehicleType> {


    public VehicleTypeController(Function function) {
        super(ProductionProvider.getInstance().getVehicleTypeDAO(), Resource.VEHICLETYPE, function);
    }

    @Override
    public boolean isOwner(VehicleType vehicleType, Function function) {
        return false;//Vehicletypes can't be owned
    }
}