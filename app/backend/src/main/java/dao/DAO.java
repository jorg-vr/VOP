package dao;


import java.util.Collection;
import java.util.UUID;

/**
 * 
 */
public interface DAO<T> {

    T get(UUID id) throws DataAccessException;

    void update(T t) throws DataAccessException;

    void remove(T t) throws DataAccessException;

    Collection<T> listFiltered(Filter... filters) throws DataAccessException;
}
