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
 * 1) history changes (not yet implemented) TODO milestone3
 * 2) Authorization
 * 3) business logic
 * <p>
 * Currently there is a generic implementation for the get, update, create and archive methods.
 */
public abstract class AbstractController<T extends EditableObject> implements AutoCloseable {

    private DAO<T> dao;
    private Resource resource;
    private Function function;
    private Role role;

    /**
     * @param dao      a DAO object on which the get, listFiltered and archive methods should be called.
     * @param resource Resource the function should have to be able to get,create,update and/or delete an object.
     * @param function of the user. This is used to determine the user has rights to do a certain operation
     */
    public AbstractController(DAO<T> dao, Resource resource, Function function) {
        this.dao = dao;
        this.resource = resource;
        this.function = function;
        this.role = function.getRole();
    }

    /**
     * This gets called when the role corresponding with the function has the READ/UPDATE/DELETE/CREATE_MINE action of the resource.
     * It should determine whether the function is the "owner" of the object.
     *
     * @param t        object that should be checked
     * @param function the user field of function will be used to determine if the user is the owner
     * @return true if function is the "owner" of the object
     */
    public abstract boolean isOwner(T t, Function function);

    /**
     * Attempt to get the object of type T that has the UUID uuid
     *
     * @param uuid uuid of object to retrieve
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException
     * @throws UnAuthorizedException The function does not have the rights to perform this operation.
     *                               The object can be retrieved if the user has the READ_ALL Action of the Resource
     *                               or READ_MINE and the user is the owner of the object.
     */
    public T get(UUID uuid) throws DataAccessException, UnAuthorizedException {
        T t = dao.get(uuid);
        if (role.hasAccess(resource, READ_ALL) ||
                (role.hasAccess(resource, READ_MINE) && isOwner(t, function))) {
            return t;
        } else {
            throw new UnAuthorizedException();
        }

    }

    /**
     * Attempt to get all object of type T that pass the filters.
     * <p>
     * This method should become protected in the near future.
     *
     * @param filters filters where the objects should be filtered
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException
     * @throws UnAuthorizedException The function does not have the rights to retrieve all of the objects that pass the filters.
     *                               If there is atleast one object that the function does not have the rights to retrieve, this exception will be thrown.
     */
    public Collection<T> getAll(Filter... filters) throws DataAccessException, UnAuthorizedException {
        Collection<T> collection = dao.listFiltered(filters);
        if (role.hasAccess(resource, READ_ALL) ||
                (role.hasAccess(resource, READ_MINE) && collection.stream().allMatch((t) -> isOwner(t, function)))) {
            return collection;
        } else {
            throw new UnAuthorizedException();
        }

    }

    /**
     * Attempt to archive the object of type T that has the UUID uuid
     *
     * @param uuid uuid of object to archive
     * @throws DataAccessException
     * @throws UnAuthorizedException The function does not have the rights to perform this operation.
     *                               The object can be retrieved if the user has the REMOVE_ALL Action of the Resource
     *                               or REMOVE_MINE and the user is the owner of the object.
     */
    public void archive(UUID uuid) throws DataAccessException, UnAuthorizedException {
        T t = dao.get(uuid);
        if (role.hasAccess(resource, REMOVE_ALL) ||
                (role.hasAccess(resource, REMOVE_MINE) && isOwner(t, function))) {
            dao.remove(uuid);
        } else {
            throw new UnAuthorizedException();
        }
    }

    /**
     * Attempt to create the object t
     *
     * @param t the value of uuid will be ignored
     * @return the same object as t, but the uuid field will now have a valid value
     * @throws DataAccessException
     * @throws UnAuthorizedException The function does not have the rights to perform this operation.
     *                               The object can be created if the user has the CREATE_ALL Action of the Resource
     *                               or CREATE_MINE and the user is the owner of the object.
     */
    public T create(T t) throws DataAccessException, UnAuthorizedException {
        if (role.hasAccess(resource, CREATE_ALL) ||
                (role.hasAccess(resource, CREATE_MINE) && isOwner(t, function))) {
            return dao.create(t);
        } else {
            throw new UnAuthorizedException();
        }
    }

    /**
     * Attempt to get the object of type T that has the UUID uuid
     *
     * @param t object that has to be updated. All fields can be different from what the fields are in the database except the uuid.
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException
     * @throws UnAuthorizedException The function does not have the rights to perform this operation.
     *                               The object can be updated if the user has the UPDATE_ALL Action of the Resource
     *                               or READ_MINE and the user is the owner of the original object and the updated object.
     */
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

    public DAO<T> getDao() {
        return dao;
    }

    /**
     * Close the dao that was passed in the constructor in the creation of this object.
     */
    public void close() {

    }
}
