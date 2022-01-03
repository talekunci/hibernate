package ua.goit.dao.config;

import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceHolder {

    private static DataSourceHolder dataSourceHolder;

    private final DataSource dataSource;

    private DataSourceHolder() {
        Properties properties = AppProperties.getProperties();
        dataSource = initPg(properties);
        if ("postgres".equals(properties.getProperty("db.type"))) {
            initPg(properties);
        }
    }

    private PGSimpleDataSource initPg(Properties props) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerNames(new String[]{props.getProperty("db.host")});
        dataSource.setPortNumbers(new int[]{Integer.parseInt(props.getProperty("db.port"))});
        dataSource.setDatabaseName(props.getProperty("db.name"));
        dataSource.setUser(props.getProperty("db.userName"));
        dataSource.setPassword(props.getProperty("db.password"));

        return dataSource;
    }

    public static DataSource getDataSource() {
        if (dataSourceHolder == null) {
            dataSourceHolder = new DataSourceHolder();
        }
        return dataSourceHolder.dataSource;
    }
}