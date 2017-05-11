package model.history;

import model.account.User;

import java.util.UUID;

import static model.history.FieldsComparator.compareFields;


public interface EditableObject {

    UUID getUuid();

    EditableObject copy();

    LogResource getLogResource();

    /**
     * Create a log entry for the creating of the object
     *
     * @param user the user that created this object
     * @return null if the object does not want to log the creation
     */
    default LogEntry logCreate(User user) {
        return new LogEntry(getUuid(), user, LogAction.CREATE, getLogResource());
    }

    /**
     * Create a log entry for the deletion of the object
     *
     * @param user the user that deleted this object
     * @return null if the object does not want to log the deletion
     */
    default LogEntry logDelete(User user) {
        return new LogEntry(getUuid(), user, LogAction.DELETE, getLogResource());
    }

    /**
     * Create a log entry for the updating of the object
     *
     * @param user the user that updated this object
     * @param old copy of the old version of this object
     * @return null if the object does not want to log the creation
     */
    default LogEntry logUpdate(User user, EditableObject old) {
        return new LogEntry(getUuid(), user, LogAction.UPDATE, getLogResource(), compareFields(old, this));
    }

}
