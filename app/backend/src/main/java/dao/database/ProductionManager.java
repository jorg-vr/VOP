package dao.database;

import dao.interfaces.*;
import model.fleet.VehicleType;
import model.identity.Address;
import model.identity.Company;
import model.identity.InsuranceCompany;
import model.insurance.Surety;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ProductionManager implements DAOManager {


    private Session session;

    private UserDAO userDAO;
    private CustomerDAO customerDAO;
    private CompanyDAO<Company> companyDAO;
    private FleetDAO fleetDAO;
    private FunctionDAO functionDAO;
    private VehicleDAO vehicleDAO;
    private VehicleTypeDAO vehicleTypeDAO;
    private AddressDAO addressDAO;
    private RoleDAO roleDAO;
    private ContractDAO contractDAO;
    private SuretyDAO<Surety> suretyDAO;
    private FlatSuretyDAO flatSuretyDAO;
    private InvoiceDAO invoiceDAO;
    private NonFlatSuretyDAO nonFlatSuretyDAO;
    private SpecialConditionDAO specialConditionDAO;
    private VehicleInsuranceDAO vehicleInsuranceDAO;
    private InsuranceCompanyDAO insuranceCompanyDAO;


    ProductionManager(Session session) {
        this.session = session;
    }

    @Override
    public UserDAO getUserDAO() {
        if (userDAO == null) {
            userDAO = new ProductionUserDAO(session);
        }
        return userDAO;
    }

    @Override
    public CustomerDAO getCustomerDAO() {
        if (customerDAO == null) {
            customerDAO = new ProductionCustomerDAO(session);
        }
        return customerDAO;
    }

    @Override
    public CompanyDAO<Company> getCompanyDAO() {
        if (companyDAO == null) {
            companyDAO = new ProductionCompanyDAO(session);
        }
        return companyDAO;
    }

    @Override
    public FleetDAO getFleetDAO() {
        if (fleetDAO == null) {
            fleetDAO = new ProductionFleetDAO(session);
        }
        return fleetDAO;
    }

    @Override
    public FunctionDAO getFunctionDAO() {
        if (functionDAO == null) {
            functionDAO = new ProductionFunctionDAO(session);
        }
        return functionDAO;
    }

    @Override
    public VehicleDAO getVehicleDAO() {
        if (vehicleDAO == null) {
            vehicleDAO = new ProductionVehicleDAO(session);
        }
        return vehicleDAO;
    }

    @Override
    public VehicleTypeDAO getVehicleTypeDAO() {
        if (vehicleTypeDAO == null) {
            vehicleTypeDAO = new ProductionVehicleTypeDAO(session);
        }
        return vehicleTypeDAO;
    }

    @Override
    public AddressDAO getAddressDao() {
        if (addressDAO == null) {
            addressDAO = new ProductionAddressDAO(session);
        }
        return addressDAO;
    }

    @Override
    public RoleDAO getRoleDAO() {
        if (roleDAO == null) {
            roleDAO = new ProductionRoleDAO(session);
        }
        return roleDAO;
    }

    @Override
    public ContractDAO getContractDao() {
        if (contractDAO == null) {
            contractDAO = new ProductionContractDAO(session);
        }
        return contractDAO;
    }

    @Override
    public SuretyDAO<Surety> getSuretyDao() {
        if (suretyDAO == null) {
            suretyDAO = new ProductionSuretyDAO(session);
        }
        return suretyDAO;
    }

    @Override
    public NonFlatSuretyDAO getFlatSuretyDao() {
        if (nonFlatSuretyDAO == null) {
            nonFlatSuretyDAO = new ProductionNonFlatSuretyDAO(session);
        }
        return nonFlatSuretyDAO;
    }

    @Override
    public InvoiceDAO getInvoiceDao() {
        if (invoiceDAO == null) {
            invoiceDAO = new ProductionInvoiceDAO(session);
        }
        return invoiceDAO;
    }

    @Override
    public FlatSuretyDAO getNonFlatSuretyDao() {
        if (flatSuretyDAO == null) {
            flatSuretyDAO = new ProductionFlatSuretyDAO(session);
        }
        return flatSuretyDAO;
    }

    @Override
    public SpecialConditionDAO getSpecialConditionDao() {
        if (specialConditionDAO == null) {
            specialConditionDAO = new ProductionSpecialConditionDAO(session);
        }
        return specialConditionDAO;
    }

    @Override
    public VehicleInsuranceDAO getVehicleInsuranceDao() {
        if (vehicleInsuranceDAO == null) {
            vehicleInsuranceDAO = new ProductionVehicleInsuranceDAO(session);
        }
        return vehicleInsuranceDAO;
    }

    @Override
    public InsuranceCompanyDAO getInsuranceCompanyDao() {
        if (insuranceCompanyDAO == null) {
            insuranceCompanyDAO = new ProductionInsuranceCompanyDAO(session);
        }
        return insuranceCompanyDAO;
    }

    @Override
    public void close() {
        session.close();
    }
}
