package dao.interfaces;

import model.identity.Address;
import model.identity.Identity;

/**
 * Created by sam on 3/7/17.
 */
public interface IdentityDAO<T extends Identity> extends DAO<T> {
    Filter<T> byAddress(Address address);
    Filter<T> byBankAccountNummber(String bankAccountNumber);
    Filter<T> byEmail(String email);
}
