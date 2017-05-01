package dao.database;

import dao.exceptions.DataAccessException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Util class to communicate with the database using Hibernate
 * Created by sam on 3/13/17.
 */
public class HibernateUtil {
    /**
     * Using Hibernate make the given object persistent in the database
     * @param session The Session to use
     * @param objectToSave The object to save
     * @throws DataAccessException Thrown when constraints are violated or session
     */
    public synchronized static void create(Session session, Object objectToSave) throws DataAccessException {
        Transaction transaction = null;
        try  {
            transaction = session.beginTransaction();
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
     * @param session       The Session to use
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
}
