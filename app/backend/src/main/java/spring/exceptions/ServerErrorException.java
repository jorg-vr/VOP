package spring.exceptions;

/**
 * An exception that gets thrown when there are certain problems with the server.
 * These problems are not intended and point to programming errors and/or inconsistencies in the database.
 * This exception gets translated to a HTTP response code in the ExceptionHandler
 */
public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }
}
