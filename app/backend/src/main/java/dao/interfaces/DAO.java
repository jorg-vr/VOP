package dao.interfaces;


import java.util.Collection;
import java.util.UUID;

/**
 *
 */
public interface DAO<T> extends AutoCloseable {


    @Deprecated
    T create(T t) throws DataAccessException;

    T get(UUID id) throws DataAccessException;

    @Deprecated
    void update(T t) throws DataAccessException;

    @Deprecated
    void remove(T t) throws DataAccessException;

    void remove(UUID id) throws DataAccessException;

    Collection<T> listFiltered(Filter<T>... filters) throws DataAccessException;

    @Override
    void close();
}
