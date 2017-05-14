package dao.interfaces;

import model.history.LogEntry;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 5/11/17.
 */
public interface LogEntryDAO extends DAO<LogEntry> {

    Collection<LogEntry> getAllLogs(UUID id);
}
