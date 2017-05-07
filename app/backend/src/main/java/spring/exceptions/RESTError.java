package spring.exceptions;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTError {
    private int code;
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
