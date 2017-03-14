package dao.interfaces;

import model.account.Account;
import model.identity.Identity;
import model.identity.Person;

import java.util.UUID;

/**
 * Created by sam on 3/7/17.
 */
public interface AccountDAO extends DAO<Account> {

    /**
     * Creates a new account
     * @param login should be unique
     * @param person person belonging to this account
     * @return account object with valid fields
     * @throws DataAccessException login already taken
     */
    Account create(String login, String hashedPassword, Person person) throws DataAccessException;

    Account update(UUID id, String login, String hashedPassword) throws DataAccessException;

    Filter<Account> bySecurity(String login, String password);
    Filter<Account> byPerson(Person identity);
}
