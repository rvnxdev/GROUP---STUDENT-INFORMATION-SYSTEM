import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/sis_db";

    private static final String USER = "root";

    // Put your MySQL password here
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {

        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            String studentsTable = """
                CREATE TABLE IF NOT EXISTS students (
                    id INT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    age INT,
                    course VARCHAR(100),
                    year_level INT,
                    gpa DOUBLE
                )
                """;

            String instructorsTable = """
                CREATE TABLE IF NOT EXISTS instructors (
                    id INT PRIMARY KEY,
                    name VARCHAR(100) NOT NULL,
                    age INT,
                    department VARCHAR(100)
                )
                """;

            st.execute(studentsTable);
            st.execute(instructorsTable);

            System.out.println("MySQL database initialized successfully!");

        } catch (SQLException e) {
            System.out.println("Database initialization error: "
                    + e.getMessage());
        }
    }
}