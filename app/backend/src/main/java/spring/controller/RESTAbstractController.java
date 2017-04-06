package spring.controller;

import controller.AbstractController;
import controller.AuthController;
import controller.ControllerFactory;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;

import model.account.Action;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.history.EditableObject;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.RESTAbstractModel;
import spring.model.AuthenticationToken;
import spring.model.RESTModelFactory;

import java.util.*;

/**
 * Created by jorg on 3/30/17.
 */
public abstract class RESTAbstractController<R extends RESTAbstractModel<M>,M extends EditableObject> extends RESTSimpleController {
    private ControllerFactory<M> controllerFactory;

    private RESTModelFactory<R,M> factory;

    public RESTAbstractController(ControllerFactory<M> controllerFactory,RESTModelFactory<R,M> factory) {
        this.controllerFactory = controllerFactory;
        this.factory=factory;
    }

    public Function verifyToken(String token, String functiunId){
        Function function = new Function();
        Role role = new Role();
        for (Resource resource: Resource.values()) {
            for (Action action: Action.values()) {
                role.setAccess(resource, action);
            }
        }
        function.setRole(role);
        return function;
        /**
        try {
            return new AuthController().getFunction(new AuthenticationToken(token),UUIDUtil.toUUID(functiunId));
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }*/
    }

    public ControllerFactory<M> getControllerFactory() {
        return controllerFactory;
    }

    @RequestMapping(method = RequestMethod.POST)
    public R post(@RequestBody R rest, @RequestHeader(value="AuthToken") String token,
                  @RequestHeader(value="Function") String function) {
        try(AbstractController<M> controller=controllerFactory.create(verifyToken(token,function))) {
            M model = controller.create(rest.translate(verifyToken(token,function)));
            return factory.create(model);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public R getId(@PathVariable("id") String id, @RequestHeader(value="AuthToken") String token,
                   @RequestHeader(value="Function") String function) {
        UUID uuid = UUIDUtil.toUUID(id);
        try(AbstractController<M> controller=controllerFactory.create(verifyToken(token,function))) {
            return factory.create(controller.get(uuid));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public R putId(@PathVariable("id") String id, @RequestBody R rest, @RequestHeader(value="AuthToken") String token,
                   @RequestHeader(value="Function") String function) {
        try(AbstractController<M> controller=controllerFactory.create(verifyToken(token,function))) {
            rest.setId(id);
            M model = rest.translate(verifyToken(token,function));
            model = controller.update(model);
            return factory.create(model);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvalidInputException();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteId(@PathVariable("id") String id, @RequestHeader(value="AuthToken") String token,
                         @RequestHeader(value="Function") String function) {
        UUID uuid = UUIDUtil.toUUID(id);
        try(AbstractController<M> controller=controllerFactory.create(verifyToken(token,function))) {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }
}
