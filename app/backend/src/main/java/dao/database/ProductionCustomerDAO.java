package dao.database;

import dao.interfaces.CustomerDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.fleet.Fleet;
import model.identity.Address;
import model.identity.Customer;
import org.hibernate.Hibernate;
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

    public ProductionCustomerDAO(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Customer get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            Customer customer=session.get(Customer.class, id);
            if(customer!=null) {
                Hibernate.initialize(customer.getAddress());
            }else{
                throw new DataAccessException();//Todo use correct exception
            }
            return customer;
        }
    }

    @Override
    public Customer create(String name, Address address, String phonenumber, String btwNumber) throws DataAccessException {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setBtwNumber(btwNumber);
        HibernateUtil.create(factory, customer);
        return customer;
    }

    @Override
    public Customer update(UUID id,String name, Address address, String phonenumber, String btwNumber) throws DataAccessException {
        Customer customer = new Customer();
        customer.setUuid(id);
        customer.setName(name);
        customer.setAddress(address);
        customer.setPhoneNumber(phonenumber);
        customer.setBtwNumber(btwNumber);
        HibernateUtil.update(factory, customer);
        return customer;
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory, get(id));
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
                filter.filter();
            }
            Collection<Customer> customers = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            for(Customer customer:customers){
                if(customer!=null) {
                    Hibernate.initialize(customer.getAddress());
                }
            }
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
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
    public Customer create(Customer customer) {
        return null;
    }

    @Override
    public Customer update(Customer customer) {
        return null;
    }


    @Override
    public Filter<Customer> containsFleet(Fleet fleet) {
        return null;
    }

    @Override
    public Filter<Customer> byName(String name) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("name"), name));
    }

    @Override
    public Filter<Customer> containsName(String name) {
        return () ->
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));

        }

        @Override
        public Filter<Customer> byVatNumber (String vatNumber){
            return () ->
                predicates.add(criteriaBuilder.equal(root.get("btwNumber"), vatNumber));
        }

        @Override
        public Filter<Customer> byPhoneNumber (String phoneNumber){
            return () ->
                predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
        }

        @Override
        public Filter<Customer> byAddress (Address address){
            return () ->
                predicates.add(criteriaBuilder.equal(root.get("address"), address));
        }

        @Override
        public Filter<Customer> byBankAccountNummber (String bankAccountNumber){
            return () ->
                predicates.add(criteriaBuilder.equal(root.get("bankAccountNumber"), bankAccountNumber));
        }

        @Override
        public Filter<Customer> byEmail (String email){
            return () ->
                predicates.add(criteriaBuilder.equal(root.get("email"), email));
        }
    }
