package dao.database;

import dao.interfaces.*;
import model.account.*;
import model.fleet.VehicleType;
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
        if (provider == null) {
            initializeProvider("test");
        }
        return provider;
    }

    @Override
    public synchronized AccountDAO getAccountDao() {
        return new ProductionAccountDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        return new ProductionCustomerDAO(sessionFactory.openSession());
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
    public synchronized PersonDAO getPersonDAO() {
        return new ProductionPersonDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized VehicleDAO getVehicleDAO() {
        return new ProductionVehicleDAO(sessionFactory.openSession());
    }

    @Override
    public synchronized VehicleTypeDao getVehicleTypeDAO() {
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
    public void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
    }

    public static void main(String[] args) throws DataAccessException {
        ProductionProvider.initializeProvider("localtest");
        DAOProvider provider = ProductionProvider.getInstance();

        try (RoleDAO roleDAO = provider.getRoleDAO();
             AccountDAO accountDAO = provider.getAccountDao();
             FunctionDAO functionDAO = provider.getFunctionDAO()) {

            Account account = new Account();
            account.setLogin("admin");
            account.setHashedPassword("123");

            Role role = new Role();
            role.setName("adminrole");
            role.setAccess(Resource.ACCOUNT, Action.CREATE_ALL);
            role.setAccess(Resource.ACCOUNT, Action.READ_ALL);
            role.setAccess(Resource.ACCOUNT, Action.REMOVE_ALL);
            role.setAccess(Resource.ACCOUNT, Action.UPDATE_ALL);

            Function function = new Function();
            function.setAccount(account);
            function.setRole(role);


            roleDAO.create(role);
            accountDAO.create(account);
            functionDAO.create(function);


        } catch (Exception e) {
            e.printStackTrace();
        }


        provider.close();
    }

}
