// DatabaseConnector.java
package Societym;
import java.sql.*;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROPERTIES_FILE = "Societym\\resources\\db_config.properties";

    public Connection getConnection() throws SQLException {
        Properties props = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input == null) {
                throw new RuntimeException("Unable to find " + PROPERTIES_FILE);
            }
            props.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load database configuration");
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }
}
