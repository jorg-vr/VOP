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
        User account= ProductionProvider.getInstance().getUserDAO().get(token.getAccountId());
        Function function= ProductionProvider.getInstance().getFunctionDAO().get(functionId);
        if(function.getUser().equals(account)){
            return function;
        } else {
            throw new UnAuthorizedException();
        }
    }

    public Collection<Function> getFunctions(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        User user= ProductionProvider.getInstance().getUserDAO().get(token.getAccountId());
        return user.getFunctions();
    }

    public AuthenticationToken getToken(String login,String password)throws DataAccessException, UnAuthorizedException{
        UserDAO userDAO=ProductionProvider.getInstance().getUserDAO();
        System.out.println("login:"+login);
        System.out.println("password:"+password);
        for(User user:userDAO.listFiltered()){
            System.out.println(user.getEmail());
        }
        User account= userDAO.getUserByLogin(login,password);
        return new AuthenticationToken(account.getUuid());
    }

    public AuthenticationToken refreshToken(AuthenticationToken token)throws DataAccessException, UnAuthorizedException{
        User user= ProductionProvider.getInstance().getUserDAO().get(token.getAccountId());
        return new AuthenticationToken(user.getUuid());
    }

    @Override
    public void close() {

    }
}
