package dao.exceptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Exception to throw when constraints in the validation.xml are violated
 * Created by sam on 5/1/17.
 */
public class ConstraintViolationException extends Exception {

    /**
     * The mapping of property and violation message
     */
    private final Map<String, String> violationMap;

    /**
     * Constructor
     * @param violationMap the mapping of property and violation message
     */
    public ConstraintViolationException(Map<String, String> violationMap) {
        this.violationMap = violationMap;
    }

    /**
     * Constructor
     * @param field the violated Property
     * @param message the error message
     */
    public ConstraintViolationException(String field, String message) {
        violationMap = new HashMap<>();
        violationMap.put(field, message);
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("One or more constrains are violated:\n");
        for(Map.Entry<String,String> entry: violationMap.entrySet()){
            builder.append("'");
            builder.append(entry.getKey());
            builder.append("': ");
            builder.append(entry.getValue());
            builder.append("\n");
        }
        return builder.toString();
    }

    /**
     * Gets the violation map
     * @return the violation map
     */
    public Map<String, String> getViolationMap() {
        return violationMap;
    }
}
