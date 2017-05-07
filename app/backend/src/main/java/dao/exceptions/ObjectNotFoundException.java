package dao.exceptions;

import model.history.EditableObject;

import java.util.UUID;

/**
 * Created by sam on 4/18/17.
 */
public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(UUID uuid ){
        super(String.format("Object with UUID '%s' was not found in the database.",uuid.toString()));
    }

}
