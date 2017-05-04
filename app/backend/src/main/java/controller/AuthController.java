package controller;

import controller.exceptions.UnAuthorizedException;
import dao.exceptions.ObjectNotFoundException;
import dao.interfaces.DAOManager;
import dao.exceptions.DataAccessException;
import dao.interfaces.UserDAO;
import main.BackendApplication;
import model.account.*;
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

    public AuthenticationToken getToken(String login, String password) throws DataAccessException, UnAuthorizedException {
        User account = userDAO.getUserByLogin(login, password);
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
