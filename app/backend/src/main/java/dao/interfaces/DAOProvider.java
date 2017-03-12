package dao.interfaces;

import model.account.Account;
import model.identity.Company;
import model.identity.InsuranceCompany;
import model.identity.Person;
import model.insurance.Insurance;
import model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {

    AccountDAO getAccountDao();

    // TODO other types of companies?
    CompanyDAO<Company> getCompanyDAO();

    CustomerDAO getCustomerDAO();

    FleetDAO getFleetDAO();

    FunctionDAO getFunctionDAO();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    // TODO other types of identities?
    IdentityDAO<Person> getIdentityDAO();

    InsuranceDAO getInsuranceDAO();

    PersonDAO getPersonDAO();

    VehicleDAO getVehicleDAO();

    VehicleTypeDao getVehicleTypeDAO();

    @Override
    void close();
}
