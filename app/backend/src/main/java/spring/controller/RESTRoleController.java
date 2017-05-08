package spring.controller;


import controller.AbstractController;
import controller.ControllerManager;
import controller.RoleController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.account.Role;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;
import spring.model.RESTRole;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

/**
 * This controller is responsible for handling the HTTP requests of the URL /roles.
 * Currently, the following HTTP requests are supported:
 * 1) GET /auth/roles
 * 2) GET /auth/roles/{id}
 * 3) POST /auth/roles
 * 4) PUT /auth/roles/{id}
 * 5) DELETE /auth/roles/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/${path.auth}/${path.roles}")
public class RESTRoleController extends RESTAbstractController<RESTRole, Role> {


    public RESTRoleController() {
        super(RESTRole::new);
    }

    @Override
    public AbstractController<Role> getController(ControllerManager manager) {
        return manager.getRoleController();
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTRole> get(HttpServletRequest request,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer limit,
                                    @RequestHeader(value = "Authorization") String token,
                                    @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            RoleController controller = manager.getRoleController();
            Collection<RESTRole> roles = controller.getAll()
                    .stream()
                    .map(RESTRole::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(roles, page, limit, request);
        } catch (DataAccessException e) {
            throw new InvalidInputException("Something is wrong with the database");
        }
    }
}
