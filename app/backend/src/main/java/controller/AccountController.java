package controller;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import model.account.Account;

/**
 * This class acts as a protecting interface of backend model
 * methods should in final state take care of:
 *      1) constraint issues
 *      2) history changes (not yet implemented) TODO milestone?
 *      3) correct authentication (not yet implemented) TODO milestone?
 */
public class AccountController {

    private DAOProvider provider;

    private AccountDAO dao;

    public AccountController(DAOProvider provider) {
        this.provider = provider;
        this.dao = null; // TODO get the dao from provider
    }

    /**
     * Attempts to create a new account
     * @param name
     * @param password
     * @return  an account object with all the fields filled in
     * @throws DataAccessException      something went wrong when communicating with the database
     * @throws IllegalArgumentException name has already been taken
     */
    public Account createAccount(String name, String password) throws DataAccessException, IllegalArgumentException {
        return null;
    }

    /**
     * Attempts to get the account with the given name
     * @throws IllegalArgumentException there is no account associated with that name
     */
    public Account getAccount(String name) throws DataAccessException, IllegalArgumentException {
        return null;
    }

    /**
     * Attempts to archive the account with the given name
     * @throws IllegalArgumentException there is no account associated with that name
     */
    public void archiveAccount(String name) throws DataAccessException, IllegalArgumentException {

    }
}
