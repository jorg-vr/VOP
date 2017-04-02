package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.PersonDAO;
import model.account.Account;
import model.identity.Address;
import model.identity.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionPersonDAO implements PersonDAO {

    private final Session session;
    private Collection<Predicate> predicates = new ArrayList<>();
    private Root<Person> root;
    private CriteriaQuery<Person> criteriaQuery;
    private CriteriaBuilder criteriaBuilder;
    public ProductionPersonDAO(Session session){
        this.session = session;
    }

    @Override
    public Person create(Person person) throws DataAccessException {
        HibernateUtil.create(session,person);
        return person;
    }

    @Override
    public Person update(Person person) throws DataAccessException {
        HibernateUtil.update(session,person);
        return person;
    }

    @Override
    public Person get(UUID id) throws DataAccessException {
        return Optional.ofNullable(session.get(Person.class, id)).orElseThrow(DataAccessException::new);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(session,get(id));
    }

    @Override
    public Person create(String firstName, String lastName, String email) throws DataAccessException {
        return create(firstName,lastName,email,null,null);
    }

    public Person create(String firstName, String lastName, String email, String phonenumber, Address address) throws DataAccessException {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(phonenumber);
        person.setAddress(address);
        HibernateUtil.create(session,person);
        return person;
    }

    @Override
    public Person update(UUID id, String firstName, String lastName, String email) throws DataAccessException {
        Person person = new Person();
        person.setUuid(id);
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setEmail(email);
        person.setPhoneNumber(null);
        person.setAddress(null);
        HibernateUtil.update(session,person);
        return person;
    }

    @Override
    public Collection<Person> listFiltered(Filter<Person>[] filters) throws DataAccessException {
        Transaction tx = null;
        try  {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Person.class);
            this.root = this.criteriaQuery.from(Person.class);
            for (Filter<Person> filter : filters) {
                filter.filter();
            }
            Collection<Person> persons = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
            predicates.clear();
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
    public Filter<Person> byAddress(Address address) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("address"), address));
    }

    @Override
    public Filter<Person> byEmail(String email) {
        return () ->
            predicates.add(criteriaBuilder.equal(root.get("email"), email));
    }

    @Override
    public Filter<Person> byPhoneNumber(String phoneNumber) {
        return () ->
                predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));

    }

    @Override
    public Filter<Person> nameContains(String name) {
        return () -> {
            Expression<String> exp1 = criteriaBuilder.concat(root.<String>get("firstName"), " ");
            exp1 = criteriaBuilder.concat(exp1, root.<String>get("surname"));
            Expression<String> exp2 = criteriaBuilder.concat(root.<String>get("lastName"), " ");
            exp2 = criteriaBuilder.concat(exp2, root.<String>get("name"));
            predicates.add(criteriaBuilder.or(criteriaBuilder.like(exp1, "%"+ name +"%"), criteriaBuilder.like(exp2, "%"+ name +"%")));
        };
    }


    @Override
    public void close() throws Exception {
        session.close();
    }
}
