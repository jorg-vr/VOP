package spring.exceptions;

import com.opencsv.exceptions.CsvException;
import controller.exceptions.InvalidTokenException;
import controller.exceptions.UnAuthorizedException;
import csv.InvalidCSVHeaderException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pdf.PdfException;


/**
 * This class is translates all the exceptions that get thrown in the application to the correct HTTP return code.
 */
@ControllerAdvice
public class MyExceptionHandler {

    /**
     * Generate a response entity with a certain http resonse code
     *
     * @param status  the status that should be returned
     * @param message message with more information about what went wrong
     * @return a response entity with a status equal to the status argument and a body that contains a RESTError with the message argument
     */
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

    @ExceptionHandler(PdfException.class)
    protected ResponseEntity<Object> handlePdfException(PdfException ex) {
        return generateErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(InvalidCSVHeaderException.class)
    protected ResponseEntity<Object> handleInvalidCSVHeaderException(InvalidCSVHeaderException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(CsvException.class)
    protected ResponseEntity<Object> handleCsvException(CsvException ex) {
        return generateErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    protected ResponseEntity<Object> handleServerErrorException(ConstraintViolationException ex) {
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new RESTConstraintsError(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        "The JSON is has correct syntax, but an attribute was missing or one of the attributes has incorrect content. " +
                                "All attributes with errors or missing ones should be reported back in the error object."
                        , ex.getViolationMap()));
    }
}
