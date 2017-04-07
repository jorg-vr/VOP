package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.UserDAO;
import model.account.User;
import model.fleet.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;

/**
 * Created by Billie Devolder on 5/04/2017.
 */
public class ProductionUserDAO extends ProductionDAO<User> implements UserDAO {

    public ProductionUserDAO(Session session) {
        super(session, User.class);
    }

    @Override
    public Filter<User> byFirstName(String firstName) {
        return filterContains("firstName", firstName);
    }

    @Override
    public Filter<User> byLastName(String lastName) {
        return filterContains("lastName", lastName);
    }

    @Override
    public Filter<User> byEmail(String email) {
        return filterContains("email", email);
    }

    @Override
    public User getUserByLogin(String login, String hashedPassword) {
        Transaction tx = null;
        try {

            tx = getSession().beginTransaction();
            CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> root = criteriaQuery.from(User.class);
            Collection<User> users = getSession().createQuery(criteriaQuery.where(
                    criteriaBuilder.equal(root.get("email"), login),
                    criteriaBuilder.equal(root.get("password"), hashedPassword))).getResultList();
            tx.commit();
            if(users.size()>1){
                throw new DataAccessException();
            }
            if(users.size()==0) {
                return null;
            }
            for (User user : users) {
                return user;
            }
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
