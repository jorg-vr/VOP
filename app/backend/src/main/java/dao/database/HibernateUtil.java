package dao.database;

import dao.interfaces.DataAccessException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Created by sam on 3/13/17.
 */
public class HibernateUtil {
    public synchronized static void create(SessionFactory factory, Object objectToSave) throws DataAccessException {
        Transaction transaction = null;
        try (Session session = factory.openSession()) {
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

    public synchronized static void remove(SessionFactory factory, Object objectToRemove) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession();) {
            tx = session.beginTransaction();
            session.delete(objectToRemove);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    public synchronized static void update(SessionFactory factory, Object objectToUpdate) throws DataAccessException {
        Transaction tx = null;
        try (Session session = factory.openSession();) {
            tx = session.beginTransaction();
            session.update(objectToUpdate);
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
