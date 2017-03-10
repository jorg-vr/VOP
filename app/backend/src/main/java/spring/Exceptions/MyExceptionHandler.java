package spring.Exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.model.RESTError;

/**
 * Created by jorg on 3/10/17.
 */
@ControllerAdvice
public class MyExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AttributeException.class)
    protected ResponseEntity<Object> handleAttributeException(AttributeException attributeException) {
        RESTError restError=attributeException.getRestError();
        return new ResponseEntity<>(restError,HttpStatus.valueOf(restError.getCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity handleBadInput(HttpMessageNotReadableException ex) {
        return new ResponseEntity(HttpStatus.OK);
        //throw new JSONMalformedException();
    }

}
