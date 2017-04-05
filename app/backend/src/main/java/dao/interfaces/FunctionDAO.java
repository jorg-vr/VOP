package dao.interfaces;

import model.account.Account;
import model.account.Function;
import model.account.Role;
import model.identity.Company;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

/**
 * DAO for the bean Function
 * Created by sam on 3/7/17.
 */
public interface FunctionDAO extends DAO<Function> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all functions the given account has.
     *
     * @param account The account to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Function> byAccount(Account account);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all functions the given company has.
     *
     * @param company The company to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Function> byCompany(Company company);
}
