package spring.controller;


import controller.RoleController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.account.Role;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTRole;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * This controller is responsible for handling the HTTP requests of the URL /roles.
 * Currently, the following HTTP requests are supported:
 * 1) GET /roles
 * 2) GET /roles/{id}
 * 3) POST /roles
 * 4) PUT /roles/{id}
 * 5) DELETE /roles/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/auth/roles")
public class RESTRoleController extends RESTAbstractController<RESTRole, Role> {


    public RESTRoleController() {
        super(RoleController::new, RESTRole::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTRole> get(HttpServletRequest request,
                                    @RequestParam(required = false) String name,
                                    @RequestParam(required = false) Integer page,
                                    @RequestParam(required = false) Integer limit,
                                    @RequestHeader(value = "AuthToken") String token,
                                    @RequestHeader(value = "Function") String fu) {
        Function function = verifyToken(token, fu);

        Collection<RESTRole> restRoles = new ArrayList<>();
        try (RoleController roleController = new RoleController(function)) {
            Collection<Role> roles = roleController.getAll();
            for (Role role : roles) {
                restRoles.add(new RESTRole(role));
            }
        } catch (DataAccessException e) {
            throw new InvalidInputException("Something is wrong with the database");
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
        return new RESTSchema<>(restRoles, page, limit, request);
    }
}
