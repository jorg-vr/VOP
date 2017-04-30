package dao.database;

import dao.interfaces.*;
import model.account.Function;
import model.account.User;
import model.billing.Invoice;
import model.identity.Company;
import model.insurance.Surety;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class ProductionProvider implements DAOProvider {

    private static ProductionProvider provider = null;
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;


    private ProductionProvider(String configLocation) {

        registry = new StandardServiceRegistryBuilder()
                .configure(configLocation)
                .build();
        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

    /**
     * SHOULD BE CALLED BEFORE getInstance()
     * initializes the provider with the right hibernate configuration
     *
     * @param environment should it run on production or development
     */
    public synchronized static void initializeProvider(String environment) {
        if (environment.equals("production")) {
            provider = new ProductionProvider("hibernate/deployment.cfg.xml");
        } else if (environment.equals("localtest")) {
            provider = new ProductionProvider("hibernate/localtest.cfg.xml");
        } else if (environment.equals("test")) {
            provider = new ProductionProvider("hibernate/test.cfg.xml");
        } else if (environment.equals("unittest")) {
            provider = new ProductionProvider("hibernate/test-in-memory.cfg.xml");
        }

    }

    /**
     * @return the DAOProvider
     */
    public synchronized static DAOProvider getInstance() {
        return provider;
    }

    @Override
    public UserDAO getUserDAO() {
        return new ProductionUserDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        return new ProductionCustomerDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized CompanyDAO<Company> getCompanyDAO() {
        return new ProductionCompanyDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized FleetDAO getFleetDAO() {
        return new ProductionFleetDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized FunctionDAO getFunctionDAO() {
        return new ProductionFunctionDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized VehicleDAO getVehicleDAO() {
        return new ProductionVehicleDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized VehicleTypeDAO getVehicleTypeDAO() {
        return new ProductionVehicleTypeDAO(sessionFactory.openSession());
    }

    @Override
    public AddressDAO getAddressDao() {
        return new ProductionAddressDAO(sessionFactory.openSession());
    }

    @Override
    public RoleDAO getRoleDAO() {
        return new ProductionRoleDAO(sessionFactory.openSession());
    }

    @Override
    public ContractDAO getContractDao() {
        return new ProductionContractDAO(sessionFactory.openSession());
    }

    @Override
    public SuretyDAO<Surety> getSuretyDao() {
        return new ProductionSuretyDAO(sessionFactory.openSession());
    }

    @Override
    public NonFlatSuretyDAO getFlatSuretyDao() {
        return new ProductionNonFlatSuretyDAO(sessionFactory.openSession());
    }

    @Override
    public InvoiceDAO getInvoiceDao() {
        return new ProductionInvoiceDAO(sessionFactory.openSession());
    }

    @Override
    public FlatSuretyDAO getNonFlatSuretyDao() {
        return new ProductionFlatSuretyDAO(sessionFactory.openSession());
    }

    @Override
    public SpecialConditionDAO getSpecialConditionDao() {
        return new ProductionSpecialConditionDAO(sessionFactory.openSession());
    }


    @Override
    public VehicleInsuranceDAO getVehicleInsuranceDao() {
        return new ProductionVehicleInsuranceDAO(sessionFactory.openSession());
    }

    @Override
    public InsuranceCompanyDAO getInsuranceCompanyDao() {
        return new ProductionInsuranceCompanyDAO(sessionFactory.openSession());
    }


    @Override
    public void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
        provider = null;
    }
}
