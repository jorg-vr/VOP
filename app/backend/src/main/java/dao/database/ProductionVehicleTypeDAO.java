package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleTypeDao;
import model.fleet.VehicleType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleTypeDAO extends ProductionDAO<VehicleType> implements VehicleTypeDao {

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
