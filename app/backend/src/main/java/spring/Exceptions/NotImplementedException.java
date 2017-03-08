package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/8/17.
 */
@ResponseStatus(reason= "not implemented",value=HttpStatus.NOT_IMPLEMENTED)
public class NotImplementedException extends RuntimeException {
}
