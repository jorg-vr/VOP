package spring.controller;

import controller.ControllerManager;
import controller.LogEntryController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.fleet.Fleet;
import model.fleet.Vehicle;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTLogEntry;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /vehicles/{vehicleId}/logs/
 * 2) GET /vehicles/{vehicleId}/logs/{id}
 * 3) GET /fleets/{fleetId}/logs/
 * 4) GET /fleets/{fleetId}/logs/{id}
 *
 * @author Billie Devolder
 */
@RestController
public class RESTLogEntryController {

    @RequestMapping(value = {"/${path.vehicles}/{vehicleId}/${path.logs}/{id}", "/${path.fleets}/{fleetId}/${path.logs}/{id}"}, method = RequestMethod.GET)
    public RESTLogEntry get(@PathVariable String id,
                            @RequestHeader(value = "Authorization") String token,
                            @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            LogEntryController controller = manager.getLogEntryController();
            return new RESTLogEntry(controller.getEntry(toUUID(id)));
        }
    }

    @RequestMapping(value = "/${path.vehicles}/{id}/${path.logs}", method = RequestMethod.GET)
    public RESTSchema<RESTLogEntry> getVehicleEntries(@PathVariable String id,
                                                      HttpServletRequest request,
                                                      Integer page, Integer limit,
                                                      @RequestHeader(value = "Authorization") String token,
                                                      @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            LogEntryController controller = manager.getLogEntryController();
            Vehicle vehicle = manager.getVehicleController().get(toUUID(id));
            Collection<RESTLogEntry> entries = controller.getVehicleLogEntries(vehicle)
                    .stream()
                    .map(RESTLogEntry::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(entries, page, limit, request, (a, b) -> b.getDateTime().compareTo(a.getDateTime()));
        }
    }

    @RequestMapping(value = "/${path.fleets}/{id}/${path.logs}", method = RequestMethod.GET)
    public RESTSchema<RESTLogEntry> getFleetEntries(@PathVariable String id,
                                                    HttpServletRequest request,
                                                    Integer page, Integer limit,
                                                    @RequestHeader(value = "Authorization") String token,
                                                    @RequestHeader(value = "Function") String function) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            LogEntryController controller = manager.getLogEntryController();
            Fleet fleet = manager.getFleetController().get(toUUID(id));
            Collection<RESTLogEntry> entries = controller.getFleetLogEntries(fleet)
                    .stream()
                    .map(RESTLogEntry::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(entries, page, limit, request, (a, b) -> b.getDateTime().compareTo(a.getDateTime()));
        }
    }
}
