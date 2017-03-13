package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.fleet.Fleet;
import model.identity.Address;
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
public class ProductionAddressDAO implements AddressDAO {

    private final SessionFactory factory;

    private CriteriaQuery<Address> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Address> root;
    private Collection<Predicate> predicates = new ArrayList<>();
    public ProductionAddressDAO(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Address create(Address address) throws DataAccessException {
        HibernateUtil.create(factory,address);
        return address;
    }

    @Override
    public Address get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Address.class, id);
        }
    }

    @Override
    public void update(Address address) throws DataAccessException {
        HibernateUtil.update(factory,address);
    }

    @Override
    public void remove(Address address) throws DataAccessException {
        HibernateUtil.remove(factory,address);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Address> listFiltered(Filter<Address>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Address.class);
            this.root = this.criteriaQuery.from(Address.class);
            for (Filter<Address> filter : filters) {
                filter.filter(null);
            }
            Collection<Address> fleets = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
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

    @Override
    public Address create(String street, String streetNumber, String town, String postalCode, String country) throws DataAccessException {
        Address address = new Address();
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setTown(town);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        HibernateUtil.create(factory,address);
        return address;
    }

    @Override
    public Address update(UUID id, String street, String streetNumber, String town, String postalCode, String country) throws DataAccessException {
        Address address = new Address();
        address.setUuid(id);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setTown(town);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        HibernateUtil.update(factory,address);
        return address;
    }
}
