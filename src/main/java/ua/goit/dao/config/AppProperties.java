package ua.goit.dao.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {

    private static AppProperties appProperties;
    private final Properties properties;

    private AppProperties() {
        properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/resources/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Properties getProperties() {
        return appProperties == null ? (appProperties = new AppProperties()).properties : appProperties.properties;
    }
}
