package dao.interfaces;

import model.account.Account;
import model.identity.Identity;

/**
 * Created by sam on 3/7/17.
 */
public interface AccountDAO extends DAO<Account> {

    void get(String name) throws DataAccessException;

    void remove(String name) throws DataAccessException;

    Filter<Account> bySecurity(String login, String password);
    Filter<Account> byIdentity(Identity identity);
}
