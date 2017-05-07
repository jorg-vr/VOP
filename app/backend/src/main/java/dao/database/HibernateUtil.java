package dao.database;

import dao.database.util.unique.ConstraintValidatorFactoryImpl;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
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

    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private static ValidatorContext validatorContext = validatorFactory.usingContext();


    /**
     * Using Hibernate make the given object persistent in the database
     *
     * @param session      The Session to use
     * @param objectToSave The object to save
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void create(Session session, Object objectToSave) throws DataAccessException, ConstraintViolationException {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            validate(session,objectToSave);
            session.save(objectToSave);
            transaction.commit();
        } catch (ConstraintViolationException e){
            throw e;
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
            throw new DataAccessException();
        }
    }


    /**
     * Using Hibernate update the given object in the database
     *
     * @param session        The Session to use
     * @param objectToUpdate The object to update
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void update(Session session, Object objectToUpdate) throws DataAccessException, ConstraintViolationException {
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            validate(session,objectToUpdate);
            session.merge(objectToUpdate);
            tx.commit();
        } catch(ConstraintViolationException e){
            throw e;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
            throw new DataAccessException();
        }
    }

    private synchronized static void validate(Session session, Object object) throws ConstraintViolationException {
        Map<String, String> map = new HashMap<>();
        validatorContext.constraintValidatorFactory(
                new ConstraintValidatorFactoryImpl
                        (session));
        Validator validator = validatorContext.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);

        for (ConstraintViolation<Object> violation : violations) {
            map.put(violation.getPropertyPath().toString(), violation.getMessage());
        }
        if(map.size()>0){
            throw new ConstraintViolationException(map);
        }
    }
}
