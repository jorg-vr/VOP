package dao.interfaces;

import model.identity.Company;

/**
 * Created by sam on 3/7/17.
 */
public interface CompanyDAO<T extends Company> extends IdentityDAO<T>{
    Filter<T> byName(String name);
    Filter<T> containsName(String name);
    Filter<T> byVatNumber(String vatNumber);
    Filter<T> byBankAccountNummber(String bankAccountNumber);
}
