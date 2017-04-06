package dao.interfaces;

import model.fleet.VehicleType;

import java.util.UUID;

/**
 * DAO for the bean VehicleType
 * Created by sam on 3/7/17.
 */
public interface VehicleTypeDao extends DAO<VehicleType> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all VehicleTypes (only one) matching the given name exactly.
     * @param name The name to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleType> byName(String name);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all VehicleTypes having a name that contains the given name.
     * @param name The name to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<VehicleType> nameContains(String name);
}
