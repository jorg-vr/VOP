package util;

import org.springframework.context.annotation.Configuration;

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


    private static final String APPLICATION_PROPERTIES = "src/main/resources/application.properties";

    private static Properties properties;

    public static String getProperty(String property) {
            return "problem with properties";
        }
}
