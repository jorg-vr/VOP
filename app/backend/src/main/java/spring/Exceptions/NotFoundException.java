package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/8/17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="Resource not found")
public class NotFoundException extends RuntimeException {
}
