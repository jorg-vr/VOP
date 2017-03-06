package src.main.java.dao;


import java.util.Collection;

/**
 * TODO: should a create(T t) method be added?
 */
public interface DAO<T> {

    T get(int id) throws DataAccessException;

    void update(T t) throws DataAccessException;

    void remove(T t) throws DataAccessException;

    Collection<T> listFiltered(Filter... filters) throws DataAccessException;
}
