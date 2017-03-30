package controller;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;

import java.util.Collection;
import java.util.UUID;

/**
 * This class and it's subclasses are framework independent controller classes.
 * In their final state, these classes should act as a protecting interface of the backend model.
 * The methods of these classes should in final state take care of:
 * 1) history changes (not yet implemented) TODO milestone?
 * 2) correct authentication (not yet implemented) TODO milestone?
 * 3) business logic (currently nothing)
 *
 * Currently there is a generic implementation for the get, archive and listFiltered methods.
 */
public abstract class AbstractController<T> {

    private DAO<T> dao;

    /**
     * @param dao a DAO object on which the get, listFiltered and archive methods should be called.
     */
    public AbstractController(DAO<T> dao) {
        this.dao = dao;
    }

    public DAO<T> getDao(){
        return dao;
    }

    public T get(UUID uuid) throws DataAccessException {
        return dao.get(uuid);
    }

    public Collection<T> getAll(Filter... filters) throws DataAccessException {
        return dao.listFiltered(filters);

    }

    public void archive(UUID uuid) throws DataAccessException {
        dao.remove(uuid);
    }

    public T create(T t)throws DataAccessException{
        return dao.create(t);
    }

    public T update(T t)throws DataAccessException{
        return dao.update(t);
    }

}
