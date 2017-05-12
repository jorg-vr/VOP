package dao.database.util;

import dao.database.ProductionProvider;
import dao.exceptions.DataAccessException;
import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;
import model.account.Function;
import model.account.User;
import model.identity.Address;
import model.identity.Company;
import model.identity.CompanyType;
import model.identity.Customer;

/**
 * Created by sam on 5/12/17.
 */
public class RealDataDatabaseFiller {
    public static void main(String[] args) throws DataAccessException {

        ProductionProvider.initializeProvider("localtest");
        try (DAOProvider provider = ProductionProvider.getInstance();
             ) {
            RealDataDatabaseFiller filler = new RealDataDatabaseFiller();
            filler.initUsers(provider);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUsers(DAOProvider provider) {
        try(DAOManager manager = provider.getDaoManager()){
            Address address = createAddress("Kerkstraat","1","Zomergem","9930","België");
            
            Company company = new Company();
            company.setCompanyType(CompanyType.CUSTOMER);
            company.setPhoneNumber("");
            company.setName("Solvas");
            company.setAddress(address);

            Function function = new Function();
            function

            User user = new User();
            user.setFirstName("Patrick");
            user.setLastName("Eastbirds");
            user.setEmail("patrick.eastbirds@solvas.be");
            user.setPassword("1h8xE660mn");

        }

    }

    private Address createAddress(String street, String number, String town, String postalcode, String country){
        Address address = new Address();
        address.setStreet(street);
        address.setStreetNumber(number);
        address.setTown(town);
        address.setCountry(country);
        address.setPostalCode(postalcode);
        return address;
    }

    private Company createCompany(CompanyType type, String phone, String name, Address address) {
        Company company = new Company();
        company.setCompanyType(type);
        company.setPhoneNumber(phone);
        company.setName(name);
        company.setAddress(address);
        return company;
    }
}
