package model.insurance;


import model.history.EditableObject;

import java.util.UUID;

public class Insurance implements EditableObject, java.io.Serializable {

    public Insurance() {
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
