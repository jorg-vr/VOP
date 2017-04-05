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
public class ProductionAccountDAO extends ProductionDAO<Account> implements AccountDAO {


    public ProductionAccountDAO(Session session) {
        super(session,Account.class);
    }


    @Override
    public Filter<Account> bySecurity(String login, String password) {
        return () -> {
            getPredicates().add(getCriteriaBuilder().equal(getRoot().get("login"), login));
            getPredicates().add(getCriteriaBuilder().equal(getRoot().get("hashedPassword"), password));

        };
    }

    @Override
    public Filter<Account> byPerson(Person identity) {
        return () -> {
            getPredicates().add(getCriteriaBuilder().equal(getRoot().get("person"), identity));
        };
    }
}
