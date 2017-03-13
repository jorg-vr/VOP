package dao.interfaces;

import model.fleet.VehicleType;

import java.util.UUID;

/**
 * Created by sam on 3/7/17.
 */
public interface VehicleTypeDao extends DAO<VehicleType> {
    VehicleType create(String type, double tax) throws DataAccessException;

    VehicleType update(UUID uuid, String type, double tax) throws DataAccessException;

    Filter<VehicleType> byName(String name);

    Filter<VehicleType> nameContains(String name);
}
