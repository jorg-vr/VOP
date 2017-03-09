package spring.controller;

import com.sun.javaws.exceptions.InvalidArgumentException;
import controller.AccountController;
import controller.PersonController;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.identity.Person;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.ConflictException;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.Exceptions.NotImplementedException;
import spring.model.RESTUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * This class is responsible for handling all the HTTP requests of the URL /users
 * There are 5 different HTTP requests for this URL:
 * 1) /users GET
 * 2) /users POST
 * 3) /users/{id} GET
 * 4) /users/{id} PUT
 * 5) /users/{id} DELETE
 * TODO: more exceptions
 */
@RestController
@RequestMapping("/users")
public class RESTUserController {

    private AccountController accountController = new AccountController(null);

    private PersonController personController = new PersonController(null);


    /**
     * @return a collection of all the users in the system.
     * If there are no users, an empty collection will be returned.
     * TODO filters
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTUser> get() {
        Collection<RESTUser> users = new ArrayList<>();

        try {
            Collection<Person> persons = personController.getAll();

            for (Person person : persons) {

                Account account = accountController.getAccount(person.getEmail());
                RESTUser user = merge(person.getUuid(), person, account);
                users.add(user);
            }
        } catch (DataAccessException e) {
            // This should not happen unless there is something wrong with the database
            System.err.println("Something is wrong with the database");
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Adds a new user to the system.
     *
     * @param user all the fields of this object are required except:
     *             1) id
     *             2) createdAt
     *             3) updatedAt
     *             4) url
     *             If there is any information in these fields, it will be ignored.
     * @return the object will be returned, with valid values for the not required field listed below:
     * 1) id:          this will be an unique UUID
     * 2) createdAt:   will be set to the moment when it was created
     * 3) updatedAt:   will be equal to createdAt
     * 4) url:         will look like: https//domain.org/users/id
     */
    @RequestMapping(method = RequestMethod.POST)
    public RESTUser post(@RequestBody RESTUser user) {
        try {

            // Check if the account name is still free
            if (accountController.nameTaken(user.getEmail())) {
                throw new ConflictException();
            }

            Account account = accountController.createAccount(user.getEmail(), user.getPassword());
            Person person = personController.createPerson(user.getFirstName(), user.getLastName(), user.getEmail(), null);

            return merge(person.getUuid(), person, account);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Returns the user associated with the id.
     *
     * @param id id of the user
     * @return the RESTUSer object
     */
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public RESTUser getId(@PathVariable("id") String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Person person = personController.get(uuid);
            Account account = accountController.getAccount(person.getEmail());

            return merge(person.getUuid(), person, account);
        } catch (DataAccessException | NumberFormatException e) {
            throw new NotFoundException();
        }
    }

    /**
     * Attempts to update the user with the given id.
     *
     * @param id   id of the user that should be updated
     * @param user a RESTUser object containing the updated fields.
     *             The following fields can not be changed:
     *             1) id
     *             2) createdAt
     *             3) updatedAt
     *             4) url
     *             Any changes of these fields will be ignored
     * @return the updated RESTUser object. The updatedAt will be updated.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTUser putId(@PathVariable("id") String id, @RequestBody RESTUser user) {
        UUID uuid;
        try {
            uuid = UUID.fromString(id);
        } catch (NumberFormatException e) {
            throw new NotFoundException();
        }
        try {
            Person person = personController.get(uuid);
            Account account = accountController.getAccount(person.getEmail());

            // TODO dit bespreken met jorg
            if (user.getFirstName() != null) {
                person.setFirstName(user.getFirstName());
            }
            if (user.getLastName() != null) {
                person.setLastName(user.getLastName());
            }
            if (user.getPassword() != null) {
                account.setHashedPassword(user.getPassword());
            }

            String email = user.getEmail();
            if (email != null) {
                if (!email.equals(account.getLogin()) && accountController.nameTaken(email)) {
                    throw new ConflictException();
                }
                account.setLogin(email);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Attempts to archive the user with the given id.
     *
     * @param id the id of the user that should be archived
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUID.fromString(id);
        try {
            Person person = personController.get(uuid);

            accountController.archiveAccount(person.getEmail());
            personController.archive(person);
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }

    private RESTUser merge(UUID id, Person person, Account account) {
        RESTUser user = new RESTUser();
        user.setId(id + "");
        user.setPassword(account.getHashedPassword());
        user.setUpdatedAt(LocalDateTime.now()); // TODO needs to be in editable object
        user.setCreatedAt(LocalDateTime.now());
        user.setFirstName(person.getFirstName());
        user.setLastName(person.getLastName());
        user.setEmail(person.getEmail());
        user.setUrl("TODO"); // TODO
        return user;
    }
}
