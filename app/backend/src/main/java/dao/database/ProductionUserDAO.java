package dao.database;

import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.interfaces.UserDAO;
import model.account.User;
import model.fleet.Vehicle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.Predicate;
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
            Collection<User> users = getSession().createQuery(getCriteriaQuery().where(
                    getCriteriaBuilder().equal(getRoot().get("login"), login),
                    getCriteriaBuilder().equal(getRoot().get("hashedPassword"), hashedPassword))).getResultList();
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
