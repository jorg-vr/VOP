package spring.controller;

import controller.exceptions.UnAuthorizedException;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;

import java.util.*;

/**
 * This class contains methods that every RESTController should have
 */
public abstract class RESTSimpleController {


    /**
     * Verifies the token and retrieves the Function corresponding to functionId.
     *
     * @param token      string representation of the token
     * @return Function object of user corresponding to functionId
     * @throws UnAuthorizedException User does not have a function with that functionId
     * @throws InvalidInputException when there is no Function with that functionId or the functionId is not a valid number
     */
    public UUID verifyToken(String token) throws UnAuthorizedException {
        return new AuthenticationToken(token).getAccountId();
    }
}
