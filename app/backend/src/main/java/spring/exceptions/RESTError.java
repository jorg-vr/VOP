package spring.exceptions;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTError {

    // The HTTP response code
    private int code;

    // A message that gives more information about what went wrong
    private String message;

    public RESTError(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public RESTError() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
