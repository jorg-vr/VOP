package dao.interfaces;

import model.fleet.VehicleType;
import model.identity.Customer;
import model.insurance.SuretyCommision;
import model.insurance.SuretyType;

/**
 * Created by sam on 4/12/17.
 */
public interface SuretyCommisionDAO extends DAO<SuretyCommision> {
    SuretyCommision get(SuretyType suretyType, VehicleType vehicleType, Customer customer);
}
