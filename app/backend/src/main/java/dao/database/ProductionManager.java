package dao.database;

import dao.interfaces.*;
import model.identity.Company;
import model.insurance.Surety;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ProductionManager implements DAOManager {

    private Session session;


    ProductionManager(Session session) {
        this.session = session;
    }

    @Override
    public UserDAO getUserDAO() {
        return new ProductionUserDAO(session);
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        return new ProductionCustomerDAO(session);
    }

    @Override
    public synchronized CompanyDAO<Company> getCompanyDAO() {
        return new ProductionCompanyDAO(session);
    }

    @Override
    public synchronized FleetDAO getFleetDAO() {
        return new ProductionFleetDAO(session);
    }

    @Override
    public synchronized FunctionDAO getFunctionDAO() {
        return new ProductionFunctionDAO(session);
    }

    @Override
    public synchronized VehicleDAO getVehicleDAO() {
        return new ProductionVehicleDAO(session);
    }

    @Override
    public synchronized VehicleTypeDAO getVehicleTypeDAO() {
        return new ProductionVehicleTypeDAO(session);
    }

    @Override
    public AddressDAO getAddressDao() {
        return new ProductionAddressDAO(session);
    }

    @Override
    public RoleDAO getRoleDAO() {
        return new ProductionRoleDAO(session);
    }

    @Override
    public ContractDAO getContractDao() {
        return new ProductionContractDAO(session);
    }

    @Override
    public SuretyDAO<Surety> getSuretyDao() {
        return new ProductionSuretyDAO(session);
    }

    @Override
    public NonFlatSuretyDAO getFlatSuretyDao() {
        return new ProductionNonFlatSuretyDAO(session);
    }

    @Override
    public InvoiceDAO getInvoiceDao() {
        return new ProductionInvoiceDAO(session.openSession());
    }

    @Override
    public FlatSuretyDAO getNonFlatSuretyDao() {
        return new ProductionFlatSuretyDAO(session.openSession());
    }

    @Override
    public SpecialConditionDAO getSpecialConditionDao() {
        return new ProductionSpecialConditionDAO(session.openSession());
    }


    @Override
    public VehicleInsuranceDAO getVehicleInsuranceDao() {
        return new ProductionVehicleInsuranceDAO(session.openSession());
    }

    @Override
    public InsuranceCompanyDAO getInsuranceCompanyDao() {
        return new ProductionInsuranceCompanyDAO(session.openSession());
    }


    @Override
    public void close() {
        session.close();
    }
}
