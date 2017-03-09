package dao.test;

import dao.interfaces.AccountDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Account;
import model.identity.Identity;
import spring.Exceptions.NotImplementedException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Billie Devolder on 8/03/2017.
 */
public class TestAccountDAO implements AccountDAO {

    private static Map<String, Account> accounts = new HashMap<>();

    static {
        Account a0 = new Account();
        a0.setLogin("jan.janssens@mail.com");
        a0.setHashedPassword("wachtwoord123");
        accounts.put(a0.getLogin(), a0);

        Account a1 = new Account();
        a1.setLogin("rik.peeters@mail.com");
        a1.setHashedPassword("ea14J489M");
        accounts.put(a1.getLogin(), a1);
    }

    @Override
    public Account get(String name) throws DataAccessException {
        if (!accounts.containsKey(name)) {
            throw new DataAccessException();
        }
        return accounts.get(name);
    }

    @Override
    public void remove(String name) throws DataAccessException {
        if (!accounts.containsKey(name)) {
            throw new DataAccessException();
        }
        accounts.remove(name);
    }

    @Override
    public Account create(Account account) throws DataAccessException {
        if (accounts.containsKey(account.getLogin())) {
            throw new DataAccessException();
        }
        accounts.put(account.getLogin(), account);
        account.setUuid(UUID.randomUUID());
        return account;
    }

    @Override
    public Account get(UUID id) throws DataAccessException {
        throw new NotImplementedException();
    }

    @Override
    public void update(Account account) throws DataAccessException {
        if (!accounts.containsKey(account.getLogin())) {
            throw new DataAccessException();
        }
        accounts.put(account.getLogin(), account);
    }

    @Override
    public void remove(Account account) throws DataAccessException {
        if (!accounts.containsKey(account.getLogin())) {
            throw new DataAccessException();
        }
        accounts.remove(account.getLogin(), account);
    }

    @Override
    public Collection<Account> listFiltered(Filter... filters) throws DataAccessException {
        return null;
    }

    @Override
    public void close() {

    }

    @Override
    public Filter<Account> bySecurity(String login, String password) {
        return null;
    }

    @Override
    public Filter<Account> byIdentity(Identity identity) {
        return null;
    }
}
