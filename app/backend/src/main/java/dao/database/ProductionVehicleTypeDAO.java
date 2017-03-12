package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleTypeDao;
import model.fleet.VehicleType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleTypeDAO implements VehicleTypeDao {

    private final SessionFactory factory;

    public ProductionVehicleTypeDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public VehicleType create(VehicleType vehicleType) throws DataAccessException {
        Transaction transaction = null;
        try (Session session = factory.openSession()){
            transaction = session.beginTransaction();
            session.save(vehicleType);
            transaction.commit();
            return vehicleType;
        }
    }

    @Override
    public VehicleType get(UUID id) throws DataAccessException {
        return null;
    }

    @Override
    public void update(VehicleType vehicleType) throws DataAccessException {

    }

    @Override
    public void remove(VehicleType vehicleType) throws DataAccessException {

    }

    @Override
    public Collection<VehicleType> listFiltered(Filter[] filters) throws DataAccessException {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public Filter<VehicleType> byName(String name) {
        return null;
    }

    @Override
    public Filter<VehicleType> nameContains(String name) {
        return null;
    }
}
