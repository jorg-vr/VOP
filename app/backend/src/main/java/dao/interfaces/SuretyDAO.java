package dao.interfaces;

import model.fleet.VehicleType;
import model.insurance.Surety;
import model.insurance.SuretyType;

/**
 * Created by jorg on 4/8/17.
 */
public interface SuretyDAO extends DAO<Surety> {
    /**
     * @param vehicleType
     * @param suretyType
     * @return the surety that is uniquely defined by vehicle- and suretyType
     */
    Surety get(VehicleType vehicleType, SuretyType suretyType);
}
