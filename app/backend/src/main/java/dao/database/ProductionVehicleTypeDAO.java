package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.VehicleTypeDao;
import model.fleet.VehicleType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionVehicleTypeDAO implements VehicleTypeDao {

    private final SessionFactory factory;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<VehicleType> criteriaQuery;
    private Root<VehicleType> root;
    private Collection<Predicate> predicates = new ArrayList<>();

    public ProductionVehicleTypeDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public VehicleType get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(VehicleType.class, id);
        }
    }

    @Override
    public VehicleType create(String type, double tax) throws DataAccessException {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setType(type);
        vehicleType.setTax(tax);
        HibernateUtil.create(factory,vehicleType);
        return vehicleType;
    }

    @Override
    public VehicleType update(UUID uuid, String type, double tax) throws DataAccessException {
        VehicleType vehicleType = new VehicleType();
        vehicleType.setUuid(uuid);
        vehicleType.setType(type);
        vehicleType.setTax(tax);
        HibernateUtil.update(factory,vehicleType);
        return vehicleType;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<VehicleType> listFiltered(Filter<VehicleType>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(VehicleType.class);
            this.root = this.criteriaQuery.from(VehicleType.class);
            for (Filter<VehicleType> filter : filters) {
                filter.filter();
            }
            Collection<VehicleType> types = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
            return types;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public VehicleType create(VehicleType vehicleType) {
        return null;
    }

    @Override
    public VehicleType update(VehicleType vehicleType) {
        return null;
    }


    @Override
    public Filter<VehicleType> byName(String name) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("type"), name));
    }

    @Override
    public Filter<VehicleType> nameContains(String name) {
        return () ->
            predicates.add(criteriaBuilder.like(root.get("type"), "%"+ name + "%"));
    }
}
