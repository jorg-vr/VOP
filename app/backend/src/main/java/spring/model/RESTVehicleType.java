package spring.model;

import model.account.Function;
import model.fleet.VehicleType;
import util.UUIDUtil;

import static util.MyProperties.PATH_TYPES;
import static util.MyProperties.PATH_VEHICLES;
import static util.MyProperties.getProperty;

/**
 * Created by jorg on 3/14/17.
 */
public class RESTVehicleType extends RESTAbstractModel<VehicleType> {

    private String name;

    public RESTVehicleType() {
    }

    public RESTVehicleType(VehicleType vehicleType){
        super(vehicleType.getUuid(), getProperty(PATH_VEHICLES) + "/" + getProperty(PATH_TYPES));
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
