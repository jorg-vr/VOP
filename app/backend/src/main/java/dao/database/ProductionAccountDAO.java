package dao.database;

import dao.interfaces.AccountDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Account;
import model.fleet.VehicleType;
import model.identity.Person;
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
public class ProductionAccountDAO implements AccountDAO {

    private final SessionFactory factory;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Account> criteriaQuery;
    private Root<Account> root;
    private Collection<Predicate> predicates = new ArrayList<>();

    public ProductionAccountDAO(SessionFactory sessionFactory) {
        this.factory = sessionFactory;
    }

    @Override
    public Account create(Account account) throws DataAccessException {
        HibernateUtil.create(factory,account);
        return account;
    }

    @Override
    public Account get(UUID id) throws DataAccessException {
        try (Session session = factory.openSession()) {
            return session.get(Account.class, id);
        }
    }

    @Override
    public void update(Account account) throws DataAccessException {
        HibernateUtil.update(factory,account);
    }

    @Override
    public void remove(Account account) throws DataAccessException {
        HibernateUtil.remove(factory,account);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(factory,get(id));
    }

    @Override
    public Collection<Account> listFiltered(Filter<Account>[] filters) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession()) {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Account.class);
            this.root = this.criteriaQuery.from(Account.class);
            for (Filter<Account> filter : filters) {
                filter.filter(null);
            }
            Collection<Account> types = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            tx.commit();
            this.root = null;
            this.criteriaQuery = null;
            this.criteriaBuilder = null;
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
    public void close() {

    }

    @Override
    public Account create(String login, String hashedPassword, Person person) throws DataAccessException {
        Account account = new Account();
        account.setLogin(login);
        account.setHashedPassword(hashedPassword);
        account.setPerson(person);
        HibernateUtil.create(factory,account);
        return account;
    }

    @Override
    public Account update(UUID id, String hashedPassword) throws DataAccessException {
        Account account = get(id);
        account.setHashedPassword(hashedPassword);
        HibernateUtil.update(factory,account);
        return account;
    }

    @Override
    public Account update(UUID id, String login, String hashedPassword) throws DataAccessException {
        Account account = new Account();
        account.setUuid(id);
        account.setLogin(login);
        account.setHashedPassword(hashedPassword);
        HibernateUtil.update(factory,account);
        return account;
    }

    @Override
    public Filter<Account> bySecurity(String login, String password) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("login"), login));
            //TODO unhash password
            predicates.add(criteriaBuilder.equal(root.get("hashedPassword"), password));
            return true;
        };
    }

    @Override
    public Filter<Account> byPerson(Person identity) {
        return (o1) -> {
            predicates.add(criteriaBuilder.equal(root.get("person"), identity));
            return true;
        };
    }
}
