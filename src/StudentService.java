import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this(new StudentDAO());
    }

    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    public boolean addStudent(Student student) throws SQLException {
        validateStudent(student);
        if (isIdExist(student.getId())) {
            return false;
        }
        studentDAO.add(student);
        return true;
    }

    public List<Student> getAllStudents() throws SQLException {
        return studentDAO.getAll();
    }

    public Optional<Student> findStudentById(String id) throws SQLException {
        if (!Validation.isNotEmpty(id)) {
            return Optional.empty();
        }
        return studentDAO.findById(id.trim());
    }

    public boolean updateStudent(Student updatedStudent) throws SQLException {
        validateStudent(updatedStudent);
        return studentDAO.update(updatedStudent);
    }

    public boolean deleteStudent(String id) throws SQLException {
        if (!Validation.isNotEmpty(id)) {
            return false;
        }
        return studentDAO.delete(id.trim());
    }

    public boolean isIdExist(String id) throws SQLException {
        return findStudentById(id).isPresent();
    }

    private void validateStudent(Student student) {
        if (student == null
                || !Validation.isNotEmpty(student.getId())
                || !Validation.isNotEmpty(student.getName())
                || !Validation.isValidEmail(student.getEmail())
                || !Validation.isValidPhone(student.getPhone())
                || !Validation.isValidGpa(student.getGpa())) {
            throw new IllegalArgumentException("Invalid student data.");
        }
    }
}
