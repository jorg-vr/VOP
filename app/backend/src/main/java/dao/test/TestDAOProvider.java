package dao.test;

import dao.interfaces.*;
import model.account.Function;
import model.fleet.Vehicle;
import model.identity.Company;
import model.identity.Person;
import model.insurance.Insurance;

/**
 * Created by Billie Devolder on 11/03/2017.
 */
public class TestDAOProvider implements DAOProvider {

    private static TestDAOProvider  provider;

    public static TestDAOProvider getInstance() {
        if (provider == null) {
            provider = new TestDAOProvider();
        }
        return provider;
    }

    private AccountDAO accountDAO;
    private CustomerDAO customerDAO;
    private PersonDAO personDAO;
    private FunctionDAO functionDAO;
    private VehicleDAO vehicleDAO;

    public TestDAOProvider() {
        this.customerDAO = new TestCustomerDAO();
        this.personDAO = new TestPersonDAO();
        this.accountDAO = new TestAccountDAO();
        this.functionDAO = new TestFunctionDAO();
        this.vehicleDAO = new TestVehicleDAO();
    }

    @Override
    public AccountDAO getAccountDao() {
        return accountDAO;
    }


    @Override
    public CustomerDAO getCustomerDAO() {
        return customerDAO;
    }

    @Override
    public FleetDAO getFleetDAO() {
        return null;
    }

    @Override
    public FunctionDAO getFunctionDAO() {
        return functionDAO;
    }


    @Override
    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        return vehicleDAO;
    }

    @Override
    public VehicleTypeDao getVehicleTypeDAO() {
        return null;
    }

    @Override
    public AddressDAO getAddressDao() {
        return null;
    }

    @Override
    public void close() {

    }
}
