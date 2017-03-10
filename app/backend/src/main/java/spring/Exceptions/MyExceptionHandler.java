package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import spring.model.RESTError;


/**
 * Created by jorg on 3/10/17.
 */
@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(AttributeException.class)
    protected ResponseEntity<Object> handleAttributeException(AttributeException attributeException) {
        RESTError restError=attributeException.getRestError();
        return new ResponseEntity<>(restError,HttpStatus.valueOf(restError.getCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleBadInput(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new RESTError(400,"The JSON is malformed. This is a syntax error.",""));
    }
}
