package dao.interfaces;

import model.identity.Address;

import java.util.UUID;

/**
 * DAO for the bean Address
 * Created by sam on 3/13/17.
 */
public interface AddressDAO extends DAO<Address> {

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all address which are in the given street.
     * @param street The street to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Address> byStreet(String street);

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all address which have the given streetnumber.
     * @param streetNumber The streetnumber to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Address> byStreetNumber(String streetNumber);

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all address which have the given town.
     * @param town The town to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Address> byTown(String town);

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all address which have the given postalcode.
     * @param postalCode The postalcode to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Address> byPostalCode(String postalCode);

    /**
     * Returns a Filter to use in ListFiltered in this class, which will give all address which have the given country.
     * @param country The country to use in the filter
     * @return A useable Filter for listFiltered
     */
    Filter<Address> byCountry(String country);

}
