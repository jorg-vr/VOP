package dao.database;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Function;
import model.fleet.Fleet;
import model.identity.Address;
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
public class ProductionCustomerDAO implements CustomerDAO {

    private final SessionFactory factory;
    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<Customer> root;
    private CriteriaQuery<Customer> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;

    public ProductionCustomerDAO(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Customer create(Customer customer) throws DataAccessException {
        HibernateUtil.create(factory,customer);
        return customer;
    }

    @Override
    public Customer get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Customer.class, id);
        }
    }

    @Override
    public Customer create(String name, Address address, String email, String phonenumber, String btwNumber, String bankAccountNumber, Collection<Fleet> fleets) throws DataAccessException {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhoneNumber(phonenumber);
        customer.setBankAccountNumber(bankAccountNumber);
        customer.setBtwNumber(btwNumber);
        customer.setFleets(fleets);
        HibernateUtil.create(factory,customer);
        return customer;
    }

    @Override
    public Customer update(UUID id, String name, Address address, String email, String phonenumber, String btwNumber, String bankAccountNumber, Collection<Fleet> fleets) throws DataAccessException {
        Customer customer = new Customer();
        customer.setUuid(id);
        customer.setName(name);
        customer.setAddress(address);
        customer.setEmail(email);
        customer.setPhoneNumber(phonenumber);
        customer.setBankAccountNumber(bankAccountNumber);
        customer.setBtwNumber(btwNumber);
        customer.setFleets(fleets);
        HibernateUtil.update(factory,customer);
        return customer;
    }


    @Override
    public void update(Customer customer) throws DataAccessException {
        HibernateUtil.update(factory,customer);
    }

    @Override
    public void remove(Customer customer) throws DataAccessException {
        HibernateUtil.remove(factory,customer);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Customer> listFiltered(Filter<Customer>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Customer.class);
            this.root = this.criteriaQuery.from(Customer.class);
            for (Filter<Customer> filter : filters) {
                filter.filter(null);
            }
            Collection<Customer> customers = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            return customers;
        } catch (Exception e) {
            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
        return null;
    }

    @Override
    public void close() {

    }


    @Override
    public Filter<Customer> containsFleet(Fleet fleet) {
        return null;
    }

    @Override
    public Filter<Customer> byName(String name) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("name"), name));
            return true;
        };
    }

    @Override
    public Filter<Customer> containsName(String name) {
        return (o1) -> {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            return true;
        };
    }

    @Override
    public Filter<Customer> byVatNumber(String vatNumber) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("btwNumber"), vatNumber));
            return true;
        };
    }

    @Override
    public Filter<Customer> byPhoneNumber(String phoneNumber) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
            return true;
        };
    }

    @Override
    public Filter<Customer> byAddress(Address address) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("address"), address));
            return true;
        };
    }

    @Override
    public Filter<Customer> byBankAccountNummber(String bankAccountNumber) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("bankAccountNumber"), bankAccountNumber));
            return true;
        };
    }

    @Override
    public Filter<Customer> byEmail(String email) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
            return true;
        };
    }
}