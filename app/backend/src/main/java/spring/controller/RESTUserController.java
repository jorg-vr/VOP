package spring.controller;

import controller.*;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import model.account.User;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTSchema;
import spring.model.RESTUser;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

import static util.UUIDUtil.toUUID;

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
@RequestMapping("/${path.users}")
public class RESTUserController extends RESTAbstractController<RESTUser, User> {

    public RESTUserController() {
        super(RESTUser::new);
    }

    @Override
    public AbstractController<User> getController(ControllerManager manager) {
        return manager.getUserController();
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
                                    @RequestHeader(value = "Authorization") String token,
                                    @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            UserController controller = manager.getUserController();
            Collection<RESTUser> users = controller.getAll()
                    .stream()
                    .map(RESTUser::new)
                    .collect(Collectors.toList());
            return new RESTSchema<>(users, page, limit, request);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
