package model.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import model.account.User;


public interface EditableObject {

    UUID getUuid();

    EditableObject copy();

    default Collection<LogEntry> logGet(User user) {
       return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.GET);
    }

    default Collection<LogEntry> logCreate(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.CREATE);

    }

    default Collection<LogEntry> logDelete(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.DELETE);
    }

    /**
     * TODO use generics
     */
    default Collection<LogEntry> logUpdate(User user, Object old) {
        return new ArrayList<>();
    }
}
