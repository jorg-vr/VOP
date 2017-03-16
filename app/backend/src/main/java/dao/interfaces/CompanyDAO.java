package dao.interfaces;

import model.identity.Company;

/**
 * DAO for the bean Company extending IdentityDAO
 * Created by sam on 3/7/17.
 */
public interface CompanyDAO<T extends Company> extends IdentityDAO<T>{

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Companies matching the given name exactly.
     * @param name The streetnumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byName(String name);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Companies which name contains the given name.
     * @param name The streetnumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> containsName(String name);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Companies which have the given VAT-number.
     * @param vatNumber The vatNumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byVatNumber(String vatNumber);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all Companies which have the given bankaccountnumber.
     * @param bankAccountNumber The bankAccountnumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byBankAccountNummber(String bankAccountNumber);
}
