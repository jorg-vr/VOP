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
 * This class implements basic GET/{id}, POST, PUT/{id} and DELETE/{id} requests.
 * Note that there is no generic implementation for GET.
 * Basic means that it translates the RESTModel R to the domain specific model M
 * and that it calls the appropriate method of the controller corresponding with the Model M.
 * Authentication and exception handling is also implemented in the methods.
 *
 * @param <R> A subclass of RESTAbstractModel
 * @param <M> The model that corresponds to the RESTModel of type R
 */
public abstract class RESTAbstractController<R extends RESTAbstractModel<M>, M extends EditableObject> extends RESTSimpleController {

    private ControllerFactory<M> controllerFactory;

    private RESTModelFactory<R, M> factory;

    /**
     * @param controllerFactory should be able to create a new controller for type M
     * @param factory           should be able to translate an object of type M to type R
     */
    public RESTAbstractController(ControllerFactory<M> controllerFactory, RESTModelFactory<R, M> factory) {
        this.controllerFactory = controllerFactory;
        this.factory = factory;
    }

    public ControllerFactory<M> getControllerFactory() {
        return controllerFactory;
    }

    @RequestMapping(method = RequestMethod.POST)
    public R post(@RequestBody R rest, @RequestHeader(value = "Authorization") String token,
                  @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (AbstractController<M> controller = controllerFactory.create(verifyToken(token, function))) {
            M model = controller.create(rest.translate(verifyToken(token, function)));
            return factory.create(model);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public R getId(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID uuid = UUIDUtil.toUUID(id);
        try (AbstractController<M> controller = controllerFactory.create(verifyToken(token, function))) {
            return factory.create(controller.get(uuid));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public R putId(@PathVariable("id") String id, @RequestBody R rest, @RequestHeader(value = "Authorization") String token,
                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        try (AbstractController<M> controller = controllerFactory.create(verifyToken(token, function))) {
            rest.setId(id);
            M model = rest.translate(verifyToken(token, function));
            model = controller.update(model);
            return factory.create(model);
        } catch (DataAccessException e) {
            e.printStackTrace();
            throw new InvalidInputException();
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteId(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                         @RequestHeader(value = "Function") String function) throws UnAuthorizedException {
        UUID uuid = UUIDUtil.toUUID(id);
        try (AbstractController<M> controller = controllerFactory.create(verifyToken(token, function))) {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }
}
