package controller;

import dao.interfaces.DAOManager;
import model.account.Function;
import model.account.Role;
import model.history.LogEntry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Billie Devolder
 */
public class LogEntryController {

    private Role role;

    public LogEntryController(Function function, DAOManager manager) {
        this.role = function.getRole();
    }

    /**
     * Get all the log entries that are relevant to the object with uuid objectId
     * @param objectId uuid of the object which all the relevant log entries should be returned
     * @return a collection of all the log entries. The collection is empty if there are nog log entries for that uuid.
     */
    public Collection<LogEntry> getLogEntries(UUID objectId) {
        Collection<LogEntry> entries = new ArrayList<>();
        return new ArrayList<>();
    }
}
