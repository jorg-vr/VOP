package spring;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import spring.controller.RESTFleetController;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tjupo on 30/04/2017.
 */
@Configuration
@PropertySource("classpath:/application.properties")
public class MyConfiguration {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer ppc = new PropertySourcesPlaceholderConfigurer();
        /*Properties properties = new Properties();

        try {
            properties.load(SpringConfiguration.class.getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            System.err.println("Could not read properties file: " + "application.properties");
            e.printStackTrace();
        }

        ppc.setProperties(properties);*/
        //ppc.setIgnoreResourceNotFound(true);
        return ppc;
    }
}
