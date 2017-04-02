package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
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

    private final Session session;

    private CriteriaQuery<Address> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    private Root<Address> root;
    private Collection<Predicate> predicates = new ArrayList<>();

    public ProductionAddressDAO(Session session) {
        this.session = session;
    }

    @Override
    public Address create(Address address) throws DataAccessException {
        HibernateUtil.create(session, address);
        return address;
    }

    @Override
    public Address update(Address address) throws DataAccessException {
        HibernateUtil.update(session, address);
        return address;
    }

    @Override
    public Address get(UUID id) throws DataAccessException {

        return session.get(Address.class, id);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(session, get(id));
    }

    @Override
    public Collection<Address> listFiltered(Filter<Address>[] filters) throws DataAccessException {
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Address.class);
            this.root = this.criteriaQuery.from(Address.class);
            for (Filter<Address> filter : filters) {
                filter.filter();
            }
            Collection<Address> fleets = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
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
    public Address create(String street, String streetNumber, String town, String postalCode, String country) throws DataAccessException {
        Address address = new Address();
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setTown(town);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        HibernateUtil.create(session, address);
        return address;
    }

    @Override
    public Address update(UUID id, String street, String streetNumber, String town, String postalCode, String
            country) throws DataAccessException {
        Address address = new Address();
        address.setUuid(id);
        address.setStreet(street);
        address.setStreetNumber(streetNumber);
        address.setTown(town);
        address.setPostalCode(postalCode);
        address.setCountry(country);
        HibernateUtil.update(session, address);
        return address;
    }

    @Override
    public Filter<Address> byStreet(String street) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("street"), street));
    }

    @Override
    public Filter<Address> byStreetNumber(String streetNumber) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("streetNumber"), streetNumber));
    }

    @Override
    public Filter<Address> byTown(String town) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("town"), town));
    }

    @Override
    public Filter<Address> byPostalCode(String postalCode) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("postalCode"), postalCode));
    }

    @Override
    public Filter<Address> byCountry(String country) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("country"), country));
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
