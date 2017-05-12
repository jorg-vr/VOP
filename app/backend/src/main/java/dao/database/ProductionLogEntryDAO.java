package dao.database;

import dao.exceptions.DataAccessException;
import dao.interfaces.LogEntryDAO;
import model.account.User;
import model.history.LogEntry;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.rmi.runtime.Log;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 5/11/17.
 */
public class ProductionLogEntryDAO extends ProductionDAO<LogEntry> implements LogEntryDAO{

    public ProductionLogEntryDAO(Session session) {
        super(session, LogEntry.class);
    }

    @Override
    public Collection<LogEntry> getAllLogs(UUID id) {
        Transaction tx = null;
        try {
            tx = getSession().beginTransaction();
            CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
            CriteriaQuery<LogEntry> criteriaQuery = criteriaBuilder.createQuery(LogEntry.class);
            Root<LogEntry> root = criteriaQuery.from(LogEntry.class);
            Collection<LogEntry> entries = getSession().createQuery(
                    criteriaQuery.where(
                            criteriaBuilder.or(
                            criteriaBuilder.equal(root.get("object"),id),
                            criteriaBuilder.isMember(id,root.get("interested"))))).getResultList();
            tx.commit();
            return entries;
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }
}
