import java.sql.Connection;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        try (Connection connection = DBConnection.getConnection()) {
            System.out.println("Connected to PostgreSQL successfully.");
            System.out.println("Database: " + connection.getCatalog());
        } catch (SQLException e) {
            System.out.println("Could not connect to PostgreSQL: " + e.getMessage());
        }
    }
}
