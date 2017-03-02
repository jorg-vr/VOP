package history;

import java.util.UUID;

/**
 * Created by jorg on 3/2/17.
 */
public interface EditableObject {
    public UUID getUUID();
    public EditableObject copy();
}
