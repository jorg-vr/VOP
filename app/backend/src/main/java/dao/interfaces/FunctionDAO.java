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
     * Creates a new Function
     *
     * @param company   the company
     * @param role      the role
     * @param account   the account
     * @param startDate the date when the given function applied first
     * @param endDate   the date when the applied applied function ends
     * @return A new function
     * @throws DataAccessException thrown when wrong data is given or the object can't be created
     */
    @Deprecated
    Function create(Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException;

    /**
     * Creates an exiting Function
     *
     * @param id        the id of the object
     * @param company   the company
     * @param role      the role
     * @param account   the account
     * @param startDate the date when the given function applied first
     * @param endDate   the date when the applied applied function ends
     * @return the updated function
     * @throws DataAccessException thrown when wrong data is given or the object can't be updated
     */
    @Deprecated
    Function update(UUID id, Company company, Role role, Account account, LocalDateTime startDate, LocalDateTime endDate) throws DataAccessException;

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
