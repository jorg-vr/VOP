package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import model.identity.Customer;
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
 * Created by sam on 3/13/17.
 */
public class ProductionFleetDAO implements FleetDAO{

    private final SessionFactory factory;
    private CriteriaQuery<Fleet> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Fleet> root;
    private Collection<Predicate> predicates = new ArrayList<>();


    public ProductionFleetDAO(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Fleet create(String name, Customer customer, Collection<Vehicle> vehicles) throws DataAccessException {
        System.out.println("OWNER = " + customer);
        Fleet fleet = new Fleet();
        //fleet.setName(name);
        fleet.setOwner(customer);
        if (vehicles == null) {
            vehicles = new ArrayList<>();
        }
        fleet.setVehicles(vehicles);
        HibernateUtil.create(factory,fleet);
        return fleet;
    }

    @Override
    public Fleet update(UUID id, String name, Customer customer) throws DataAccessException {
        Fleet fleet = new Fleet();
        //fleet.setName(name);
        fleet.setUuid(id);
        fleet.setOwner(customer);
        HibernateUtil.update(factory,fleet);
        return null;
    }

    @Override
    public Filter<Fleet> byOwner(Customer customer) {
        return null;
    }

    @Override
    public Fleet get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Fleet.class, id);
        }
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Fleet> listFiltered(Filter<Fleet>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Fleet.class);
            this.root = this.criteriaQuery.from(Fleet.class);
            for (Filter<Fleet> filter : filters) {
                filter.filter();
            }
            Collection<Fleet> fleets = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
            return fleets;
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
    public void close() {

    }
}
