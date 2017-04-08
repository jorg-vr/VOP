package spring.controller;

import controller.AbstractController;
import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import model.account.User;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTFunction;
import spring.model.RESTUser;

import java.util.ArrayList;
import java.util.Collection;

@RestController
@RequestMapping("/${path.users}/me")
public class RESTUserMeController {

    @RequestMapping(method = RequestMethod.GET)
    public RESTUser getMe(@RequestHeader(value = "Authorization") String token) {
        return new RESTUser(getUser(token));
    }

    @RequestMapping(value = "/functions", method = RequestMethod.GET)
    public Collection<RESTFunction> getAll(@RequestHeader(value = "Authorization") String token) {
        Collection<RESTFunction> functions = new ArrayList<>();
        for (Function function: getUser(token).getFunctions()) {
            functions.add(new RESTFunction(function));
        }
        return functions;
    }

    @RequestMapping(method = RequestMethod.PUT)
    public RESTUser putId(@RequestBody RESTUser rest, @RequestHeader(value = "Authorization") String token,
                   @RequestHeader(value = "Function") String function) {
        String id = UUIDUtil.UUIDToNumberString(new AuthenticationToken(token).getAccountId());
        return new RESTUserController().putId(id, rest, token , function);
    }


    private User getUser(String token) {
        try (AuthController authController = new AuthController()) {
            return authController.getUser(new AuthenticationToken(token));
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }

}
