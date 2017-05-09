package spring.controller;

import controller.ControllerManager;
import controller.LogEntryController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.model.AuthenticationToken;
import spring.model.RESTLogEntry;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * @author Billie Devolder
 */
@RestController
public class RESTLogEntryController {

    @RequestMapping(value = {"/{path.vehicles}/id/{path.logs}", "/{path.fleets}/id/${path.logs}"})
    public RESTSchema<RESTLogEntry> getEntries(@PathVariable String id,
                                               HttpServletRequest request,
                                               Integer page, Integer limit,
                                               @RequestHeader(value = "Authorization") String token,
                                               @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            LogEntryController controller = manager.getLogEntryController();
            Collection<RESTLogEntry> entries = controller.getLogEntries(toUUID(id))
                    .stream()
                    .map(RESTLogEntry::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(entries, page, limit, request);
        }
    }
}
