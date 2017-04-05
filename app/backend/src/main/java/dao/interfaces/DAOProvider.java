package dao.interfaces;

import model.account.Account;
import model.identity.Company;
import model.identity.InsuranceCompany;
import model.identity.Person;
import model.insurance.Insurance;
import model.fleet.Vehicle;

/**
 * DAO provider to get DAO's
 * Created by sam on 3/7/17.
 */
public interface DAOProvider extends AutoCloseable {

    /**
     * Gets a UserDAO
     * @return a UserDAO
     */
    UserDAO getUserDAO();

    /**
     * Gets an CustomerDAO
     * @return a CustomerDAO
     */
    CustomerDAO getCustomerDAO();

    /**
     * Gets a FleetDAO
     * @return a FleetDAO
     */
    FleetDAO getFleetDAO();

    /**
     * Gets a FunctionDAO
     * @return a FunctionDAO
     */
    FunctionDAO getFunctionDAO();

    /**
     * Gets a VehicleDAO
     * @return a VehicleDAO
     */
    VehicleDAO getVehicleDAO();

    /**
     * Gets a VehicleTypeDAO
     * @return a VehicleTypeDAO
     */
    VehicleTypeDao getVehicleTypeDAO();

    /**
     * Gets an AdressDAO
     * @return an AddressDAO
     */
    AddressDAO getAddressDao();

    /**
     * Gets an RoleDAO
     * @return a RoleDAO
     */
    RoleDAO getRoleDAO();

    /**
     * MUST BE CALLED WHEN PROVIDER IS NOT USED ANYMORE
     * if not called application will keep running
     */
    @Override
    void close();
}
