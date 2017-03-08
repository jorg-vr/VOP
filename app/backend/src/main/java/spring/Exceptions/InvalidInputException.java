package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/8/17.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid input")
public class InvalidInputException extends RuntimeException {
}
