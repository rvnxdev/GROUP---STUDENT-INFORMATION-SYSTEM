import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:students.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    // Creates the tables if they don't exist yet (same as schema.sql)
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            st.execute("CREATE TABLE IF NOT EXISTS students ("
                    + "id INTEGER PRIMARY KEY, name TEXT NOT NULL, age INTEGER, "
                    + "course TEXT, year_level INTEGER, gpa REAL)");

            st.execute("CREATE TABLE IF NOT EXISTS instructors ("
                    + "id INTEGER PRIMARY KEY, name TEXT NOT NULL, age INTEGER, "
                    + "department TEXT)");

        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}