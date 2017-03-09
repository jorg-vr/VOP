package controller;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.identity.Person;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Billie Devolder on 9/03/2017.
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
