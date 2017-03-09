package controller;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.test.TestAccountDAO;
import model.account.Account;

/**
 * This class acts as a protecting interface of backend model
 * methods should in final state take care of:
 * 1) constraint issues
 * 2) history changes (not yet implemented) TODO milestone?
 * 3) correct authentication (not yet implemented) TODO milestone?
 */
public class AccountController {

    private DAOProvider provider;

    private AccountDAO dao;

    public AccountController(DAOProvider provider) {
        this.provider = provider;
        this.dao = new TestAccountDAO(); // TODO get the dao from provider
    }

    /**
     * Attempts to create a new account
     *
     * @param name
     * @param password
     * @return an account object with all the fields filled in
     * @throws DataAccessException name is already taken
     */
    public Account createAccount(String name, String password) throws DataAccessException {
        Account account = new Account();
        account.setLogin(name);
        account.setHashedPassword(password);
        account = dao.create(account);
        return account;
    }

    /**
     * Attempts to get the account with the given name
     *
     * @throws DataAccessException there is no account associated with that name
     */
    public Account getAccount(String name) throws DataAccessException {
        return dao.get(name);
    }

    public void updateAccount(Account account) throws DataAccessException {
        dao.update(account);
    }

    /**
     * Attempts to archive the account with the given name
     *
     * @throws DataAccessException there is no account associated with that name
     */
    public void archiveAccount(String name) throws DataAccessException {
        dao.remove(name);
    }

    /**
     * @param name the account name
     * @return true if the name is already taken
     */
    public boolean isTaken(String name) {
        boolean taken = true;
        try {
            dao.get(name);
        } catch (DataAccessException e) {
            taken = false;
        }
        return taken;
    }
}
