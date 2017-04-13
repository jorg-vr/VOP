package dao.interfaces;

import model.fleet.VehicleType;
import model.insurance.Surety;
import model.insurance.SuretyTax;
import model.insurance.SuretyType;

/**
 * Created by sam on 4/12/17.
 */
public interface SuretyTaxDAO extends DAO<SuretyTax> {
    SuretyTax get(SuretyType suretyType, VehicleType vehicleType);
}
