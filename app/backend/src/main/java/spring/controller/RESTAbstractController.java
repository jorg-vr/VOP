package spring.controller;

import controller.AbstractController;
import controller.ControllerFactory;
import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DataAccessException;
import model.history.EditableObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import spring.exceptions.InvalidInputException;
import spring.exceptions.NotAuthorizedException;
import spring.exceptions.NotFoundException;
import spring.model.RESTAbstractModel;
import spring.model.RESTModelFactory;

import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class RESTAbstractController<R extends RESTAbstractModel<M>,M extends EditableObject> {
    private ControllerFactory<M> controllerFactory;

    private RESTModelFactory<R,M> factory;

    public RESTAbstractController(ControllerFactory<M> controllerFactory,RESTModelFactory<R,M> factory) {
        this.controllerFactory = controllerFactory;
        this.factory=factory;
    }
    @RequestMapping(method = RequestMethod.POST)
    public R post(@RequestBody R rest) {
        try {
            M model = controller.create(rest.translate());
            return factory.create(model);
        } catch (DataAccessException e) {
            throw new InvalidInputException(e);
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{id}")
    public R getId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            return factory.create(controller.get(uuid));
        } catch (DataAccessException e) {
            throw new NotFoundException();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{id}")
    public R putId(@PathVariable("id") String id, @RequestBody R rest) {
        try {
            rest.setId(id);
            M model = rest.translate();
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
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            controller.archive(uuid);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        } catch (UnAuthorizedException e) {
            throw new NotAuthorizedException();
        }
    }
}
