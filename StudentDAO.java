import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentDAO {

    // ADD STUDENT
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO students " +
                     "(id, name, course, year_level, gpa) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, Integer.parseInt(student.getId()));
            pst.setString(2, student.getFullName());
            pst.setString(3, student.getCourse());
            pst.setInt(4, student.getYearLevel());
            pst.setDouble(5, student.getGpa());

            return pst.executeUpdate() > 0;

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Add Error: " + e.getMessage());
            return false;
        }
    }

    // GET ALL STUDENTS
    public ArrayList<Student> getAllStudents() {

        ArrayList<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM students";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {

                Student student = new Student(
                    String.valueOf(rs.getInt("id")),
                    rs.getString("name"),
                    "",
                    "",
                    rs.getString("course"),
                    rs.getInt("year_level"),
                    rs.getDouble("gpa"),
                    ""
                );

                students.add(student);
            }

        } catch (SQLException e) {
            System.out.println("View Error: " + e.getMessage());
        }

        return students;
    }

    // UPDATE STUDENT
    public boolean updateStudent(Student student) {

        String sql = "UPDATE students SET " +
                     "name = ?, course = ?, year_level = ?, gpa = ? " +
                     "WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setString(1, student.getFullName());
            pst.setString(2, student.getCourse());
            pst.setInt(3, student.getYearLevel());
            pst.setDouble(4, student.getGpa());
            pst.setInt(5, Integer.parseInt(student.getId()));

            return pst.executeUpdate() > 0;

        } catch (SQLException | NumberFormatException e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    // DELETE STUDENT
    public boolean deleteStudent(int id) {

        String sql = "DELETE FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);

            return pst.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }

    // SEARCH STUDENT
    public Student searchStudent(int id) {

        String sql = "SELECT * FROM students WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {

            pst.setInt(1, id);

            try (ResultSet rs = pst.executeQuery()) {

                if (rs.next()) {

                    return new Student(
                        String.valueOf(rs.getInt("id")),
                        rs.getString("name"),
                        "",
                        "",
                        rs.getString("course"),
                        rs.getInt("year_level"),
                        rs.getDouble("gpa"),
                        ""
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Search Error: " + e.getMessage());
        }

        return null;
    }
}