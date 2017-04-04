package spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/10/17.
 */
@ResponseStatus(value= HttpStatus.UNSUPPORTED_MEDIA_TYPE, reason="The Content-Type header is not set to application/json .")
public class ContentTypeHeaderException extends RuntimeException{
}
