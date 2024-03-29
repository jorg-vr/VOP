package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;
import spring.model.RESTFunction;
import spring.model.RESTSchema;
import spring.model.RESTUser;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Requests that are implemented in this class:
 * 1) GET /users/me
 * 2) PUT /users/me
 * 3) GET /users/me/functions
 * 4) GET /users/me/functions/{id}
 * these requests to not require a "Function" header and only need an "Authorization" header
 */
@RestController
@RequestMapping("/${path.users}/me")
public class RESTUserMeController {

    @RequestMapping(method = RequestMethod.GET)
    public RESTUser getMe(@RequestHeader(value = "Authorization") String token) throws ObjectNotFoundException, DataAccessException {
        try (AuthController authController = new AuthController()) {
            return new RESTUser(authController.getUser(new AuthenticationToken(token)));
        }
    }

    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public RESTSchema<RESTFunction> getAll(HttpServletRequest request,
                                           Integer page,
                                           Integer limit,
                                           @RequestHeader(value = "Authorization") String token) throws ObjectNotFoundException, DataAccessException {
        try (AuthController authController = new AuthController()) {
            Collection<RESTFunction> functions = authController.getUser(new AuthenticationToken(token))
                    .getFunctions()
                    .stream()
                    .map(RESTFunction::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(functions, page, limit, request);
        }
    }

    @RequestMapping(value = "/${path.functions}/{id}", method = RequestMethod.GET)
    public RESTFunction getFunction(@PathVariable String id, @RequestHeader(value = "Authorization") String token) throws ObjectNotFoundException, DataAccessException {
        UUID uuid = UUIDUtil.toUUID(id);
        try (AuthController controller = new AuthController()) {
            for (Function function : controller.getUser(new AuthenticationToken(token)).getFunctions()) {
                if (function.getUuid().equals(uuid)) {
                    return new RESTFunction(function);
                }
            }
            throw new InvalidInputException("user has no function with that id");
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RESTUser putId(@RequestBody RESTUser rest, @RequestHeader(value = "Authorization") String token,
                          @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException, DataAccessException {
        String id = UUIDUtil.UUIDToNumberString(new AuthenticationToken(token).getAccountId());
        return new RESTUserController().putId(id, rest, token, function);
    }
}
