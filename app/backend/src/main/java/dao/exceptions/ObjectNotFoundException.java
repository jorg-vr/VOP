package dao.exceptions;

import java.util.UUID;

/**
 * Exception to throw when a given uuid is not found in the database
 * Created by sam on 4/18/17.
 */
public class ObjectNotFoundException extends Exception {

    /**
     * Constructor
     * @param uuid the uuid which gave the exception
     */
    public ObjectNotFoundException(UUID uuid ){
        super(String.format("Object with UUID '%s' was not found in the database.",uuid.toString()));
    }

}
