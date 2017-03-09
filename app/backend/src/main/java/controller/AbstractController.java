package controller;

import dao.interfaces.DAO;
import dao.interfaces.DataAccessException;
import model.identity.Person;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by Billie Devolder on 9/03/2017.
 */
public class AbstractController<T> {

    private DAO<T> dao;

    public AbstractController(DAO<T> dao) {
        this.dao = dao;
    }

    public T get(UUID uuid) throws DataAccessException {
        return dao.get(uuid);
    }

    public Collection<T> getAll() throws DataAccessException {
        return dao.listFiltered();

    }

    public void update(T t) throws DataAccessException {
        dao.update(t);
    }

    public void archive(T t) throws DataAccessException {
        dao.remove(t);
    }

}
