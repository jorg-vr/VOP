package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.interfaces.DAOProvider;
import dao.interfaces.DataAccessException;
import dao.interfaces.FunctionDAO;
import dao.interfaces.UserDAO;
import main.BackendApplication;
import model.account.*;
import spring.model.AuthenticationToken;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthController implements AutoCloseable {

    private DAOProvider provider = BackendApplication.getProvider();

    private FunctionDAO functionDAO=provider.getFunctionDAO();
    private  UserDAO userDAO=provider.getUserDAO();

    public Function getFunction(AuthenticationToken token, UUID functionId) throws DataAccessException, UnAuthorizedException {
        User account = userDAO.get(token.getAccountId());
        Function function = functionDAO.get(functionId);
        init(function);
        if (function.getUser().equals(account)) {
            return function;
        } else {
            throw new UnAuthorizedException();
        }
    }

    public User getUser(AuthenticationToken token) throws DataAccessException {
        return provider.getUserDAO().get(token.getAccountId());
    }

    public Collection<Function> getFunctions(AuthenticationToken token) throws DataAccessException {
        User user = getUser(token);
        return user.getFunctions();
    }

    public AuthenticationToken getToken(String login, String password) throws DataAccessException, UnAuthorizedException {
        User account = userDAO.getUserByLogin(login, password);
        return new AuthenticationToken(account.getUuid());
    }

    public AuthenticationToken refreshToken(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        User user = userDAO.get(token.getAccountId());
        return new AuthenticationToken(user.getUuid());
    }

    private void init(Function function){
        function.getCompany();
        for(Action action:Action.values()){
            for(Resource resource: Resource.values()){
                function.getRole().hasAccess(resource,action);
            }
        }
        function.getUser();
    }

    @Override
    public void close() {
        try {
            userDAO.close();
            functionDAO.close();
        } catch (Exception e) {
            //TODO
            e.printStackTrace();
        }
    }
}
