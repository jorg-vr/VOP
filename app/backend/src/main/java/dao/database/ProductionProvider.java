package dao.database;

import dao.interfaces.DAOProvider;
import dao.interfaces.HistoryDAO;
import dao.interfaces.InsuranceDAO;
import dao.interfaces.VehicleDAO;
import model.fleet.Subfleet;
import model.fleet.Vehicle;
import model.identity.Identity;
import model.insurance.Insurance;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.Map;

/**
 * Created by sam on 3/8/17.
 */
public class ProductionProvider implements DAOProvider {

    private static ProductionProvider provider = null;
    private StandardServiceRegistry registry;
    private SessionFactory sessionFactory;


    private ProductionProvider(){

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
    public static DAOProvider getInstance() {
        if(provider==null){
            provider = new ProductionProvider();
        }
        return provider;
    }

    @Override
    public InsuranceDAO getInsuranceDao() {
        return null;
    }

    @Override
    public VehicleDAO getVehicleDao() {
        return null;
    }

    @Override
    public HistoryDAO<Vehicle> getVehicleHistoryDAO() {
        return null;
    }

    @Override
    public HistoryDAO<Insurance> getInsuranceHistoryDAO() {
        return null;
    }

    @Override
    public HistoryDAO<Subfleet> getSubfleetHistoryDAO() {
        return null;
    }

    @Override
    public void close() {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
    }

    public static void main(String[] args) {
//        try(DAOProvider daoProvider = ProductionProvider.getInstance()){
//
//        }
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            System.out.format("%s=%s%n",
                    envName,
                    env.get(envName));
        }
        System.out.println(System.getenv("TESTTEST"));
    }
}
