package spring.controller;

import controller.AbstractController;
import controller.ControllerManager;
import controller.FunctionController;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;

import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTFleet;
import spring.model.RESTFunction;
import spring.model.RESTSchema;
import util.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * Requests that are implemented in this class:
 * 1) GET /users/{userId}/functions
 * 2) POST /users/{userId}/functions
 * 2) GET /users/{userId}/functions/{id}
 * 4) PUT /users/{userId}/functions/{id}
 * 2) DELETE /users/{userId}/functions/{id}
 */
@RestController
@RequestMapping("/${path.users}/{userId}/${path.functions}")
public class RESTFunctionController extends RESTAbstractController<RESTFunction, Function> {

    public RESTFunctionController() {
        super(RESTFunction::new);
    }

    @Override
    public AbstractController<Function> getController(ControllerManager manager) {
        return manager.getFunctionController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFunction> get(HttpServletRequest request,
                                        @PathVariable String userId,
                                        @RequestParam(required = false) String company,
                                        @RequestParam(required = false) String role,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) String sort,
                                        @RequestHeader(value = "Authorization") String token,
                                        @RequestHeader(value = "Function") String function) throws UnAuthorizedException {

        Collection<RESTFunction> restFunctions;
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            FunctionController controller = manager.getFunctionController();
            restFunctions = controller.getAll()
                    .stream()
                    .map(RESTFunction::new)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return new RESTSchema<>(restFunctions, page, limit, request);
    }
}
