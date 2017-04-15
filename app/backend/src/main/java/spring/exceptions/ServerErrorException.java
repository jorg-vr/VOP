package spring.exceptions;

/**
 * Created by Billie Devolder on 15/04/2017.
 */
public class ServerErrorException extends RuntimeException {

    public ServerErrorException(String message) {
        super(message);
    }
}
