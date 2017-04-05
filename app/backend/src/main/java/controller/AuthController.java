package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.interfaces.AccountDAO;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.account.Function;
import spring.model.AuthenticationToken;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by jorg on 3/30/17.
 */
public class AuthController implements  AutoCloseable{
    public Function getFunction(AuthenticationToken token, UUID functionId) throws DataAccessException, UnAuthorizedException {
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAccountId());
        Function function= ProductionProvider.getInstance().getFunctionDAO().get(functionId);
        if(token.getExpire().isAfter(LocalDateTime.now())&&function.getAccount().equals(account)){
            return function;
        }else{
            throw new UnAuthorizedException();
        }
    }

    public Collection<Function> getFunctions(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAccountId());
        if(token.getExpire().isAfter(LocalDateTime.now())){
            return account.getFunctions();
        }else{
            throw new UnAuthorizedException();
        }
    }

    public AuthenticationToken getToken(String login,String password)throws DataAccessException, UnAuthorizedException{
        AccountDAO accountDAO=ProductionProvider.getInstance().getAccountDao();
        Account account=accountDAO.listFiltered(accountDAO.bySecurity(login,password)).iterator().next();
        return new AuthenticationToken(account.getUuid());
    }

    public AuthenticationToken refreshToken(AuthenticationToken token)throws DataAccessException, UnAuthorizedException{
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAccountId());
        if(token.getExpire().isAfter(LocalDateTime.now())){
            return new AuthenticationToken(account.getUuid());
        }else{
            throw new UnAuthorizedException();
        }
    }

    @Override
    public void close() {

    }
}
