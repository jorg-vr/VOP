package dao.interfaces;

import model.fleet.VehicleType;
import model.insurance.SuretyTax;
import model.insurance.SuretyType;

/**
 * Created by jorg on 4/8/17.
 */
public interface SuretyTaxAndCommissionDAO extends DAO<SuretyTax> {
    /**
     * @param vehicleType
     * @param suretyType
     * @return the surety that is uniquely defined by vehicle- and suretyType
     */
    SuretyTax get(VehicleType vehicleType, SuretyType suretyType);
}
