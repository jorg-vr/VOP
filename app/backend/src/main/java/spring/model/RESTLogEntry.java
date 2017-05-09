package spring.model;

import model.history.Description;
import model.history.LogAction;
import model.history.LogEntry;
import model.history.LogResource;

import java.time.LocalDateTime;
import java.util.Collection;

import static util.UUIDUtil.UUIDToNumberString;

/**
 * @author Billie Devolder
 */
public class RESTLogEntry {

    private String id;
    private String object;
    private String user;
    private LogResource resource;
    private LogAction action;
    private Collection<Description> description;
    private LocalDateTime dateTime;


    public RESTLogEntry(LogEntry entry) {
        this.id = UUIDToNumberString(entry.getUuid());
        this.object = UUIDToNumberString(entry.getObject());
        this.user = UUIDToNumberString(entry.getObject());
        this.resource = entry.getResource();
        this.action = entry.getAction();
        this.description = entry.getDescriptions();
        this.dateTime = entry.getTime();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public LogResource getResource() {
        return resource;
    }

    public void setResource(LogResource resource) {
        this.resource = resource;
    }

    public LogAction getAction() {
        return action;
    }

    public void setAction(LogAction action) {
        this.action = action;
    }

    public Collection<Description> getDescription() {
        return description;
    }

    public void setDescription(Collection<Description> description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
