package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.FleetDAO;
import model.fleet.Fleet;
import model.identity.Customer;
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
 * Created by sam on 3/13/17.
 */
public class ProductionFleetDAO implements FleetDAO {

    private final Session session;
    private CriteriaQuery<Fleet> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Fleet> root;
    private Collection<Predicate> predicates = new ArrayList<>();


    public ProductionFleetDAO(Session session) {
        this.session = session;
    }

    @Override
    public Fleet create(String name, Customer customer) throws DataAccessException {
        Fleet fleet = new Fleet();
        fleet.setName(name);
        fleet.setOwner(customer);
        HibernateUtil.create(session, fleet);
        return fleet;
    }

    @Override
    public Fleet update(UUID id, String name, Customer customer) throws DataAccessException {
        Fleet fleet = get(id);
        fleet.setName(name);
        fleet.setOwner(customer);
        HibernateUtil.update(session, fleet);
        return fleet;
    }

    @Override
    public Fleet create(Fleet fleet) throws DataAccessException {
        HibernateUtil.create(session, fleet);
        return fleet;
    }

    @Override
    public Fleet update(Fleet fleet) throws DataAccessException {
        HibernateUtil.update(session, fleet);
        return fleet;
    }

    @Override
    public Fleet get(UUID id) throws DataAccessException {
        return Optional.ofNullable(session.get(Fleet.class, id)).orElseThrow(DataAccessException::new);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(session, get(id));
    }

    @Override
    public Collection<Fleet> listFiltered(Filter<Fleet>[] filters) throws DataAccessException {
        Transaction tx = null;
        try {

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
    public void close() throws Exception {
        session.close();
    }

}
