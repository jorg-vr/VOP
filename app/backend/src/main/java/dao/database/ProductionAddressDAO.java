package dao.database;

import dao.interfaces.AddressDAO;
import dao.interfaces.DataAccessException;
import dao.interfaces.Filter;
import model.account.Account;
import model.identity.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

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
