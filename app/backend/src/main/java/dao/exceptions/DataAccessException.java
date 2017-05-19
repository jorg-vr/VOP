package dao.exceptions;

/**
 * Custom general Exception for database errors
 */
public class DataAccessException extends Exception {

    /**
     * Default Constructor
     */
    public DataAccessException() {
    }

    /**
     * Constructor
     * @param s the message
     */
    public DataAccessException(String s) {
        super(s);
    }

    /**
     * Constructor
     * @param s the message
     * @param throwable another exception
     */
    public DataAccessException(String s, Throwable throwable) {
        super(s, throwable);
    }

    /**
     * Constructor
     * @param throwable another exception
     */
    public DataAccessException(Throwable throwable) {
        super(throwable);
    }

}
