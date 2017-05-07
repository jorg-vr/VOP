package model.history;

import model.account.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public interface EditableObject<T> {

    UUID getUuid();

    T copy();

    LogResource getLogResource();

    default Collection<LogEntry> logGet(User user) {
       return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.GET);
    }

    default Collection<LogEntry> logCreate(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.CREATE);

    }

    default Collection<LogEntry> logDelete(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.DELETE);
    }

    default Collection<LogEntry> logUpdate(User user, T old) {
        return new ArrayList<>();
    }
}
