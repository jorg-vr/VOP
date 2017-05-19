package dao.interfaces;

import model.history.LogEntry;

import java.util.Collection;
import java.util.UUID;

/**
 * DAO for the bean LogEntry
 * Created by sam on 5/11/17.
 */
public interface LogEntryDAO extends DAO<LogEntry> {

    /**
     * Gets all LogEntries where the object of the logentry had the given id or the id is in the interested collection
     * @param id the id
     * @return all logs for the given id
     */
    Collection<LogEntry> getAllLogs(UUID id);
}
