package spring.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jorg on 3/10/17.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Resource is still being used and could not be archived.")
public class ArchiveConflictException extends RuntimeException {
}
