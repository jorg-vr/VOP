package model.history;

import model.account.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public interface EditableObject {

    UUID getUuid();

    EditableObject copy();

    LogResource getLogResource();

    default Collection<LogEntry> logCreate(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.CREATE, getLogResource());

    }

    default Collection<LogEntry> logDelete(User user) {
        return LogEntry.createSimpleLogCollection(getUuid(), user, LogAction.DELETE, getLogResource());
    }

    default Collection<LogEntry> logUpdate(User user, EditableObject old) {
        return new ArrayList<>();
    }


}
