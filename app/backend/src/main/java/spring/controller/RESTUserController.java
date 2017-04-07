package spring.controller;

import controller.AuthController;
import controller.ControllerFactory;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.account.User;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;
import spring.model.RESTUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * This controller is responsible for handling the HTTP requests of the URL /user.
 * Currently, the following HTTP requests are supported:
 * 1) GET /user
 * 2) GET /user/{id}
 * 3) POST /user
 * 4) PUT /user/{id}
 * 5) DELETE /user/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/users")
public class RESTUserController extends RESTAbstractController<RESTUser, User> {

    public RESTUserController() {
        super(UserController::new, RESTUser::new);
    }

    /**
     * @return a collection of all the users in the system.
     * If there are no users, an empty collection will be returned.
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTUser> get(HttpServletRequest request,
                                    String email,
                                    String firstName,
                                    String lastName,
                                    Integer page,
                                    Integer limit,
                                    @RequestHeader(value="Authorization") String token,
                                    @RequestHeader(value="Function") String function) {
        Collection<RESTUser> restUsers = new ArrayList<>();
        try (UserController userController = new UserController(verifyToken(token, function))) {
            Collection<User> users = userController.getAll();
            for (User user: users) {
                restUsers.add(new RESTUser(user));
            }

        }catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        return new RESTSchema<>(restUsers, page, limit, request);
    }
}
