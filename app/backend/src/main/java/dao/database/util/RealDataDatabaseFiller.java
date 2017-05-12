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

import java.util.Collection;

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
            Company company = new Company();
            company.setCompanyType(CompanyType.CUSTOMER);
            company.setPhoneNumber("093725663");
            company.setName("Solvas");
            company.setAddress(createAddress("Kerkstraat","1","Zomergem","9930","België"));


            Function function = new Function();
            function.setCompany(company);

            User user = new User();
            user.setFirstName("Patrick");
            user.setLastName("Eastbirds");
            user.setEmail("patrick.eastbirds@solvas.be");
            user.setPassword("1h8xE660mn");
            user.set

        }

    }

    private User createUser(String firstName, String lastName, String email, String password, Collection<Function> functions){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        user.setFunctions(functions);
        return user;
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
}
