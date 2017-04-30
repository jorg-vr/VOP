package spring.controller;

import controller.AuthController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.Function;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;
import spring.model.RESTAuth;
import spring.model.RESTFunction;
import spring.model.RESTRole;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Requests that are implemented in this class:
 *  1) POST /auth/login
 *  2) POST /auth/refresh
 */
@RestController
@RequestMapping("/${path.auth}")
public class RESTAuthController {

    @RequestMapping(value = "/${path.login}", method = RequestMethod.POST)
    public String post(@RequestBody RESTAuth restAuth) throws UnAuthorizedException {
        try (AuthController authController = new AuthController()) {
            return authController.getToken(restAuth.getLogin(), restAuth.getPassword()).toString();
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }

    @RequestMapping(value = "/${path.refresh}", method = RequestMethod.POST)
    public String put(@RequestHeader(value = "Authorization") String token) throws UnAuthorizedException {
        try (AuthController authController = new AuthController()) {
            return authController.refreshToken(new AuthenticationToken(token)).toString();
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }
}
