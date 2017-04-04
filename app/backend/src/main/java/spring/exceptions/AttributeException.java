package spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.model.RESTError;

/**
 * Created by jorg on 3/10/17.
 */
@ResponseStatus(value= HttpStatus.UNPROCESSABLE_ENTITY, reason="The JSON is has correct syntax, but an attribute was missing or one of the attributes has incorrect content.")
public class AttributeException extends RuntimeException{
    //TODO add attributes to error object
    private RESTError restError;

    public AttributeException(String field) {
        restError=new RESTError(422,"The JSON is has correct syntax, but an attribute was missing or one of the attributes has incorrect content.",field);
    }


    public RESTError getRestError() {
        return restError;
    }
}
