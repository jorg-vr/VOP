package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
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
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAcountId());
        Function function= ProductionProvider.getInstance().getFunctionDAO().get(functionId);
        if(token.getExpire().isAfter(LocalDateTime.now())&&function.getAccount().equals(account)&&account.validatePassword(token.getHash())){
            return function;
        }else{
            throw new UnAuthorizedException();
        }
    }

    public Collection<Function> getFunctions(AuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAcountId());
        if(token.getExpire().isAfter(LocalDateTime.now())&&account.validatePassword(token.getHash())){
            return account.getFunctions();
        }else{
            throw new UnAuthorizedException();
        }
    }

    public AuthenticationToken getToken(String login,String password){
        return ProductionProvider.getInstance().getAccountDao().bySecurity(login,password);
    }

    @Override
    public void close() throws Exception {

    }
}
