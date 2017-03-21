package dao.database;

import dao.interfaces.*;
import model.account.Function;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Company;
import model.identity.Person;
import model.insurance.Insurance;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDateTime;

/**
 * Created by sam on 3/8/17.
 */
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
     * @param production should it run on production or development
     */
    public synchronized static void initializeProvider(String environment) {
        if (environment.equals("production")) {
            provider = new ProductionProvider("hibernate/deployment.cfg.xml");
        } else if (environment.equals("localtest")) {
            provider = new ProductionProvider("hibernate/localtest.cfg.xml");
        } else if (environment.equals("test")) {
            provider = new ProductionProvider("hibernate/test.cfg.xml");
        }

    }

    /**
     * @return the DAOProvider
     */
    public synchronized static DAOProvider getInstance() {
        if (provider == null) {
        }
        return provider;
    }

    @Override
    public synchronized AccountDAO getAccountDao() {
        return new ProductionAccountDAO(sessionFactory);
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        return new ProductionCustomerDAO(sessionFactory);
    }

    @Override
    public synchronized FleetDAO getFleetDAO() {
        return new ProductionFleetDAO(sessionFactory);
    }

    @Override
    public synchronized FunctionDAO getFunctionDAO() {
        return new ProductionFunctionDAO(sessionFactory);
    }

    @Override
    public synchronized PersonDAO getPersonDAO() {
        return new ProductionPersonDAO(sessionFactory);
    }

    @Override
    public synchronized VehicleDAO getVehicleDAO() {
        return new ProductionVehicleDAO(sessionFactory);
    }

    @Override
    public synchronized VehicleTypeDao getVehicleTypeDAO() {
        return new ProductionVehicleTypeDAO(sessionFactory);
    }

    @Override
    public AddressDAO getAddressDao() {
        return new ProductionAddressDAO(sessionFactory);
    }

    @Override
    public void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
    }

    public static void main(String[] args) throws DataAccessException {
        ProductionProvider.initializeProvider("production");
        DAOProvider provider = ProductionProvider.getInstance();

        VehicleTypeDao dao = provider.getVehicleTypeDAO();
        for(VehicleType type : dao.listFiltered(dao.nameContains("type"))){
            dao.remove(type.getUuid());
        }


        provider.close();
    }

}
