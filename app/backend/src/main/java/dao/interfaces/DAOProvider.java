package dao.interfaces;

import model.identity.Company;
import model.insurance.Surety;

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
     * Gets a companyDAO
     * @return a CompanyDAO
     */
    CompanyDAO<Company> getCompanyDAO();

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
    VehicleTypeDAO getVehicleTypeDAO();

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
     * Gets a ContractDao
     * @return a ContractDAO
     */
    ContractDAO getContractDao();

    /**
     * Gets a SuretyDAO
     * @return a SuretyDAO
     */
    SuretyDAO<Surety> getSuretyDao();

    /**
     * Gets a FlatSuretyDAO
     * @return a FlatSuretyDAO
     */
    NonFlatSuretyDAO getFlatSuretyDao();

    /**
     * Gets an InvoiceDAO
     * @return an InvoiceDAO
     */
    InvoiceDAO getInvoiceDao();

    /**
     * Gets a NonFlatSuretyDAO
     * @return a NonFlatSuretyDAO
     */
    FlatSuretyDAO getNonFlatSuretyDao();

    /**
     * Gets a SpecialConditionDAO
     * @return a SpecialConditionDAO
     */
    SpecialConditionDAO getSpecialConditionDao();


    /**
     * Gets a VehicleInsuranceDAO
     * @return a VehicleInsuranceDAO
     */
    VehicleInsuranceDAO getVehicleInsuranceDao();

    /**
     * Gets an InsuranceCompanyDAO
     * @return an InsuranceCompanyDAO
     */
    InsuranceCompanyDAO getInsuranceCompanyDao();


    /**
     * MUST BE CALLED WHEN PROVIDER IS NOT USED ANYMORE
     * if not called application will keep running
     */
    @Override
    void close();
}
