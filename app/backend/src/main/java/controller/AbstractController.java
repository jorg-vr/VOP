package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAO;
import dao.interfaces.DAOManager;
import dao.interfaces.Filter;
import dao.interfaces.LogEntryDAO;
import model.account.Function;
import model.account.Resource;
import model.account.Role;
import model.history.EditableObject;
import model.history.LogEntry;

import java.util.Collection;
import java.util.UUID;

import static model.account.Action.*;

/**
 * This class and it's subclasses are framework independent controller classes.
 * These classes should act as a protecting interface of the backend model.
 * The methods of these classes take care of
 * 1) Logging
 * 2) Authorization
 * <p>
 * Currently there is a generic implementation for the get all , get id, update, create and archive methods.
 */
public abstract class AbstractController<T extends EditableObject> {

    private DAO<T> dao;
    private LogEntryDAO logEntryDAO;
    private Resource resource;
    private Function function;
    private Role role;

    /**
     * @param dao      a DAO object on which the get, listFiltered and archive methods should be called.
     * @param resource Resource the function should have to be able to get,create,update and/or delete an object.
     * @param function of the user. This is used to determine the user has rights to do a certain operation
     */
    public AbstractController(DAOManager manager, DAO<T> dao, Resource resource, Function function) {
        this.dao = dao;
        this.logEntryDAO = manager.getLogEntryDao();
        this.resource = resource;
        this.function = function;
        this.role = function.getRole();
    }

    /**
     * This gets called when the role corresponding with the function has the READ_MINE/UPDATE_MINE/DELETE_MINE/CREATE_MINE action of the resource.
     * It should determine whether the function is the "owner" of the object.
     *
     * @param t        object that should be checked
     * @param function the user field of function will be used to determine if the user is the owner
     * @return true if the user is authorized to perform an action on the object if it has *_MINE rights.
     */
    public abstract boolean isOwner(T t, Function function);

    /**
     * Attempt to get the object of type T that has the UUID uuid
     *
     * @param uuid uuid of object to retrieve
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException     something went wrong with the database
     * @throws UnAuthorizedException   The function does not have the rights to perform this operation.
     *                                 The object can be retrieved if the user has the READ_ALL Action of the Resource
     *                                 or READ_MINE and the user is the owner of the object.
     * @throws ObjectNotFoundException no object with that id exists
     */
    public T get(UUID uuid) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
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
     *
     * @param filters filters where the objects should be filtered
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException   something went wrong with the database
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
     * @throws DataAccessException     something went wrong with the database
     * @throws UnAuthorizedException   The function does not have the rights to perform this operation.
     *                                 The object can be retrieved if the user has the REMOVE_ALL Action of the Resource
     *                                 or REMOVE_MINE and the user is the owner of the object.
     * @throws ObjectNotFoundException no object with that id exists
     */
    public void archive(UUID uuid) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        T t = dao.get(uuid);
        if (role.hasAccess(resource, REMOVE_ALL) ||
                (role.hasAccess(resource, REMOVE_MINE) && isOwner(t, function))) {
            dao.remove(uuid);

            try {
                LogEntry entry = t.logDelete(function.getUser());
                if (entry != null) {
                    logEntryDAO.create(entry);
                }
            } catch (ConstraintViolationException e) {
                e.printStackTrace();
            }
        } else {
            throw new UnAuthorizedException();
        }
    }

    /**
     * Attempt to create the object t
     *
     * @param t the value of uuid will be ignored
     * @return the same object as t, but the uuid field will now have a valid value
     * @throws DataAccessException          something went wrong with the database
     * @throws UnAuthorizedException        The function does not have the rights to perform this operation.
     *                                      The object can be created if the user has the CREATE_ALL Action of the Resource
     *                                      or CREATE_MINE and the user is the owner of the object.
     * @throws ConstraintViolationException one or more fields of t are invalid
     */
    public T create(T t) throws DataAccessException, UnAuthorizedException, ConstraintViolationException {
        if (role.hasAccess(resource, CREATE_ALL) ||
                (role.hasAccess(resource, CREATE_MINE) && isOwner(t, function))) {
            T result = dao.create(t);

            try {
                LogEntry entry = t.logCreate(function.getUser());
                if (entry != null) {
                    logEntryDAO.create(entry);
                }
            } catch (ConstraintViolationException e) {
                e.printStackTrace();
            }

            return result;
        } else {
            throw new UnAuthorizedException();
        }
    }

    /**
     * Attempt to get the object of type T that has the UUID uuid
     *
     * @param t object that has to be updated. All fields can be different from what the fields are in the database except the uuid.
     * @return Object of type T corresponding with the uuid
     * @throws DataAccessException          something went wrong with the database
     * @throws UnAuthorizedException        The function does not have the rights to perform this operation.
     *                                      The object can be updated if the user has the UPDATE_ALL Action of the Resource
     *                                      or READ_MINE and the user is the owner of the original object and the updated object.
     * @throws ObjectNotFoundException      there is no object with the same id as t.id
     * @throws ConstraintViolationException one more fields of t are invalid
     */
    public T update(T t) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException, ConstraintViolationException {
        T tOld = dao.get(t.getUuid());
        EditableObject copy = tOld.copy();
        if (role.hasAccess(resource, UPDATE_ALL) ||
                (role.hasAccess(resource, UPDATE_MINE) &&
                        isOwner(tOld, function) &&
                        isOwner(t, function))) {
            T result = dao.update(t);

            try {
                LogEntry entry = t.logUpdate(function.getUser(), copy);
                if (entry != null) {
                    logEntryDAO.create(entry);
                }
            } catch (ConstraintViolationException e) {
                e.printStackTrace();
            }

            return result;
        } else {
            throw new UnAuthorizedException();
        }
    }

    protected DAO<T> getDao() {
        return dao;
    }

}
