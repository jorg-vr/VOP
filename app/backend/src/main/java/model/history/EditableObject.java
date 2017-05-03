package model.history;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;


public interface EditableObject {
    UUID getUuid();
    EditableObject copy();

    default Collection<LogEntry> logCreate() {
        return new ArrayList<>();
    }
}
