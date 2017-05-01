package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.Filter;
import model.identity.Address;
import org.hibernate.Session;

/**
 * Created by sam on 3/13/17.
 */
public class ProductionAddressDAO extends ProductionDAO<Address> implements AddressDAO {

    public ProductionAddressDAO(Session session) {
        super(session,Address.class);
    }

    @Override
    public Filter<Address> byStreet(String street) {
        return filterContains("street",street);
    }

    @Override
    public Filter<Address> byStreetNumber(String streetNumber) {
        return filterContains("streetNumber",streetNumber);
    }

    @Override
    public Filter<Address> byTown(String town) {
        return filterContains("town",town);
    }

    @Override
    public Filter<Address> byPostalCode(String postalCode) {
        return filterContains("postalCode",postalCode);
    }

    @Override
    public Filter<Address> byCountry(String country) {
        return filterContains("country",country);
    }
}
