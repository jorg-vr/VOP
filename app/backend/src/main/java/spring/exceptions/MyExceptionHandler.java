package spring.exceptions;

import controller.exceptions.InvalidTokenException;
import controller.exceptions.UnAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import spring.model.RESTError;


/**
 * Created by jorg on 3/10/17.
 */
@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(AttributeException.class)
    protected ResponseEntity<Object> handleAttributeException(AttributeException attributeException) {
        RESTError restError = attributeException.getRestError();
        return new ResponseEntity<>(restError, HttpStatus.valueOf(restError.getCode()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleBadInput(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new RESTError(400, "The JSON is malformed. This is a syntax error.", null));
    }

    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new RESTError(HttpStatus.FORBIDDEN.value(), "Not authorized", null));
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new RESTError(HttpStatus.UNAUTHORIZED.value(), "Token is not valid. The token can be expired or have the wrong format", null));
    }

    // TODO this should probably be changed/removed after DAOs throw proper exceptions
    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new RESTError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), null));
    }
}
