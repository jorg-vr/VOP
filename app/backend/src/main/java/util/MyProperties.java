package util;

import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
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

    public static String getProperty(String property) {
        if (myProperties == null) {
            myProperties = new MyProperties();
        }
        return myProperties.properties.getProperty(property);
    }
}
