package spring.controller;

import dao.database.ProductionProvider;
import dao.interfaces.*;
import model.account.*;
import model.identity.Address;
import model.identity.Customer;
import model.identity.Periodicity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.model.RESTAuth;
import util.UUIDUtil;

import java.time.LocalDateTime;

/**
 * Created by tjupo on 30/04/2017.
 */
public class AuthUtil {


    public static String[] getAdminToken() throws Exception {
        DAOManager manager = ProductionProvider.getInstance().getDaoManager();
        String functionId = null;

        //Adds a user with all privileges to the database and sets the corresponding functionId

        UserDAO userDAO = manager.getUserDAO();
        FunctionDAO functionDAO = manager.getFunctionDAO();
        CustomerDAO customerDAO = manager.getCustomerDAO();
        RoleDAO roleDAO = manager.getRoleDAO();
        AddressDAO addressDAO = manager.getAddressDao();

        Role role = roleDAO.create(new Role("roleName-AuthUtil"));
        for (Resource resource: Resource.values()) {
            addActions(role, resource);
        }

        roleDAO.update(role);
        role = roleDAO.get(role.getUuid());

        Address address = new Address("street", "1", "town", "8530", "country");
        Customer customer = customerDAO.create(new Customer(address, "123", "customerName-AuthUtil", "456-AuthUtil", Periodicity.QUARTERLY, Periodicity.QUARTERLY));

        User user = userDAO.create(new User("firstname-AuthUtil", "lastname-AuthUtil", "admin@login.com-AuthUtil", "admin"));

        Function function = functionDAO.create(new Function(customer, role, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));
        functionId = UUIDUtil.UUIDToNumberString(function.getUuid());

        //Gets an authorization token for the created user
        RESTAuth restAuth = new RESTAuth();
        restAuth.setLogin("admin@login.com-AuthUtil");
        restAuth.setPassword("admin");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTAuthController())
                .addPlaceholderValue("path.auth", "auth")
                .addPlaceholderValue("path.login", "login")
                .addPlaceholderValue("path.refresh", "refresh")
                .build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth/login").header("Content-Type", "application/json").content(TestUtil.convertObjectToJsonBytes(restAuth))).andReturn();
        String[] authPair = new String[2];
        authPair[0] = result.getResponse().getContentAsString();
        authPair[1] = functionId;
        return authPair;
    }

    public static String[] getCustomerToken(Customer customer) throws Exception {
        DAOManager manager = ProductionProvider.getInstance().getDaoManager();
        String functionId = null;

        //Adds a user with all privileges to the database and sets the corresponding functionId

        UserDAO userDAO = manager.getUserDAO();
        FunctionDAO functionDAO = manager.getFunctionDAO();
        CustomerDAO customerDAO = manager.getCustomerDAO();
        RoleDAO roleDAO = manager.getRoleDAO();
        AddressDAO addressDAO = manager.getAddressDao();

        Role role = roleDAO.create(new Role("roleName-AuthUtil"));
        for (Resource resource: Resource.values()) {
            addActions(role, resource);
        }
        roleDAO.update(role);
        role = roleDAO.get(role.getUuid());

        Address address = new Address("street", "1", "town", "8530", "country");

        User user = userDAO.create(new User("firstname-AuthUtil", "lastname-AuthUtil", "admin@login.com-AuthUtil", "admin"));

        Function function = functionDAO.create(new Function(customer, role, user, LocalDateTime.of(2016, 7, 15, 0, 0), LocalDateTime.of(2200, 8, 3, 0, 0)));
        functionId = UUIDUtil.UUIDToNumberString(function.getUuid());

        //Gets an authorization token for the created user
        RESTAuth restAuth = new RESTAuth();
        restAuth.setLogin("admin@login.com-AuthUtil");
        restAuth.setPassword("admin");

        MockMvc mvc = MockMvcBuilders.standaloneSetup(new RESTAuthController())
                .addPlaceholderValue("path.auth", "auth")
                .addPlaceholderValue("path.login", "login")
                .addPlaceholderValue("path.refresh", "refresh")
                .build();
        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/auth/login").header("Content-Type", "application/json").content(TestUtil.convertObjectToJsonBytes(restAuth))).andReturn();
        String[] authPair = new String[2];
        authPair[0] = result.getResponse().getContentAsString();
        authPair[1] = functionId;
        return authPair;
    }

    private static void addActions(Role role, Resource resource) {
        role.setAccess(resource, Action.READ_ALL);
        role.setAccess(resource, Action.UPDATE_ALL);
        role.setAccess(resource, Action.REMOVE_ALL);
        role.setAccess(resource, Action.CREATE_ALL);
    }
}
