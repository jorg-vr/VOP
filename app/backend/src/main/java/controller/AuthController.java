package controller;

import controller.exceptions.InvalidTokenException;
import controller.exceptions.UnAuthorizedException;
import dao.exceptions.DataAccessException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.interfaces.UserDAO;
import main.BackendApplication;
import model.account.User;
import org.jasypt.util.password.StrongPasswordEncryptor;
import spring.model.AuthenticationToken;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthController implements AutoCloseable {

    private DAOManager provider;
    private UserDAO userDAO;

    public AuthController() {
        provider = BackendApplication.getProvider().getDaoManager();
        userDAO = provider.getUserDAO();
    }

    public User getUser(AuthenticationToken token) throws DataAccessException, ObjectNotFoundException {
        return userDAO.get(token.getAccountId());
    }

    /**
     *
     * @param login email of the user that wants to log in
     * @param password plain text password of the user
     * @return an authentication token for the user
     * @throws DataAccessException something went wrong with the database
     * @throws UnAuthorizedException There is no user with that login or the password does not match
     */
    public AuthenticationToken getToken(String login, String password) throws DataAccessException, UnAuthorizedException {
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        User account = userDAO.getUserByLogin(login);
        System.out.println(password);
        if (account == null || ! passwordEncryptor.checkPassword(password, account.getPassword())) {
            throw new InvalidTokenException();
        }
        return new AuthenticationToken(account.getUuid());
    }

    public AuthenticationToken refreshToken(AuthenticationToken token) throws DataAccessException, UnAuthorizedException, ObjectNotFoundException {
        User user = userDAO.get(token.getAccountId());
        return new AuthenticationToken(user.getUuid());
    }


    @Override
    public void close() {
        provider.close();
    }
}
