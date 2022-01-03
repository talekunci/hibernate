package ua.goit.dao;

import ua.goit.dao.config.DataSourceHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlExecutor {

    public static int execute(String sql, ParameterSetter setter) {
        try (Connection connection = DataSourceHolder.getDataSource().getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setter.set(ps);

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public static ResultSet getResultSet(String sql, ParameterSetter setter) throws SQLException {
        try (Connection connection = DataSourceHolder.getDataSource().getConnection()) {

            PreparedStatement ps = connection.prepareStatement(sql);
            setter.set(ps);
            return ps.executeQuery();
        }
    }

    @FunctionalInterface
    public interface ParameterSetter {
        void set(PreparedStatement preparedStatement) throws SQLException;
    }
}
