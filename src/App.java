import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/Ballog";
    private static final String USER = "postgres";
    private static final String PASSWORD = "binh2308";

    public static void main(String[] args) throws Exception {
        String selectQuery = "SELECT * FROM \"Categories\"";
        String insertQuery = "INSERT INTO \"Categories\" (\"Name\") VALUES (?)";
        String updateQuery = "UPDATE \"Categories\" SET \"Name\" = ? WHERE \"Id\" = ?";
        String deleteQuery = "DELETE FROM \"Categories\" WHERE \"Id\" = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(selectQuery)) {

            while (rs.next()) {
                int id = rs.getInt("Id");
                String name = rs.getString("Name");
                System.out.println("ID: " + id + ", Name: " + name);
            }
            // updateStmt.setString(1, "Uefa Champions League");
            // updateStmt.setInt(2, 4);
            // int rowsUpdated = updateStmt.executeUpdate();
            // System.out.println(rowsUpdated + " row(s) updated.");

            // deleteStmt.setInt(1, 1);
            // int rowsDeleted = deleteStmt.executeUpdate();
            // System.out.println(rowsDeleted + " row(s) deleted.");

            // insertStmt.setString(1, "Serie A");
            // int rowsInserted = insertStmt.executeUpdate();
            // System.out.println(rowsInserted + " row(s) inserted.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
