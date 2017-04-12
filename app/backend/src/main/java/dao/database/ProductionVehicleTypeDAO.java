package dao.database;

import dao.interfaces.Filter;
import dao.interfaces.VehicleTypeDAO;
import model.fleet.VehicleType;
import org.hibernate.Session;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleTypeDAO extends ProductionDAO<VehicleType> implements VehicleTypeDAO {

    public ProductionVehicleTypeDAO(Session session) {
        super(session,VehicleType.class);
    }

    @Override
    public Filter<VehicleType> byName(String name) {
        return filterEqual("type",name);
    }

    @Override
    public Filter<VehicleType> nameContains(String name) {
        return filterContains("type",name);
    }
}
