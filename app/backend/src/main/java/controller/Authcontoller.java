package controller;

import controller.exceptions.UnAuthorizedException;
import dao.database.ProductionProvider;
import dao.interfaces.DataAccessException;
import model.account.Account;
import model.account.Function;
import spring.model.RESTAuthenticationToken;

import java.time.LocalDateTime;

/**
 * Created by jorg on 3/30/17.
 */
public class Authcontoller {
    public Function getFunction(RESTAuthenticationToken token) throws DataAccessException, UnAuthorizedException {
        Account account= ProductionProvider.getInstance().getAccountDao().get(token.getAcountId());
        Function function= ProductionProvider.getInstance().getFunctionDAO().get(token.getFunctionId());
        if(token.getExpire().isAfter(LocalDateTime.now())&&function.getAccount().equals(account)&&account.validatePassword(token.getHash())){
            return function;
        }else{
            throw new UnAuthorizedException();
        }
    }

}
