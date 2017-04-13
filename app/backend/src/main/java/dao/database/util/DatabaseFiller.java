package dao.database.util;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.account.*;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Customer;

/**
 * Created by sam on 4/13/17.
 */
public class DatabaseFiller {
    public static void main(String[] args) throws DataAccessException {

        ProductionProvider.initializeProvider("localtest");
        try (DAOProvider provider = ProductionProvider.getInstance();) {
            DatabaseFiller filler = new DatabaseFiller();
            filler.initAdminRole(provider);
            filler.initVehicleTypes(provider);

        }
    }

    private void initAdminRole(DAOProvider provider){
        try (RoleDAO roleDAO = provider.getRoleDAO();
             UserDAO userDAO = provider.getUserDAO();
             FunctionDAO functionDAO = provider.getFunctionDAO();
             AddressDAO addressDAO = provider.getAddressDao();
             CustomerDAO customerDAO = provider.getCustomerDAO();) {

            User user = new User();
            user.setEmail("admin@solvas.be");
            user.setPassword("123");
            user.setFirstName("Bill");
            user.setLastName("kill");
            user = userDAO.create(user);

            Role role = new Role();
            role.setName("adminrole");
            for (Resource resource : Resource.values()) {
                role.setAccess(resource, Action.CREATE_ALL);
                role.setAccess(resource, Action.READ_ALL);
                role.setAccess(resource, Action.REMOVE_ALL);
                role.setAccess(resource, Action.UPDATE_ALL);
            }

            Function function = new Function();
            function.setUser(user);
            function.setRole(role);
            function.setName("Adminfunction");

            Address address = new Address("mystreet", "11", "The town", "9850", "Belgium");
            Customer customer = new Customer();
            customer.setAddress(address);
            customer.setName("Solvas");
            function.setCompany(customer);


            userDAO.create(user);
            addressDAO.create(address);
            customerDAO.create(customer);
            roleDAO.create(role);
            functionDAO.create(function);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initVehicleTypes(DAOProvider provider){
        try(VehicleTypeDAO dao = provider.getVehicleTypeDAO()){
            dao.create(new VehicleType("Personenwagens",0));
            dao.create(new VehicleType("Vrachauto",0));
            dao.create(new VehicleType("Vrachtauto (+12)",0));
            dao.create(new VehicleType("Lichte vrachtwagen",0));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
