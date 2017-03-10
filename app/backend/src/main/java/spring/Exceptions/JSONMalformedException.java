package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/10/17.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="The JSON is malformed. This is a syntax error.")
public class JSONMalformedException extends RuntimeException{
}
