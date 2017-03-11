package dao.interfaces;

import model.account.Account;
import model.account.Function;
import model.identity.Company;

import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/10/17.
 */
public interface FunctionDAO extends DAO<Function> {

    Filter<Function> byAccount(Account account);
    Filter<Function> byCompany(Company company);
}
