package dao.interfaces;

import model.fleet.VehicleType;
import model.insurance.SuretyTaxAndCommission;
import model.insurance.SuretyType;

/**
 * Created by jorg on 4/8/17.
 */
public interface SuretyTaxAndCommissionDAO extends DAO<SuretyTaxAndCommission> {
    /**
     * @param vehicleType
     * @param suretyType
     * @return the surety that is uniquely defined by vehicle- and suretyType
     */
    SuretyTaxAndCommission get(VehicleType vehicleType, SuretyType suretyType);
}
