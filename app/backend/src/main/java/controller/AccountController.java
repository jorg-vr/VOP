package controller;

import dao.interfaces.AccountDAO;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import main.BackendApplication;
import model.account.Account;
import model.account.Function;
import model.account.Resource;
import model.identity.Person;
import spring.exceptions.NotImplementedException;

import java.util.UUID;


/**
 * For more information of what this class does, see AbstractController
 */
public class AccountController extends AbstractController<Account> {

    private AccountDAO dao;
    private DAOProvider provider;

    public AccountController(Function function) {
        super(BackendApplication.getProvider().getAccountDao(), Resource.ACCOUNT,function);
        this.provider = BackendApplication.getProvider();
        this.dao = provider.getAccountDao();
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

    @Override
    public boolean isOwner(Account account, Function function) {
        return function.getAccount().equals(account);
    }
}
