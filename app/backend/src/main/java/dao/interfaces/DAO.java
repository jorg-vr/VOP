package dao.interfaces;


import java.util.Collection;
import java.util.UUID;

/**
 * DAO interface which all other DAO interfaces should extend
 * Created by sam on 3/7/17.
 */
public interface DAO<T> extends AutoCloseable {

    T create(T t) throws DataAccessException;

    T update(T t) throws DataAccessException;


    /**
     * Retrieves an object using its unique id
     * @param id the id of the object
     * @return the object matching the given UUID
     * @throws DataAccessException Thrown when something goes wrong retrieving the object
     */
    T get(UUID id) throws DataAccessException;

    /**
     * Removes an object using its unique id
     * @param id the id of the object
     * @throws DataAccessException Thrown when something goes wrong retrieving the object
     */
    void remove(UUID id) throws DataAccessException;

    /**
     * Gets all objects of the DAO using filters
     * @param filters an array of filters to use (can be empty)
     * @return A collection of objects which satisfy the given filters
     * @throws DataAccessException Thrown when the objects cannot be retrieved
     */
    Collection<T> listFiltered(Filter<T>... filters) throws DataAccessException;

<<<<<<< HEAD
    T create(T t)throws DataAccessException;

    T update(T t)throws DataAccessException;
=======
>>>>>>> master

}
