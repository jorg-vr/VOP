package dao.interfaces;

import model.account.Account;
import model.identity.Company;
import model.identity.InsuranceCompany;
import model.identity.Person;
import model.insurance.Insurance;
import model.fleet.Vehicle;

public interface DAOProvider extends AutoCloseable {

    AccountDAO getAccountDao();

    CompanyDAO<Company> getCompanyDAO();

    CustomerDAO getCustomerDAO();

    FleetDAO getFleetDAO();

    FunctionDAO getFunctionDAO();

    HistoryDAO<Vehicle> getVehicleHistoryDAO();

    HistoryDAO<Insurance> getInsuranceHistoryDAO();

    IdentityDAO<Person> getIdentityDAO();

    InsuranceDAO getInsuranceDAO();

    PersonDAO getPersonDAO();

    VehicleDAO getVehicleDAO();

    VehicleTypeDao getVehicleTypeDAO();

    AddressDAO getAddressDao();

    @Override
    void close();
}
