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
import spring.Exceptions.NotImplementedException;
import spring.model.RESTSchema;
import spring.model.RESTUser;

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
 * TODO: more more exceptions
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
     * TODO filters
     */
    @RequestMapping(method = RequestMethod.GET)
    public RESTSchema<RESTUser> get(Integer page, Integer limit) {
        Collection<RESTUser> users = new ArrayList<>();

        try {
            Collection<Account> accounts = accountController.getAll();
            for (Account account : accounts) {
                Person person = account.getPerson();
                users.add(merge(person, account));
            }
        } catch (DataAccessException e) {
            // This should not happen unless there is something wrong with the database
            System.err.println("Something is wrong with the database");
            e.printStackTrace();
        }
        return new RESTSchema<>(users, page, limit, PATH_USER+"?");
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

            return merge(person, account);
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

            return merge(person, account);
        } catch (DataAccessException | NumberFormatException e) {
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
            return merge(person, account);
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

    /**
     * Merges a person and account object to 1 RESTUser object
     *
     * @param person
     * @param account
     * @return object that has been created from the values of person and account
     */
    private RESTUser merge(Person person, Account account) {
        String id = UUIDUtil.UUIDToNumberString(account.getUuid());
        String firstName=person!=null?person.getFirstName():null;
        String lastName=person!=null?person.getLastName():null;
        String email=person!=null?person.getEmail():null;
        RESTUser user = new RESTUser();
        user.setId(id);
        user.setPassword(account.getHashedPassword());
        //user.setUpdatedAt(LocalDateTime.now());
        //user.setCreatedAt(LocalDateTime.now());
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setUrl(PATH_USER + "/" + id);
        return user;
    }
}
