package dao.database;

import dao.interfaces.AccountDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Account;
import model.identity.Person;
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
import java.util.Optional;
import java.util.UUID;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionAccountDAO implements AccountDAO {

    private final Session session;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Account> criteriaQuery;
    private Root<Account> root;
    private Collection<Predicate> predicates = new ArrayList<>();

    public ProductionAccountDAO(Session session) {
        this.session = session;
    }

    @Override
    public Account create(Account account) throws DataAccessException {
        HibernateUtil.create(session,account);
        return account;
    }

    @Override
    public Account update(Account account) throws DataAccessException {
        HibernateUtil.update(session,account);
        return account;
    }

    @Override
    public Account get(UUID id) throws DataAccessException {
        return Optional.ofNullable(session.get(Account.class, id)).orElseThrow(DataAccessException::new);
    }

    @Override
    public void remove(UUID id) throws DataAccessException {
        HibernateUtil.remove(session, get(id));
    }

    @Override
    public Collection<Account> listFiltered(Filter<Account>[] filters) throws DataAccessException {
        Transaction tx = null;
        try {

            tx = session.beginTransaction();
            this.criteriaBuilder = session.getCriteriaBuilder();
            this.criteriaQuery = this.criteriaBuilder.createQuery(Account.class);
            this.root = this.criteriaQuery.from(Account.class);
            for (Filter<Account> filter : filters) {
                filter.filter();
            }
            Collection<Account> types = session.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]))).getResultList();
            for(Account account:types){
                if (account!=null) {
                    Hibernate.initialize(account.getPerson());
                }
            }
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
    public Account create(String login, String hashedPassword, Person person) throws DataAccessException {
        Account account = new Account();
        account.setLogin(login);
        account.setHashedPassword(hashedPassword);
        account.setPerson(person);
        HibernateUtil.create(session, account);
        return account;
    }


    @Override
    public Account update(UUID id, String login, String hashedPassword) throws DataAccessException {
        Account account = new Account();
        account.setUuid(id);
        account.setLogin(login);
        account.setHashedPassword(hashedPassword);
        account.setPerson(get(id).getPerson());//TODO evaluate this
        HibernateUtil.update(session, account);
        return account;
    }

    @Override
    public Filter<Account> bySecurity(String login, String password) {
        return () -> {
            predicates.add(criteriaBuilder.equal(root.get("login"), login));
            predicates.add(criteriaBuilder.equal(root.get("hashedPassword"), password));

        };
    }

    @Override
    public Filter<Account> byPerson(Person identity) {
        return () -> {
            predicates.add(criteriaBuilder.equal(root.get("person"), identity));
        };
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
