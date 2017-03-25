package spring.controller;

import controller.AccountController;
import controller.PersonController;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.identity.Person;
import org.springframework.web.bind.annotation.*;
import spring.Exceptions.ConflictException;
import spring.Exceptions.InvalidInputException;
import spring.Exceptions.NotFoundException;
import spring.model.RESTSchema;
import spring.model.RESTUser;

import java.util.*;

/**
 * This controller is responsible for handling the HTTP requests of the URL /user.
 * Currently, the following HTTP requests are supported:
 * 1) GET /user
 * 2) GET /user/{id}
 * 3) POST /user
 * 4) PUT /user/{id}
 * 5) DELETE /user/{id}
 * <p>
 * This controller is responsible for translating the RESTModels to the backend specific models and calling the appropriate methods
 * of the spring independent controllers,  located in the controller package.
 * It is also responsible for translating the backend specific exceptions to HTPP repsonse codes.
 * <p>
 * For more information about what the HTTP requests do, see the API specification
 */
@RestController
@RequestMapping("/users")
public class RESTUserController {

    public static final String PATH_USER = "/users";

    private AccountController accountController = new AccountController();

    private PersonController personController = new PersonController();


    /**
     * @return a collection of all the users in the system.
     * If there are no users, an empty collection will be returned.
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTUser> get(String email,
                                    String firstName,
                                    String lastName,
                                    Integer page,
                                    Integer limit) {
        Collection<RESTUser> users = new ArrayList<>();

        try {
            Collection<Account> accounts = accountController.getAll();
            for (Account account : accounts) {
                Person person = account.getPerson();
                if (passesFilters(person, email, firstName, lastName)) {
                    users.add(new RESTUser(account, person));
                }
            }
        } catch (DataAccessException e) {
            // This should not happen unless there is something wrong with the database
            System.err.println("Something is wrong with the database");
            e.printStackTrace();
        }
        return new RESTSchema<>(users, page, limit, PATH_USER + "?");
    }

    /**
     * This method checks if the person passes the filters.
     *
     * @param person    the person that should be checked
     * @param firstName can be null
     * @param lastName  can be null
     * @param email     can be null
     * @return whether the person passes the filters or not
     */
    private boolean passesFilters(Person person, String email, String firstName, String lastName) {
        return containsLowerCase(person.getEmail(), email)
                && containsLowerCase(person.getFirstName(), firstName)
                && containsLowerCase(person.getLastName(), lastName);
    }

    /**
     * @param toCheck the string that should be tested
     * @param filter  if null, true will be returned
     * @return returns true if toCheck contains filter (case insensitive) or true if filter is null
     */
    private boolean containsLowerCase(String toCheck, String filter) {
        return filter == null || toCheck.toLowerCase().contains(filter.toLowerCase());
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
            if (accountController.isTaken(user.getEmail())) {
                throw new ConflictException();
            }

            Person person = personController.createPerson(user.getFirstName(), user.getLastName(), user.getEmail());
            Account account = accountController.createAccount(user.getEmail(), user.getPassword(), person.getUuid());

            return new RESTUser(account, person);
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
            UUID uuid = UUIDUtil.toUUID(id);
            Account account = accountController.get(uuid);
            Person person = account.getPerson();

            return new RESTUser(account, person);
        } catch (DataAccessException | NumberFormatException | NullPointerException e) {
            throw new NotFoundException();
        }
    }

    /**
     * Attempts to updateId the user with the given id.
     *
     * @param id   id of the user that should be updated
     * @param user fields that can be changed:
     *             1) firstName
     *             2) lastName
     *             3) password
     * @return the updated RESTUser object. The updatedAt will be updated.
     */
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public RESTUser putId(@PathVariable("id") String id, @RequestBody RESTUser user) {
        UUID uuid = UUIDUtil.toUUID(id);
        try {
            Account account = accountController.updateAccount(uuid, user.getEmail(), user.getPassword());
            Person person = account.getPerson();
            person = personController.updatePerson(person.getUuid(), user.getFirstName(), user.getLastName(), user.getEmail());
            return new RESTUser(account, person);
        } catch (DataAccessException e) {
            throw new InvalidInputException();
        }
    }

    /**
     * Attempts to archive the user with the given id.
     *
     * @param id the id of the user that should be archived
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
        UUID uuid = UUIDUtil.toUUID(id);

        try {
            Account account = accountController.get(uuid);
            accountController.archive(account.getUuid());
            personController.archive(account.getPerson().getUuid());
        } catch (DataAccessException e) {
            throw new NotFoundException();
        }
    }
}
