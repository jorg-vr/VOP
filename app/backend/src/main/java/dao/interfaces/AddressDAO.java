package dao.interfaces;

import model.identity.Address;

import java.util.UUID;

/**
 * Created by sam on 3/13/17.
 */
public interface AddressDAO extends DAO<Address> {
    Address create(String street, String streetNumber, String town, String postalCode, String country) throws DataAccessException;

    Address update(UUID id, String street, String streetNumber, String town, String postalCode, String country) throws DataAccessException;
}
