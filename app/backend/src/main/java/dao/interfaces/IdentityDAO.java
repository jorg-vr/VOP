package dao.interfaces;

import model.identity.Address;
import model.identity.Identity;

/**
 * DAO for the bean Identity which all DAO interfaces for subclasses of identity should extends
 * Created by sam on 3/7/17.
 */
public interface IdentityDAO<T extends Identity> extends DAO<T> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all identities having the given address.
     *
     * @param address The address to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byAddress(Address address);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all identities having the given email (probably only one).
     *
     * @param email The email to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byEmail(String email);

    /**
     * Returns a Filter to use in ListFiltered in this class, which returns all identities having the given phoneNumber (probably only one).
     *
     * @param phoneNumber The phoneNumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<T> byPhoneNumber(String phoneNumber);
}
