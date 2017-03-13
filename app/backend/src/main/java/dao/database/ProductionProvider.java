package dao.database;

import dao.interfaces.*;
import model.fleet.Vehicle;
import model.fleet.VehicleType;
import model.identity.Company;
import model.identity.Identity;
import model.identity.Person;
import model.insurance.Insurance;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;

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

    public synchronized static void initializeProvider(boolean production) {
        if (production) {
            provider = new ProductionProvider("hibernate/hibernatedeployment.cfg.xml");
        } else {
            provider = new ProductionProvider("hibernate/hibernate.cfg.xml");
        }
    }

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
    public synchronized CompanyDAO<Company> getCompanyDAO() {
        return null;
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
    public synchronized HistoryDAO<Vehicle> getVehicleHistoryDAO() {return null;}

    @Override
    public synchronized FunctionDAO getFunctionDAO() {
        return new ProductionFunctionDAO(sessionFactory);
    }

    @Override
    public synchronized HistoryDAO<Insurance> getInsuranceHistoryDAO() {
        return null;
    }

    @Override
    public synchronized IdentityDAO<Person> getIdentityDAO() {
        return null;
    }

    @Override
    public synchronized InsuranceDAO getInsuranceDAO() {
        return null;
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

    public static void main(String[] args) {
        try (DAOProvider daoProvider = ProductionProvider.getInstance()) {

            AccountDAO accountDAO = daoProvider.getAccountDao();
            PersonDAO personDAO = daoProvider.getPersonDAO();
            Person sam =  personDAO.create("test","test","test");
            accountDAO.create("test","hashed",sam);
            accountDAO.create("test2","hashed",sam);
            accountDAO.create("test3","hashed",sam);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    }
}
