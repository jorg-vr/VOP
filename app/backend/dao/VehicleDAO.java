package dao;

import model.Insurance;
import model.Vehicle;

import java.util.Collection;

/**
 * Created by sam on 2/26/17.
 */
public interface VehicleDAO {

    public Collection<Vehicle> listFiltered(Filter... filters);
}
