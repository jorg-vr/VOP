package model.history;

import model.account.User;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * @author Billie Devolder
 */
public class LogEntry {

    private UUID object;

    // User that created/retrieved/updated/deleted the object
    private User user;

    // The time when the LogEntry was created
    private LocalDateTime time;

    // The type of action that has been logged
    private LogAction action;

    // The type of resource that has been logged
    private LogResource resource;

    // A collection of descriptions of fields that have been changed by the action.
    private Collection<Description> descriptions;

    public LogEntry() {
    }

    public LogEntry(UUID object, User user, LogAction action, LogResource resource) {
        this.object = object;
        this.user = user;
        this.time = LocalDateTime.now();
        this.action = action;
        this.resource = resource;
    }

    public LogEntry(UUID object, User user, LogAction action, LogResource resource, Collection<Description> descriptions) {
        this.object = object;
        this.user = user;
        this.time = LocalDateTime.now();
        this.action = action;
        this.resource = resource;
        this.descriptions = descriptions;
    }

    public UUID getObject() {
        return object;
    }

    public void setObject(UUID object) {
        this.object = object;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public LogAction getAction() {
        return action;
    }

    public void setAction(LogAction action) {
        this.action = action;
    }

    public LogResource getResource() {
        return resource;
    }

    public void setResource(LogResource resource) {
        this.resource = resource;
    }

    public Collection<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Collection<Description> descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "LogEntry{" +
                "object=" + object +
                ", user=" + user +
                ", time=" + time +
                ", action=" + action +
                ", resource=" + resource +
                ", descriptions=" + descriptions +
                '}';
    }

    public static Collection<LogEntry> createSimpleLogCollection(UUID uuid, User user, LogAction action, LogResource resource) {
        Collection<LogEntry> entries = new ArrayList<>();
        LogEntry entry = new LogEntry(uuid, user, action, resource);
        entries.add(entry);
        return entries;
    }
}
