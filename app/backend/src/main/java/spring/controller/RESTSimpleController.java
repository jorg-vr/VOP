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
 * Created by Billie Devolder on 6/04/2017.
 */
public abstract class RESTSimpleController {

    public Function verifyToken(String token, String functiunId){
        try {
            Function function = new Function();
            Role role = new Role();
            Map<Resource, Permission> rights = new HashMap<>();
            for (Resource resource: Resource.values()) {
                Permission permission = new Permission();
                Set<Action> actionSet = new HashSet<>();
                Collections.addAll(actionSet, Action.values());
                permission.setActions(actionSet);
                rights.put(resource, permission);
            }
            role.setRights(rights);
            function.setRole(role);
            return function;
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new AuthController().getFunction(new AuthenticationToken(token),UUIDUtil.toUUID(functiunId));
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }
}
