package src.main.java.model.history;

import java.util.UUID;


public interface EditableObject {
    public UUID getUuid();
    public EditableObject copy();
}
