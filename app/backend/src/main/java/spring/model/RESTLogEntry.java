package spring.model;

import model.history.Description;
import model.history.LogAction;
import model.history.LogEntry;
import model.history.LogResource;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static util.UUIDUtil.UUIDToNumberString;

/**
 * @author Billie Devolder
 */
public class RESTLogEntry {

    private String id;
    private String object;
    private RESTUser user;
    private LogResource resource;
    private LogAction action;
    private Collection<RESTDescription> description;
    private LocalDateTime dateTime;


    public RESTLogEntry(LogEntry entry) {
        this.id = UUIDToNumberString(entry.getUuid());
        this.object = UUIDToNumberString(entry.getObject());
        this.user = entry.getUser() != null ? new RESTUser(entry.getUser()) : null;
        this.resource = entry.getResource();
        this.action = entry.getAction();
        this.description = entry.getDescriptions()
                .stream()
                .map(RESTDescription::new)
                .collect(Collectors.toList());
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

    public Collection<RESTDescription> getDescription() {
        return description;
    }

    public void setDescription(Collection<RESTDescription> description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public RESTUser getUser() {
        return user;
    }

    public void setUser(RESTUser user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return dateTime.toString();
    }

    public static class RESTDescription {

        // The name of the field that has been changed
        private String field;

        // String representation of the old value of the field
        private String oldValue;

        // String representation of the new value of the field
        private String newValue;

        public RESTDescription() {
        }

        public RESTDescription(Description description) {
            this.field = description.getField();
            this.oldValue = description.getOldValue();
            this.newValue = description.getNewValue();
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getOldValue() {
            return oldValue;
        }

        public void setOldValue(String oldValue) {
            this.oldValue = oldValue;
        }

        public String getNewValue() {
            return newValue;
        }

        public void setNewValue(String newValue) {
            this.newValue = newValue;
        }


    }
}
