import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDAO {

    public void add(Student student) throws SQLException {
        try (Connection connection = DBConnection.getConnection();
                CallableStatement statement = connection.prepareCall("call insert_student(?, ?, ?, ?, ?)")) {
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getPhone());
            statement.setDouble(5, student.getGpa());
            statement.execute();
        }
    }

    public List<Student> getAll() throws SQLException {
        List<Student> students = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (CallableStatement statement = connection.prepareCall("call get_all_students(?)")) {
                statement.setObject(1, "students_cursor", Types.OTHER);
                statement.registerOutParameter(1, Types.OTHER);
                statement.execute();

                try (ResultSet resultSet = (ResultSet) statement.getObject(1)) {
                    while (resultSet.next()) {
                        students.add(mapStudent(resultSet));
                    }
                }
            } finally {
                connection.commit();
            }
        }

        return students;
    }

    public boolean update(Student student) throws SQLException {
        if (findById(student.getId()).isEmpty()) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
                CallableStatement statement = connection.prepareCall("call update_student(?, ?, ?, ?, ?)")) {
            statement.setString(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getPhone());
            statement.setDouble(5, student.getGpa());
            statement.execute();
            return true;
        }
    }

    public boolean delete(String id) throws SQLException {
        if (findById(id).isEmpty()) {
            return false;
        }

        try (Connection connection = DBConnection.getConnection();
                CallableStatement statement = connection.prepareCall("call delete_student(?)")) {
            statement.setString(1, id);
            statement.execute();
            return true;
        }
    }

    public Optional<Student> findById(String id) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false);
            try (CallableStatement statement = connection.prepareCall("call find_student_by_id(?, ?)")) {
                statement.setString(1, id);
                statement.setObject(2, "student_cursor", Types.OTHER);
                statement.registerOutParameter(2, Types.OTHER);
                statement.execute();

                try (ResultSet resultSet = (ResultSet) statement.getObject(2)) {
                    if (resultSet.next()) {
                        return Optional.of(mapStudent(resultSet));
                    }
                }
            } finally {
                connection.commit();
            }
        }

        return Optional.empty();
    }

    private Student mapStudent(ResultSet resultSet) throws SQLException {
        return new Student(
                resultSet.getString("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getString("phone"),
                resultSet.getDouble("gpa"));
    }
}
