package dao.interfaces;


import java.util.Collection;
import java.util.UUID;

/**
 *
 */
public interface DAO<T> extends AutoCloseable {


    T get(UUID id) throws DataAccessException;

    void remove(UUID id) throws DataAccessException;

    Collection<T> listFiltered(Filter<T>... filters) throws DataAccessException;

    @Override
    void close();
}
