package dao.interfaces;

import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * Created by sam on 3/10/17.
 */
public interface FunctionDAO extends DAO<Function> {

    Function create(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException;

    @Deprecated
    Function update(UUID id, LocalDateTime endDate) throws DataAccessException;

    Filter<Function> byAccount(Account account);
    Filter<Function> byCompany(Company company);
}
