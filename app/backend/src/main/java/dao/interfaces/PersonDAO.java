package dao.interfaces;

import model.identity.Person;

import java.util.UUID;

/**
 * DAO for the bean Company extending IdentityDAO
 * Created by sam on 3/7/17.
 */
public interface PersonDAO extends IdentityDAO<Person> {

    /**
     * Creates a new Person
     * @param email should be unique
     * @param firstName the firstname
     * @param lastName the lastname
     * @return a new Person object with a valid UUID
     * @throws DataAccessException thrown when unique constraints are violated or the object can't be created
     */
    @Deprecated
    Person create(String firstName, String lastName, String email) throws DataAccessException;

    /**
     * Updates an existing Person
     * @param id id of the object
     * @param email should be unique
     * @param firstName the firstname
     * @param lastName the lastname
     * @return a new Person object with a valid UUID
     * @throws DataAccessException thrown when unique constraints are violated or the object can't be created
     */
    @Deprecated
    Person update(UUID id, String firstName, String lastName, String email) throws DataAccessException;

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all persons having a name that contains the given name.
     *
     * @param name The name to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Person> nameContains(String name);




}
