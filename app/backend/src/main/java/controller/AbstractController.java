package controller;

import dao.interfaces.DAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import dao.test.TestDAOProvider;
import model.identity.Person;

import java.util.Collection;
import java.util.UUID;

/**
 * This class acts as a protecting interface of backend model
 * methods should in final state take care of:
 * 1) constraint issues
 * 2) history changes (not yet implemented) TODO milestone?
 * 3) correct authentication (not yet implemented) TODO milestone?
 *
 * Currently there is a generic implementation for the 4 CRUD methods.
 */
public abstract class AbstractController<T> {

    private DAO<T> dao;

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

    public void update(T t) throws DataAccessException {
        dao.update(t);
    }

    public void archive(UUID uuid) throws DataAccessException {
        dao.remove(get(uuid));
    }

}
