package model.history;

import java.util.UUID;

/**
 * This class gives more information of what the value of a certain field changed to in a certain action.
 *
 * @author Billie Devolder
 */
public class Description {

    // Unique id
    private UUID uuid;

    // The name of the field that has been changed
    private String field;

    // String representation of the old value of the field
    private String oldValue;

    // String representation of the new value of the field
    private String newValue;

    public Description() {
    }

    public Description(String field, Object oldObject, Object newObject) {
        this.field = field;
        oldValue = "null";
        if (oldObject != null) {
            oldValue = oldObject.toString();
        }

        newValue = "null";
        if (newObject != null) {
            newValue = newObject.toString();
        }
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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

    @Override
    public String toString() {
        return field + ": " + oldValue + " -> " + newValue;
    }
}
