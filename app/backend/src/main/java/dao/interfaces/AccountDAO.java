package dao.interfaces;

import model.account.Account;
import model.identity.Identity;

/**
 * Created by sam on 3/7/17.
 */
public interface AccountDAO extends DAO<Account> {
    Filter<Account> bySecurity(String login, String password);
    Filter<Account> byIdentity(Identity identity);
}
