package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.*;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;
import util.UUIDUtil;

/**
 * This class contains methods that every RESTController should have
 */
public abstract class RESTSimpleController {


    /**
     * Verifies the token and retrieves the Function corresponding to functionId.
     * @param token string representation of the token
     * @param functionId string representation of id of function of user that should be used
     * @return Function object of user corresponding to functionId
     * @throws UnAuthorizedException User does not have a function with that functionId
     * @throws InvalidInputException when there is no Function with that functionId or the functionId is not a valid number
     */
    public Function verifyToken(String token, String functionId) throws UnAuthorizedException {
        try (AuthController authController = new AuthController()) {
            return authController.getFunction(new AuthenticationToken(token), UUIDUtil.toUUID(functionId));
        } catch (DataAccessException | NumberFormatException e) {
            throw new InvalidInputException("Value of function header is invalid");
        }
    }
}
