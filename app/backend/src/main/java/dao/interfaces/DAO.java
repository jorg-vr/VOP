package dao.interfaces;


import dao.exceptions.ConstraintViolationException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;

import java.util.Collection;
import java.util.UUID;

/**
 * DAO interface which all other DAO interfaces should extend
 * Created by sam on 3/7/17.
 */
public interface DAO<T>  {

    /**
     * Persists the given Object to the database and initializes not lazy loaded properties
     * @param t the object
     * @return the same object but some nonlazy properties might be loaded
     * @throws DataAccessException Thrown when something goes wrong in the database
     * @throws ConstraintViolationException Thrown when some properties violate the existing constraints
     */
    T create(T t) throws DataAccessException, ConstraintViolationException;

    /**
     * Updates the given Object to the database
     * @param t the object to update
     * @return the same object given to the method
     * @throws DataAccessException Thrown when something goes wrong in the database
     * @throws ConstraintViolationException Thrown when some properties violate the existing constraints
     */
    T update(T t) throws DataAccessException, ConstraintViolationException;


    /**
     * Retrieves an object using its unique id
     * @param id the id of the object
     * @return the object matching the given UUID
     * @throws DataAccessException Thrown when something goes wrong retrieving the object
     */
    T get(UUID id) throws DataAccessException, ObjectNotFoundException;

    /**
     * Removes an object using its unique id
     * @param id the id of the object
     * @throws DataAccessException Thrown when something goes wrong retrieving the object
     */
    void remove(UUID id) throws DataAccessException, ObjectNotFoundException;

    /**
     * Gets all objects of the DAO using filters
     * @param filters an array of filters to use (can be empty)
     * @return A collection of objects which satisfy the given filters
     * @throws DataAccessException Thrown when the objects cannot be retrieved
     */
    Collection<T> listFiltered(Filter<T>... filters) throws DataAccessException;

}
