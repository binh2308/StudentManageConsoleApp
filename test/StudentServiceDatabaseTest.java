import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StudentServiceDatabaseTest {
    private static final String INSERT_ID = "TST001";
    private static final String FIND_ID = "TST002";
    private static final String UPDATE_ID = "TST003";
    private static final String DELETE_ID = "TST004";
    private static final String INVALID_ID = "TST005";

    private StudentService studentService;

    @BeforeAll
    static void prepareDatabase() throws SQLException {
        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement()) {
            // statement.execute("""
            // CREATE OR REPLACE PROCEDURE insert_student(
            // IN p_id VARCHAR(10),
            // IN p_name VARCHAR(100),
            // IN p_email VARCHAR(100),
            // IN p_phone VARCHAR(20),
            // IN p_gpa DOUBLE PRECISION
            // )
            // LANGUAGE plpgsql
            // AS $$
            // BEGIN
            // INSERT INTO students(id, name, email, phone, gpa)
            // VALUES (p_id, p_name, p_email, p_phone, p_gpa);
            // END;
            // $$;
            // """);

            // statement.execute("""
            // CREATE OR REPLACE PROCEDURE get_all_students(INOUT p_cursor REFCURSOR)
            // LANGUAGE plpgsql
            // AS $$
            // BEGIN
            // OPEN p_cursor FOR
            // SELECT id, name, email, phone, gpa
            // FROM students
            // ORDER BY id;
            // END;
            // $$;
            // """);

            // statement.execute("""
            // CREATE OR REPLACE PROCEDURE update_student(
            // IN p_id VARCHAR(10),
            // IN p_name VARCHAR(100),
            // IN p_email VARCHAR(100),
            // IN p_phone VARCHAR(20),
            // IN p_gpa DOUBLE PRECISION
            // )
            // LANGUAGE plpgsql
            // AS $$
            // BEGIN
            // UPDATE students
            // SET name = p_name,
            // email = p_email,
            // phone = p_phone,
            // gpa = p_gpa
            // WHERE id = p_id;
            // END;
            // $$;
            // """);

            // statement.execute("""
            // CREATE OR REPLACE PROCEDURE delete_student(IN p_id VARCHAR(10))
            // LANGUAGE plpgsql
            // AS $$
            // BEGIN
            // DELETE FROM students WHERE id = p_id;
            // END;
            // $$;
            // """);

            // statement.execute("""
            // CREATE OR REPLACE PROCEDURE find_student_by_id(
            // IN p_id VARCHAR(10),
            // INOUT p_cursor REFCURSOR
            // )
            // LANGUAGE plpgsql
            // AS $$
            // BEGIN
            // OPEN p_cursor FOR
            // SELECT id, name, email, phone, gpa
            // FROM students
            // WHERE id = p_id;
            // END;
            // $$;
            // """);
        }
    }

    @BeforeEach
    void setUp() throws SQLException {
        studentService = new StudentService();
        cleanupTestData();
    }

    @AfterEach
    void tearDown() throws SQLException {
        cleanupTestData();
    }

    @Test
    void testInsertRecord() throws SQLException {
        Student student = new Student(INSERT_ID, "Nguyen Van A", "a@gmail.com", "0912345678", 3.5);

        boolean result = studentService.addStudent(student);
        Optional<Student> savedStudent = studentService.findStudentById(INSERT_ID);

        assertTrue(result);
        assertTrue(savedStudent.isPresent());
        assertEquals(INSERT_ID, savedStudent.get().getId());
    }

    @Test
    void testFindById() throws SQLException {
        studentService.addStudent(new Student(FIND_ID, "Tran Thi B", "b@gmail.com", "0987654321", 3.8));

        Optional<Student> result = studentService.findStudentById(FIND_ID);

        assertTrue(result.isPresent());
        assertEquals("Tran Thi B", result.get().getName());
        assertEquals("b@gmail.com", result.get().getEmail());
    }

    @Test
    void testUpdateRecord() throws SQLException {
        studentService.addStudent(new Student(UPDATE_ID, "Old Name", "old@gmail.com", "0900000000", 2.5));
        Student updatedStudent = new Student(UPDATE_ID, "New Name", "new@gmail.com", "0911111111", 3.7);

        boolean result = studentService.updateStudent(updatedStudent);
        Student savedStudent = studentService.findStudentById(UPDATE_ID).orElseThrow();

        assertTrue(result);
        assertEquals("New Name", savedStudent.getName());
        assertEquals("new@gmail.com", savedStudent.getEmail());
        assertEquals("0911111111", savedStudent.getPhone());
        assertEquals(3.7, savedStudent.getGpa());
    }

    @Test
    void testDeleteRecord() throws SQLException {
        studentService.addStudent(new Student(DELETE_ID, "Delete Me", "delete@gmail.com", "0922222222", 2.9));

        boolean result = studentService.deleteStudent(DELETE_ID);

        assertTrue(result);
        assertFalse(studentService.findStudentById(DELETE_ID).isPresent());
    }

    @Test
    void testValidation() {
        Student invalidStudent = new Student(INVALID_ID, "Invalid Email", "wrong-email", "0933333333", 3.0);

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> studentService.addStudent(invalidStudent));

        assertEquals("Invalid student data.", exception.getMessage());
    }

    private void cleanupTestData() throws SQLException {
        deleteIfExists(INSERT_ID);
        deleteIfExists(FIND_ID);
        deleteIfExists(UPDATE_ID);
        deleteIfExists(DELETE_ID);
        deleteIfExists(INVALID_ID);
    }

    private void deleteIfExists(String id) throws SQLException {
        if (studentService.findStudentById(id).isPresent()) {
            studentService.deleteStudent(id);
        }
    }
}
