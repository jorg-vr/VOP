package dao.exceptions;

import java.util.Map;

/**
 * Created by sam on 5/1/17.
 */
public class ConstraintViolationException extends Exception {

    private final Map<String, String> violationMap;

    public ConstraintViolationException(Map<String, String> violationMap) {
        this.violationMap = violationMap;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append("One or more constrains are violated:\n");
        for(Map.Entry<String,String> entry: violationMap.entrySet()){
            builder.append(entry.getKey());
            builder.append(": ");
            builder.append(entry.getValue());
            builder.append("\n");
        }
        return builder.toString();
    }
}
