package controller;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.test.TestAccountDAO;
import dao.test.TestDAOProvider;
import main.BackendApplication;
import model.account.Account;
import spring.Exceptions.NotImplementedException;

/**
 * This class acts as a protecting interface of backend model
 * methods should in final state take care of:
 * 1) constraint issues
 * 2) history changes (not yet implemented) TODO milestone?
 * 3) correct authentication (not yet implemented) TODO milestone?
 */
public class AccountController extends AbstractController<Account> {

    private AccountDAO dao;

    public AccountController() {
        super(BackendApplication.PROVIDER.getAccountDao());
        this.dao = BackendApplication.PROVIDER.getAccountDao();
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
     * @param name the account name
     * @return true if the name is already taken
     */
    public boolean isTaken(String name) {
        throw new NotImplementedException();
    }
}
