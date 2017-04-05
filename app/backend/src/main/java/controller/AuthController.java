package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.UserDAO;
import model.account.Function;
import model.account.User;
import spring.model.AuthenticationToken;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthController implements AutoCloseable {
    public Function getFunction(AuthenticationToken token, UUID functionId) throws DataAccessException, UnAuthorizedException {
        User user = ProductionProvider.getInstance().getUserDAO().get(token.getAcountId());
        Function function = ProductionProvider.getInstance().getFunctionDAO().get(functionId);
        if (token.getExpire().isAfter(LocalDateTime.now()) && function.getUser().equals(user) && user.validatePassword(token.getHash())) {
            return function;
        } else {
            throw new UnAuthorizedException();
        }
    }

    public Collection<Function> getFunctions(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        User user = ProductionProvider.getInstance().getUserDAO().get(token.getAcountId());
        if (token.getExpire().isAfter(LocalDateTime.now()) && user.validatePassword(token.getHash())) {
            return user.getFunctions();
        } else {
            throw new UnAuthorizedException();
        }
    }

    public AuthenticationToken getToken(String login, String password) throws DataAccessException, UnAuthorizedException {
        UserDAO userDAO = ProductionProvider.getInstance().getUserDAO();
        User user = null; // TODO accountDAO.listFiltered(accountDAO.bySecurity(login,password)).iterator().next();
        return new AuthenticationToken(user.getUuid(), user.getPassword());
    }

    public AuthenticationToken refreshToken(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        User user = ProductionProvider.getInstance().getUserDAO().get(token.getAcountId());
        if (token.getExpire().isAfter(LocalDateTime.now()) && user.validatePassword(token.getHash())) {
            return new AuthenticationToken(user.getUuid(), user.getPassword());
        } else {
            throw new UnAuthorizedException();
        }
    }

    @Override
    public void close() {

    }
}
