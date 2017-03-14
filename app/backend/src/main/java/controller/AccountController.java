package controller;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.test.TestAccountDAO;
import dao.test.TestDAOProvider;
import main.BackendApplication;
import model.account.Account;
import model.identity.Person;
import spring.Exceptions.NotImplementedException;

import java.util.Collection;
import java.util.UUID;


public class AccountController extends AbstractController<Account> {

    private AccountDAO dao;
    private DAOProvider provider;

    public AccountController() {
        super(BackendApplication.PROVIDER.getAccountDao());
        this.provider = BackendApplication.PROVIDER;
        this.dao = provider.getAccountDao();
    }

    /**
     * Attempts to create a new account
     *
     * @param name
     * @param password
     * @param personId UUID of the person associated with this account
     * @return an account object with all the fields filled in
     * @throws DataAccessException name is already taken
     */
    public Account createAccount(String name, String password, UUID personId) throws DataAccessException {
        Person person = provider.getPersonDAO().get(personId);
        return dao.create(name, password, person);
    }

    public Account updateAccount(UUID id, String login, String hashedPassword) throws DataAccessException {
        return dao.update(id, login, hashedPassword);
    }

    /**
     * @param name the account name
     * @return true if the name is already taken
     */
    public boolean isTaken(String name) throws DataAccessException {
        for (Account account: dao.listFiltered()) {
            if (account.getLogin().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
