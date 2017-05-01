package dao.exceptions;

import model.history.EditableObject;

import java.util.UUID;

/**
 * Created by sam on 4/18/17.
 */
public class ObjectNotFoundException extends Exception {

    private final EditableObject object;

    public ObjectNotFoundException(EditableObject e ){
        super(String.format("Object with UUID '%s' was not found in the database.",e.getUuid().toString()));
        this.object =e ;
    }

    public EditableObject getObject() {
        return object;
    }
}
