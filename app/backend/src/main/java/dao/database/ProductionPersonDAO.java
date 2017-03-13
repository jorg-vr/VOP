package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.PersonDAO;
import model.identity.Address;
import model.identity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionPersonDAO implements PersonDAO {

    private final SessionFactory factory;
    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<Person> root;
    private CriteriaQuery<Person> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    public ProductionPersonDAO(SessionFactory factory){
        this.factory = factory;
    }

    @Override
    public Person create(Person person) throws DataAccessException {
        HibernateUtil.create(factory,person);
        return person;
    }

    @Override
    public Person get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Person.class, id);
        }
    }

    @Override
    public void update(Person person) throws DataAccessException {
        HibernateUtil.update(factory,person);
    }

    @Override
    public void remove(Person person) throws DataAccessException {
        HibernateUtil.remove(factory,person);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Person create(String firstName, String lastName, String email) throws DataAccessException {
        return create(firstName,lastName,email,null,null);
    }

    @Override
    public Person create(String firstName, String lastName, String email, String phonenumber, Address address) throws DataAccessException {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phonenumber);
        person.setAddress(address);
        HibernateUtil.create(factory,person);
        return person;
    }

    @Override
    public Person update(UUID id, String firstName, String lastName) throws DataAccessException {
        return update(id,firstName,lastName,null,null,null);
    }

    @Override
    public Person update(UUID id, String firstName, String lastName, String email, String phonenumber, Address address) throws DataAccessException {
        Person person = new Person();
        person.setUuid(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phonenumber);
        person.setAddress(address);
        HibernateUtil.update(factory,person);
        return person;
    }

    @Override
    public Collection<Person> listFiltered(Filter<Person>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Person.class);
            this.root = this.criteriaQuery.from(Person.class);
            for (Filter<Person> filter : filters) {
                filter.filter(null);
            }
            Collection<Person> persons = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            return persons;
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
    public Filter<Person> byAddress(Address address) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("address"), address));
            return true;
        };
    }

    @Override
    public Filter<Person> byBankAccountNummber(String bankAccountNumber) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("bankAccountNumber"), bankAccountNumber));
            return true;
        };
    }

    @Override
    public Filter<Person> byEmail(String email) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
            return true;
        };
    }

    @Override
    public Filter<Person> nameContains(String name) {
        return (o1) -> {
            Expression<String> exp1 = criteriaBuilder.concat(root.<String>get("firstName"), " ");
            exp1 = criteriaBuilder.concat(exp1, root.<String>get("surname"));
            Expression<String> exp2 = criteriaBuilder.concat(root.<String>get("lastName"), " ");
            exp2 = criteriaBuilder.concat(exp2, root.<String>get("name"));
            predicates.add(criteriaBuilder.or(criteriaBuilder.like(exp1, "%"+ name +"%"), criteriaBuilder.like(exp2, "%"+ name +"%")));
            return true;
        };
    }


}