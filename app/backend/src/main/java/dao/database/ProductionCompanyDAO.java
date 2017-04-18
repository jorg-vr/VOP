package dao.database;

import dao.interfaces.CompanyDAO;
import dao.interfaces.Filter;
import model.identity.Address;
import model.identity.Company;
import model.identity.Customer;
import org.hibernate.Session;

/**
 * Created by sam on 4/18/17.
 */
public class ProductionCompanyDAO extends ProductionDAO<Company> implements CompanyDAO<Company> {
    public ProductionCompanyDAO(Session session) {
        super(session, Company.class);
    }
    @Override
    public Filter<Company> byName(String name) {
        return filterEqual("name", name);
    }

    @Override
    public Filter<Company> containsName(String name) {
        return filterContains("name", name);
    }

    @Override
    public Filter<Company> byVatNumber(String vatNumber) {
        return filterContains("btwNumber",vatNumber);
    }

    @Override
    public Filter<Company> byPhoneNumber(String phoneNumber) {
        return filterContains("phoneNumber",phoneNumber);
    }

    @Override
    public Filter<Company> byAddress(Address address) {
        return filterEqual("address",address);
    }

    @Override
    public Filter<Company> byBankAccountNummber(String bankAccountNumber) {
        return filterContains("bankAccountNumber",bankAccountNumber);
    }

    @Override
    public Filter<Company> byEmail(String email) {
        return filterContains("email",email);
    }

}
