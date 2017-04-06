package spring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/30/17.
 */
@ResponseStatus(reason= "Not Authorized",value= HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException{
}
