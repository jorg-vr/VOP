package spring.controller;

import controller.AbstractController;
import controller.ControllerManager;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import model.history.EditableObject;
import org.springframework.web.bind.annotation.*;
import spring.exceptions.InvalidInputException;
import spring.model.AuthenticationToken;
import spring.model.RESTAbstractModel;
import spring.model.RESTModelFactory;

import java.util.UUID;

import static util.UUIDUtil.toUUID;

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

    private RESTModelFactory<R, M> factory;

    /**
     * @param factory           should be able to translate an object of type M to type R
     */
    public RESTAbstractController(RESTModelFactory<R, M> factory) {
        this.factory = factory;
    }

    public abstract AbstractController<M> getController(ControllerManager manager);

    @RequestMapping(method = RequestMethod.POST)
    public R post(@RequestBody R rest, @RequestHeader(value = "Authorization") String token,
                  @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            AbstractController<M> controller = getController(manager);
            M model = controller.create(rest.translate(manager));
            return factory.create(model);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public R getId(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        UUID uuid = toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            AbstractController<M> controller = getController(manager);
            return factory.create(controller.get(uuid));
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public R putId(@PathVariable("id") String id, @RequestBody R rest, @RequestHeader(value = "Authorization") String token,
                   @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ConstraintViolationException, ObjectNotFoundException, DataAccessException {
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            AbstractController<M> controller = getController(manager);
            rest.setId(id);
            M model = rest.translate(manager);
            model = controller.update(model);
            return factory.create(model);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "{id}")
    public void deleteId(@PathVariable("id") String id, @RequestHeader(value = "Authorization") String token,
                         @RequestHeader(value = "Function") String function) throws UnAuthorizedException, ObjectNotFoundException, DataAccessException {
        UUID uuid = toUUID(id);
        UUID user = new AuthenticationToken(token).getAccountId();
        try (ControllerManager manager = new ControllerManager(user, toUUID(function))) {
            AbstractController<M> controller = getController(manager);
            controller.archive(uuid);
        }
    }
}
