package spring.exceptions;

import java.util.Map;

/**
 * Created by Billie Devolder on 4/05/2017.
 */
public class RESTConstraintsError extends RESTError {

    private Map<String, String> errors;

    public RESTConstraintsError(int code, String message, Map<String, String> errors) {
        super(code, message);
        this.errors = errors;
    }

    public RESTConstraintsError() {
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
