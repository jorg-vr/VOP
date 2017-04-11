package spring.controller;

import controller.AuthController;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.account.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.model.AuthenticationToken;

import java.util.*;

/**
 * This class contains methods that every RESTController should have
 */
public abstract class RESTSimpleController {

    public Function verifyToken(String token,String functionId){
//        Function function = new Function();
//        Role role = new Role();
//        for (Resource resource: Resource.values()) {
//            for (Action action: Action.values()) {
//                role.setAccess(resource, action);
//            }
//        }
//        function.setRole(role);
//        return function;
        try (AuthController authController = new AuthController()){
            return authController.getFunction(new AuthenticationToken(token),UUIDUtil.toUUID(functionId));
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }
}
