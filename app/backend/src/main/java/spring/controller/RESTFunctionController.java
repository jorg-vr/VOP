package spring.controller;

import controller.ControllerFactory;
import controller.CustomerController;
import controller.FleetController;
import controller.FunctionController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.fleet.Fleet;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.RESTFleet;
import spring.model.RESTFunction;
import spring.model.RESTModelFactory;
import spring.model.RESTSchema;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * Created by Jarre on 6-4-2017.
 */

@RestController
@RequestMapping("/users/{userId}/functions")
public class RESTFunctionController extends RESTAbstractController<RESTFunction, Function> {

    public RESTFunctionController() {
        super(FunctionController::new, RESTFunction::new);
    }

    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTFunction> get(HttpServletRequest request,
                                        @PathVariable Optional<String> userId,
                                        @RequestParam(required = false) String user,
                                        @RequestParam(required = false) String company,
                                        @RequestParam(required = false) String role,
                                        @RequestParam(required = false) Integer page,
                                        @RequestParam(required = false) Integer limit,
                                        @RequestParam(required = false) String sort,
                                        @RequestHeader(value="AuthToken") String token,
                                        @RequestHeader(value="Function") String authorityFunction){

        if(userId.isPresent()){
            user = userId.get();
        }
        Collection<RESTFunction> restFunctions = new ArrayList<>();
        try(FunctionController controller = new FunctionController(verifyToken(token, authorityFunction))){
            for(Function function: controller.getAll()){
                restFunctions.add(new RESTFunction(function));
            }
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }

        return new RESTSchema<RESTFunction>(restFunctions, page, limit, request);
    }
}
