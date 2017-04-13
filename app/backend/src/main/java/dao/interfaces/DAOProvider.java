package dao.interfaces;

import model.insurance.Contract;
import model.insurance.SpecialCondition;

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

    ContractDAO getContractDao();

    FlatSuretyDAO getFlatSuretyDao();

    InvoiceDAO getInvoiceDao();

    NonFlatSuretyDAO getNonFlatSuretyDao();

    SpecialConditionDAO getSpecialConditionDao();

    SuretyCommisionDAO getSuretyCommisionDao();

    SuretyTaxDAO getSuretyTaxDao();

    VehicleInsuranceDAO getVehicleInsuranceDao();


    /**
     * MUST BE CALLED WHEN PROVIDER IS NOT USED ANYMORE
     * if not called application will keep running
     */
    @Override
    void close();
}
