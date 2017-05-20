package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import org.springframework.web.bind.annotation.*;
import spring.model.AuthenticationToken;
import spring.model.RESTAuth;

/**
 * Requests that are implemented in this class:
 * 1) POST /auth/login
 * 2) POST /auth/refresh
 */
@RestController
@RequestMapping("/${path.auth}")
public class RESTAuthController {

    @RequestMapping(value = "/${path.login}", method = RequestMethod.POST)
    public String post(@RequestBody RESTAuth restAuth) throws UnAuthorizedException, DataAccessException {
        try (AuthController authController = new AuthController()) {
            return authController.getToken(restAuth.getLogin(), restAuth.getPassword()).toString();
        }
    }

    @RequestMapping(value = "/${path.refresh}", method = RequestMethod.POST)
    public String put(@RequestHeader(value = "Authorization") String token) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        try (AuthController authController = new AuthController()) {
            return authController.refreshToken(new AuthenticationToken(token)).toString();
        }
    }
}
