package model.billing;

import model.history.EditableObject;

import java.util.UUID;

/**
 * Created by Ponti on 27/03/2017.
 */
public class Cost implements EditableObject, java.io.Serializable {

    public boolean isCorrection() {
        return false;
    }

    @Override
    public UUID getUuid() {
        return null;
    }

    @Override
    public EditableObject copy() {
        return null;
    }
}
