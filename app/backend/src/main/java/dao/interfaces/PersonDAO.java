package dao.interfaces;

import model.identity.Address;
import model.identity.Function;
import model.identity.Person;

import java.time.LocalDate;
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

    Person create(String firstName, String lastName, String email, String phonenumber, Address address) throws DataAccessException;
    /**
     * Creates a new Person
     * @return the Person object with the adjusted fields
     * @throws DataAccessException email already taken or does not exist
     */
    Person update(UUID id, String firstName, String lastName) throws DataAccessException;

    Person update(UUID id, String firstName, String lastName, String email, String phonenumber, Address address) throws DataAccessException;

    //Checks both first and last name
    Filter<Person> nameContains(String name);




}
