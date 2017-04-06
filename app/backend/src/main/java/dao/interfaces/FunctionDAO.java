package dao.interfaces;

import model.account.Function;
import model.account.User;
import model.identity.Company;

/**
 * DAO for the bean Function
 * Created by sam on 3/7/17.
 */
public interface FunctionDAO extends DAO<Function> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all functions the given account has.
     *
     * @param user The User to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Function> byUser(User user);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all functions the given company has.
     *
     * @param company The company to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Function> byCompany(Company company);
}
