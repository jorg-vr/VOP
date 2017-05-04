package spring.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Billie Devolder
 */
public class RESTConstraintsError extends RESTError {

    private List<ErrorItem> errors;

    public RESTConstraintsError(int code, String message, Map<String, String> errors) {
        super(code, message);
        setErrors(errors);
    }

    public RESTConstraintsError() {
    }

    public List<ErrorItem> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorItem> errors) {
        this.errors = errors;
    }

    public void setErrors(Map<String, String> violations) {
        errors = new ArrayList<>();
        for (String field : violations.keySet()) {
            errors.add(new ErrorItem(field, violations.get(field)));
        }
    }

    private class ErrorItem {

        private String field;
        private String message;

        public ErrorItem(String field, String message) {
            this.field = field;
            this.message = message;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
