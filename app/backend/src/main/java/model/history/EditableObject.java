package model.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import model.account.User;


public interface EditableObject {

    UUID getUuid();

    EditableObject copy();

    default Collection<LogEntry> logCreate(User user) {
        Collection<LogEntry> entries = new ArrayList<>();
        LogEntry entry = new LogEntry(getUuid(), user, LogAction.CREATE);
        entries.add(entry);
        return entries;
    }
}
