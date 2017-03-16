package dao.interfaces;

import model.identity.Person;

import java.util.UUID;

/**
 * Created by sam on 3/7/17.
 */
public interface PersonDAO extends IdentityDAO<Person> {

    /**
     * Creates a new Person
     * @param email should be unique
     * @return a new Person object with a valid UUID
     * @throws DataAccessException email already taken
     */
    Person create(String firstName, String lastName, String email) throws DataAccessException;

    Person update(UUID id, String firstName, String lastName, String email) throws DataAccessException;

    //Checks both first and last name
    Filter<Person> nameContains(String name);




}
