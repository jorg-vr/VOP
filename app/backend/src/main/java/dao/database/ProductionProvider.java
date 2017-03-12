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


    private ProductionProvider() {

        registry = new StandardServiceRegistryBuilder()
                .configure("hibernate/hibernate.cfg.xml")
                .build();
        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();

    }

    public synchronized static DAOProvider getInstance() {
        if (provider == null) {
            provider = new ProductionProvider();
        }
        return provider;
    }

    @Override
    public synchronized AccountDAO getAccountDao() {
        return null;
    }

    @Override
    public synchronized CompanyDAO<Company> getCompanyDAO() {
        return null;
    }

    @Override
    public synchronized CustomerDAO getCustomerDAO() {
        return null;
    }

    @Override
    public synchronized FleetDAO getFleetDAO() {
        return null;
    }

    @Override
    public synchronized HistoryDAO<Vehicle> getVehicleHistoryDAO() {
        return null;
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
        return null;
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
    public void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
    }

    public static void main(String[] args) {
        try (DAOProvider daoProvider = ProductionProvider.getInstance()) {
            VehicleDAO vehicleDAO = daoProvider.getVehicleDAO();
            VehicleTypeDao vehicleTypeDao = daoProvider.getVehicleTypeDAO();
            VehicleType type = new VehicleType();
            type.setTax(2);
            type.setType("NEN OTO");

            Vehicle vehicle1 = new Vehicle();
            vehicle1.setBrand("Brand1");
            vehicle1.setLicensePlate("123a123");
            vehicle1.setModel("Mooi");
            vehicle1.setProductionDate(LocalDate.now());
            vehicle1.setValue(1000);
            vehicle1.setMileage(1);
            vehicle1.setType(type);

            vehicleTypeDao.create(type);
            vehicleDAO.create(vehicle1);
            for(Vehicle vehicle: vehicleDAO.listFiltered(vehicleDAO.byBrand("Brand1"),vehicleDAO.atLeastMileage(1),vehicleDAO.byType(type),vehicleDAO.atProductionDate(LocalDate.now()))){
                System.out.println(vehicle);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
        }


    }
}
