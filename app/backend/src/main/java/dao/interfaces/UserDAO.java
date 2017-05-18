package dao.interfaces;

import model.account.User;

/**
 * DAO for bean User
 * Created by Billie Devolder on 5/04/2017.
 */
public interface UserDAO extends DAO<User> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Users having the given name.
     *
     * @param firstName The name to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<User> byFirstName(String firstName);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Users having the given name.
     *
     * @param lastName The name to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<User> byLastName(String lastName);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Users having the given email.
     *
     * @param email The email to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<User> byEmail(String email);

    /**
     * Returns a user, when the right credentials are given
     *
     * @param login The login to use
     * @return the account or null when no match
     */
    User getUserByLogin(String login);
}
