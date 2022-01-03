package ua.goit.dao.config.flyway;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceHolder {

    private static DataSourceHolder dataSourceHolder;

    private final DataSource dataSource;

    private DataSourceHolder() {
        Properties properties = FlywayProperties.getProperties();
        dataSource = initPg(properties);
    }

    private PGSimpleDataSource initPg(Properties properties) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUser(properties.getProperty("flyway.url"));
        dataSource.setUser(properties.getProperty("flyway.userName"));
        dataSource.setPassword(properties.getProperty("flyway.password"));

        return dataSource;
    }

    public static DataSource getDataSource() {
        if (dataSourceHolder == null) {
            dataSourceHolder = new DataSourceHolder();
        }
        return dataSourceHolder.dataSource;
    }
}