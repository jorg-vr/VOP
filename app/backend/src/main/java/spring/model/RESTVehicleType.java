package spring.model;

import controller.ControllerManager;
import model.account.Function;
import model.fleet.VehicleType;
import model.insurance.SuretyType;
import util.UUIDUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static util.MyProperties.PATH_TYPES;
import static util.MyProperties.PATH_VEHICLES;
import static util.MyProperties.getProperty;

/**
 * Created by jorg on 3/14/17.
 */
public class RESTVehicleType extends RESTAbstractModel<VehicleType> {

    private String name;
    private Collection<RESTTax> taxes;

    public RESTVehicleType() {
    }

    public RESTVehicleType(VehicleType vehicleType){
        super(vehicleType.getUuid(), getProperty(PATH_VEHICLES) + "/" + getProperty(PATH_TYPES));
        setName(vehicleType.getType());
        taxes=new ArrayList<>();
        for(SuretyType suretyType: vehicleType.getTaxes().keySet()){
            taxes.add(new RESTTax(vehicleType.getTaxes().get(suretyType),suretyType));
        }
    }

    @Override
    public VehicleType translate(ControllerManager manager) {
        VehicleType vehicleType=new VehicleType();
        vehicleType.setUuid(UUIDUtil.toUUID(getId()));
        vehicleType.setType(getName());
        Map<SuretyType,Double> map=new HashMap<>();
        for(RESTTax tax:taxes){
            map.put(tax.getSuretyType(),tax.getTax());
        }
        vehicleType.setTaxes(map);
        return vehicleType;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<RESTTax> getTaxes() {
        return taxes;
    }

    public void setTaxes(Collection<RESTTax> taxes) {
        this.taxes = taxes;
    }
}
