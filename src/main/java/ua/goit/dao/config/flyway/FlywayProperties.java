package ua.goit.dao.config.flyway;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FlywayProperties {

    private static FlywayProperties flywayProperties;
    private final Properties properties;

    private FlywayProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/flyway.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return flywayProperties == null ? (flywayProperties = new FlywayProperties()).properties : flywayProperties.properties;
    }
}
