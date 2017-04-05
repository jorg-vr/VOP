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
