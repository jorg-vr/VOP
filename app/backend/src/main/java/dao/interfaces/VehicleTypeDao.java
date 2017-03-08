package dao.interfaces;

import model.fleet.VehicleType;

/**
 * Created by sam on 3/7/17.
 */
public interface VehicleTypeDao extends DAO<VehicleType>{
    Filter<VehicleType> byName(String name);
    Filter<VehicleType> nameContains(String name);
}
