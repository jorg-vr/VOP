package dao.database;

import dao.database.util.unique.ConstraintValidatorFactoryImpl;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import dao.interfaces.UserDAO;
import model.account.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.validation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Util class to communicate with the database using Hibernate
 * Created by sam on 3/13/17.
 */
public class HibernateUtil {
    /**
     * Using Hibernate make the given object persistent in the database
     *
     * @param session      The Session to use
     * @param objectToSave The object to save
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void create(Session session, Object objectToSave) throws DataAccessException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            //validate(session,objectToSave);
            session.save(objectToSave);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataAccessException();
        }
    }

    /**
     * Using Hibernate remove the given object from the database
     *
     * @param session        The Session to use
     * @param objectToRemove The object to remove
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void remove(Session session, Object objectToRemove) throws DataAccessException {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(objectToRemove);
            tx.commit();
        } catch (Exception e) {

            e.printStackTrace();
            if (tx != null) {
                tx.rollback();
            }
        }
    }


    /**
     * Using Hibernate update the given object in the database
     *
     * @param session        The Session to use
     * @param objectToUpdate The object to update
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void update(Session session, Object objectToUpdate) throws DataAccessException {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.merge(objectToUpdate);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    private synchronized static Map<String, String> validate(Session session, Object object) {
        Map<String, String> map = new HashMap<>();
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        ValidatorContext validatorContext = validatorFactory.usingContext();
        validatorContext.constraintValidatorFactory(
                new ConstraintValidatorFactoryImpl
                        (session));
        Validator validator = validatorContext.getValidator();


        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        for (ConstraintViolation<Object> violation : violations) {
            map.put(violation.getPropertyPath().toString(), violation.getMessage());
            System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
        }
//        throw new RuntimeException();
        return map;
    }

    public static void main(String[] args) {
        ProductionProvider.initializeProvider("unittest");
        try (DAOProvider provider = ProductionProvider.getInstance();
             DAOManager daoManager = provider.getDaoManager()) {
            UserDAO userDAO = daoManager.getUserDAO();
            User user = new User();
            user.setFirstName("test");
            user.setLastName("test");
            user.setEmail("test,test");
            userDAO.create(user);
            //userDAO.remove(user.getUuid());
            User user1 = new User();
            user1.setFirstName("test");
            user1.setLastName("test");
            user1.setEmail("test,test");
            userDAO.create(user1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
