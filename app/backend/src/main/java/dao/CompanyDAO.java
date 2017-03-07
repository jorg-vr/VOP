package dao;

import model.identity.Address;
import model.identity.Company;
import model.identity.Customer;

/**
 * Created by sam on 3/7/17.
 */
public interface CompanyDAO<T extends Company> extends IdentityDAO<T>{
    Filter<T> byName(String name);
    Filter<T> containsName(String name);
    Filter<T> byVatNumber(String vatNumber);
    Filter<T> byPhoneNumber(String email);
}
