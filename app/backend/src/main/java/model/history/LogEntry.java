package model.history;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import model.account.Action;
import model.account.User;

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

    // A collection of descriptions of fields that have been changed by the action.
    private Collection<Description> descriptions;

    public LogEntry() {
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

    public Collection<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(Collection<Description> descriptions) {
        this.descriptions = descriptions;
    }
}
