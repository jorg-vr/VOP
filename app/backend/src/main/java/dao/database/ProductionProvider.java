package dao.database;


import dao.interfaces.DAOManager;
import dao.interfaces.DAOProvider;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Created by sam on 4/30/17.
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

    public static ProductionProvider getInstance(){
        if(provider==null){
            throw new RuntimeException("ProductionProvider is not initialized yet");
        }
        return provider;
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

    @Override
    public DAOManager getDaoManager() {
        return new ProductionManager(sessionFactory.openSession());
    }

    @Override
    public void close() throws Exception {
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(this.registry);
        provider = null;
    }
}
