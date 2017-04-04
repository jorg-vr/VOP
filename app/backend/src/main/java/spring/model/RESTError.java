package spring.model;

/**
 * This is a bean class as specified in the API specification
 */
public class RESTError {
    private int code;
    private String message;
    private  String fields;

    public RESTError(int code, String message, String fields) {
        this.code = code;
        this.message = message;
        this.fields = fields;
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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
