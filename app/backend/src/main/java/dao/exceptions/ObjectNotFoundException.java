package dao.exceptions;

import java.util.UUID;

/**
 * Created by sam on 4/18/17.
 */
public class ObjectNotFoundException extends Exception {

    public ObjectNotFoundException(){
        super();
    }

    public ObjectNotFoundException(UUID uuid){
        super(String.format("Object with UUID '%s' was not found.",uuid.toString()));
    }
}
