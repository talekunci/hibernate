package ua.goit;

import org.flywaydb.core.Flyway;
import ua.goit.console.CommandHandler;
import ua.goit.dao.config.flyway.DataSourceHolder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Client {

    public static void main(String[] args) {
        initDataBase();
        runMainApp();
    }

    private static void initDataBase() {
        Flyway flyway = Flyway
                .configure()
                .dataSource(DataSourceHolder.getDataSource())
                .load();
        flyway.migrate();
    }

    public static void runMainApp() {
        CommandHandler commandHandler = new CommandHandler();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                commandHandler.handleCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
