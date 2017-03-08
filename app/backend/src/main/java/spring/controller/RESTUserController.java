package spring.controller;

import org.springframework.web.bind.annotation.*;
import spring.model.RESTUser;

import java.time.LocalDate;
import java.util.*;

/**
 * This class is responsible for handling all the HTTP requests of the URL /users
 * There are 5 different HTTP requests for this URL:
 * 1) /users GET
 * 2) /users POST
 * 3) /users/{id} GET
 * 4) /users/{id} PUT
 * 5) /users/{id} DELETE
 * TODO: exceptions
 */
@RestController
@RequestMapping("/users")
public class RESTUserController {

    /**
     * @return a collection of all the users in the system.
     * If there are no users, an empty collection will be returned.
     */
    @RequestMapping(method = RequestMethod.GET)
    public Collection<RESTUser> get() {
        // Get the account with login = email

        // Get the person with id = id

        // Merge the 2 objects
        return null;
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
        // Create the Person

        // Create the Account
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
        return null;
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
        return null;
    }

    /**
     * Attempts to archive the user with the given id.
     *
     * @param id the id of the user that should be archived
     */
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteId(@PathVariable("id") String id) {
    }

}
