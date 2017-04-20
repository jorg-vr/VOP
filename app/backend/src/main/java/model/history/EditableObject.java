package model.history;

import java.util.UUID;


public interface EditableObject {
    UUID getUuid();
    EditableObject copy();
}
