package spring.controller;

import controller.AbstractController;
import controller.ControllerManager;
import controller.FunctionController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.account.Function;
import model.account.Role;
import model.account.User;
import model.identity.Company;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTFunction;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
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

            User userObject = new User(toUUID(userId));
            Company companyObject = company != null ? new Company(toUUID(company)) : null;
            Role roleObject = role != null ? new Role(toUUID(role)) : null;

            restFunctions = controller.getFiltered(userObject, companyObject, roleObject)
                    .stream()
                    .map(RESTFunction::new)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return new RESTSchema<>(restFunctions, page, limit, request);
    }
}
