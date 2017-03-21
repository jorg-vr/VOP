package dao.interfaces;

import model.account.Account;
import model.identity.Identity;
import model.identity.Person;

import java.util.UUID;

/**
 * DAO for the bean Account
 * Created by sam on 3/7/17.
 */
public interface AccountDAO extends DAO<Account> {

    /**
     * Creates a new Account
     *
     * @param login          should be unique
     * @param hashedPassword DO NOT pass an unhashed password
     * @param person         The person who own this account
     * @return account object with valid fields
     * @throws DataAccessException thrown when the given parameters can't be updated
     */
    Account create(String login, String hashedPassword, Person person) throws DataAccessException;

    /**
     * Updates an existing account
     *
     * @param login          should be unique
     * @param hashedPassword DO NOT pass an unhashed password
     * @return account object with valid fields
     * @throws DataAccessException thrown when given id not exists or when the given parameters can't be updated
     */
    Account update(UUID id, String login, String hashedPassword) throws DataAccessException;

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all accounts with the given login and password (can only be one account)
     *
     * @param login    The login to use in the filter
     * @param password The password to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Account> bySecurity(String login, String password);

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all accounts who the given identity owns.
     *
     * @param identity The identity to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Account> byPerson(Person identity);
}
