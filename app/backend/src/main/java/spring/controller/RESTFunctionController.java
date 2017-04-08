package spring.controller;

import controller.FunctionController;
import controller.UserController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;

import org.springframework.web.bind.annotation.*;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTFunction;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Requests that are implemented in this class:
 *  1) GET /users/{userId}/functions
 *  2) POST /users/{userId}/functions
 *  2) GET /users/{userId}/functions/{id}
 *  4) PUT /users/{userId}/functions/{id}
 *  2) DELETE /users/{userId}/functions/{id}
 */
@RestController
@RequestMapping("/${path.users}/{userId}/${path.functions}")
public class RESTFunctionController extends RESTAbstractController<RESTFunction, Function> {

    public RESTFunctionController() {
        super(FunctionController::new, RESTFunction::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFunction> get(HttpServletRequest request,
                                        @PathVariable String userId,
                                        @RequestParam(required = false) String company,
                                        @RequestParam(required = false) String role,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) String sort,
                                        @RequestHeader(value="Authorization") String token,
                                        @RequestHeader(value="Function") String authorityFunction){

        Collection<RESTFunction> restFunctions = new ArrayList<>();
        try(UserController userController = new UserController(verifyToken(token, authorityFunction))){
            Collection<Function> functions = userController.get(UUIDUtil.toUUID(userId)).getFunctions();
            for(Function function: functions) {
                restFunctions.add(new RESTFunction(function));
            }
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        return new RESTSchema<>(restFunctions, page, limit, request);
    }
}
