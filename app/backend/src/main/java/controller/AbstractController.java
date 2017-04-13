package controller;

import controller.exceptions.UnAuthorizedException;
import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Action;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.history.EditableObject;

import java.util.Collection;
import java.util.UUID;

import static model.account.Action.*;

/**
 * This class and it's subclasses are framework independent controller classes.
 * In their final state, these classes should act as a protecting interface of the backend model.
 * The methods of these classes should in final state take care of:
 * 1) history changes (not yet implemented) TODO milestone?
 * 2) correct authentication (not yet implemented) TODO milestone 2
 * 3) business logic (currently nothing)
 * <p>
 * Currently there is a generic implementation for the get, archive and listFiltered methods.
 */
public abstract class AbstractController<T extends EditableObject> implements AutoCloseable {

    private DAO<T> dao;
    private Resource resource;
    private Function function;
    private Role role;

    /**
     * @param dao a DAO object on which the get, listFiltered and archive methods should be called.
     */
    public AbstractController(DAO<T> dao, Resource resource, Function function) {
        this.dao = dao;
        this.resource = resource;
        this.function = function;
        this.role = function.getRole();
    }

    public DAO<T> getDao() {
        return dao;
    }

    public T get(UUID uuid) throws DataAccessException, UnAuthorizedException {
        T t = dao.get(uuid);
        if (role.hasAccess(resource, READ_ALL) ||
                (role.hasAccess(resource, READ_MINE) && isOwner(t, function))) {
            return t;
        } else {
            throw new UnAuthorizedException();
        }

    }

    public Collection<T> getAll(Filter... filters) throws DataAccessException, UnAuthorizedException {
        Collection<T> collection = dao.listFiltered(filters);
        if (role.hasAccess(resource, READ_ALL)||
                (role.hasAccess(resource, READ_MINE)&&collection.stream().allMatch((t) -> isOwner(t, function)))) {
            return collection;
        } else {
            throw new UnAuthorizedException();
        }

    }

    public void archive(UUID uuid) throws DataAccessException, UnAuthorizedException {
        T t = dao.get(uuid);
        if (role.hasAccess(resource, REMOVE_ALL) ||
                (role.hasAccess(resource, REMOVE_MINE) && isOwner(t, function))) {
            dao.remove(uuid);
        } else {
            throw new UnAuthorizedException();
        }
    }

    public T create(T t) throws DataAccessException, UnAuthorizedException {
        if (role.hasAccess(resource, CREATE_ALL) ||
                (role.hasAccess(resource, CREATE_MINE) && isOwner(t, function))) {
            return dao.create(t);
        } else {
            throw new UnAuthorizedException();
        }
    }

    public T update(T t) throws DataAccessException, UnAuthorizedException {
        T tOld = dao.get(t.getUuid());
        if (role.hasAccess(resource, UPDATE_ALL) ||
                (role.hasAccess(resource, UPDATE_MINE) &&
                        isOwner(tOld, function) &&
                        isOwner(t, function))) {
            return dao.update(t);
        } else {
            throw new UnAuthorizedException();
        }
    }


    public void close() {
        try {
            dao.close();
        } catch (Exception e) {
            System.err.println("Could not close DAO!");
            e.printStackTrace();
        }
    }


    public abstract boolean isOwner(T t, Function function);

}
