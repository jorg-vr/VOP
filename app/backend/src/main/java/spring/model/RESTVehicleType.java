package spring.model;

import model.account.Function;
import model.fleet.VehicleType;
import spring.controller.UUIDUtil;

/**
 * Created by jorg on 3/14/17.
 */
public class RESTVehicleType extends RESTAbstractModel<VehicleType> {
    private static final String PATH_VEHICLETYPES = "/vehicletypes";
    private String name;

    public RESTVehicleType() {
    }

    public RESTVehicleType(VehicleType vehicleType){
        super(vehicleType.getUuid(), PATH_VEHICLETYPES);
        setName(vehicleType.getType());
    }

    @Override
    public VehicleType translate(Function function) {
        VehicleType vehicleType=new VehicleType();
        vehicleType.setUuid(UUIDUtil.toUUID(getId()));
        vehicleType.setType(getName());
        return vehicleType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
