package util;

import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class MyProperties {

    public static final String PATH_AUTH = "path.auth";
    public static final String PATH_LOGIN = "path.login";
    public static final String PATH_REFRESH = "path.refresh";
    public static final String PATH_USERS = "path.users";
    public static final String PATH_ROLES = "path.roles";
    public static final String PATH_FUNCTIONS = "path.functions";
    public static final String PATH_PERMISSIONS = "path.permissions";
    public static final String PATH_FLEETS = "path.fleets";
    public static final String PATH_VEHICLES = "path.vehicles";
    public static final String PATH_TYPES = "path.types";
    public static final String PATH_COMPANIES = "path.companies";
    public static final String PATH_SURETIES = "path.sureties";
    public static final String PATH_VEHICLE_INSURANCES = "path.vehicle_insurances";
    public static final String PATH_CONTRACTS = "path.contracts";
    public static final String PATH_SPECIAL_CONDITIONS = "path.special_conditions";
    public static final String PATH_INVOICES = "path.invoices";

    private static final String APPLICATION_PROPERTIES = "application.properties";

    private static MyProperties myProperties;
    private Properties properties;

    private MyProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream(APPLICATION_PROPERTIES));
        } catch (IOException e) {
            System.err.println("Could not read properties file: " + APPLICATION_PROPERTIES);
            e.printStackTrace();
        }
    }

    /**
     * Searches for the property with the specified key in this property list.
     * The method returns {@code null} if the property is not found.
     *
     * @param property the property key.
     * @return the value in this property list with the specified key value.
     */
    public static String getProperty(String property) {
        if (myProperties == null) {
            myProperties = new MyProperties();
        }
        return myProperties.properties.getProperty(property);
    }
}
