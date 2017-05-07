package spring.exceptions;

import controller.exceptions.InvalidTokenException;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Created by jorg on 3/10/17.
 */
@ControllerAdvice
public class MyExceptionHandler {

    private ResponseEntity<Object> generateErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new RESTError(status.value(), message));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<Object> handleBadInput(HttpMessageNotReadableException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, "The JSON is malformed. This is a syntax error.");
    }

    @ExceptionHandler(UnAuthorizedException.class)
    protected ResponseEntity<Object> handleUnAuthorizedException(UnAuthorizedException ex) {
        return generateErrorResponse(HttpStatus.FORBIDDEN, "Not authorized");
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<Object> handleInvalidTokenException(InvalidTokenException ex) {
        return generateErrorResponse(HttpStatus.UNAUTHORIZED, "Token is not valid");
    }

    @ExceptionHandler(InvalidInputException.class)
    protected ResponseEntity<Object> handleInvalidInputException(InvalidInputException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    protected ResponseEntity<Object> handleDataAccessException(DataAccessException ex) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    protected ResponseEntity<Object> handleServerErrorException(ObjectNotFoundException ex) {
        return generateErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleServerErrorException(ConstraintViolationException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new RESTConstraintsError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "The JSON is has correct syntax, but an attribute was missing or one of the attributes has incorrect content. " +
                                "All attributes with errors or missing ones should be reported back in the error object."
                        , ex.getViolationMap()));
    }
}
