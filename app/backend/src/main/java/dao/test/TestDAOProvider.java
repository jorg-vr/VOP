package dao.test;

import dao.interfaces.*;
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

    public TestDAOProvider() {
        this.customerDAO = new TestCustomerDAO();
        this.personDAO = new TestPersonDAO();
        this.accountDAO = new TestAccountDAO();
    }

    @Override
    public AccountDAO getAccountDao() {
        return accountDAO;
    }

    @Override
    public CompanyDAO<Company> getCompanyDAO() {
        return null;
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
    public HistoryDAO<Vehicle> getVehicleHistoryDAO() {
        return null;
    }

    @Override
    public HistoryDAO<Insurance> getInsuranceHistoryDAO() {
        return null;
    }

    @Override
    public IdentityDAO<Person> getIdentityDAO() {
        return null;
    }

    @Override
    public InsuranceDAO getInsuranceDAO() {
        return null;
    }

    @Override
    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        return null;
    }

    @Override
    public VehicleTypeDao getVehicleTypeDAO() {
        return null;
    }

    @Override
    public void close() {

    }
}
