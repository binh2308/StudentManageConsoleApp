
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentService {
    private final List<Student> students = new ArrayList<>();

    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to protect the original list
    }

    public boolean addStudent(Student student) {
        if (isIdExist(student.getId())) {
            return false; // ID already exists
        }
        students.add(student);
        return true;
    }

    public Optional<Student> findStudentById(String id) {
        return students.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Student> findStudentsByName(String name) {
        return students.stream()
                .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    public boolean updateStudent(String id, Student updatedStudent) {
        Optional<Student> studentOpt = findStudentById(id);
        if (studentOpt.isPresent()) {
            Student existingStudent = studentOpt.get();
            existingStudent.setName(updatedStudent.getName());
            existingStudent.setEmail(updatedStudent.getEmail());
            existingStudent.setPhone(updatedStudent.getPhone());
            existingStudent.setGpa(updatedStudent.getGpa());
            return true;
        }
        return false;
    }

    public boolean deleteStudent(String id) {
        return students.removeIf(s -> s.getId().equalsIgnoreCase(id));
    }

    public boolean isIdExist(String id) {
        return students.stream().anyMatch(s -> s.getId().equalsIgnoreCase(id));
    }

    public void saveToFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Student student : students) {
                writer.write(student.toCsvLine());
                writer.newLine();
            }
        }
    }

    public int loadFromFile(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            throw new IOException("File does not exist: " + fileName);
        }

        List<Student> loadedStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) {
                    continue;
                }
                Student student = parseData(line, lineNumber);
                if (isDuplicateId(loadedStudents, student.getId())) {
                    throw new IOException("Duplicate student ID at line " + lineNumber + ": " + student.getId());
                }
                loadedStudents.add(student);
            }
        }

        students.clear();
        students.addAll(loadedStudents);
        return loadedStudents.size();
    }

    private Student parseData(String line, int lineNumber) throws IOException {
        String[] parts = line.split(",", -1);
        if (parts.length != 5) {
            throw new IOException("Invalid data format at line " + lineNumber + ": expected 5 fields.");
        }

        String id = parts[0].trim();
        String name = parts[1].trim();
        String email = parts[2].trim();
        String phone = parts[3].trim();
        double gpa;

        try {
            gpa = Double.parseDouble(parts[4].trim());
        } catch (NumberFormatException e) {
            throw new IOException("Invalid GPA at line " + lineNumber + ".", e);
        }

        if (!Validation.isNotEmpty(id)
                || !Validation.isNotEmpty(name)
                || !Validation.isNotEmpty(email)
                || !Validation.isNotEmpty(phone)
                || !Validation.isValidEmail(email)
                || !Validation.isValidPhone(phone)
                || !Validation.isValidGpa(gpa)) {
            throw new IOException("Invalid student data at line " + lineNumber + ".");
        }

        return new Student(id, name, email, phone, gpa);
    }

    private boolean isDuplicateId(List<Student> loadedStudents, String id) {
        return loadedStudents.stream().anyMatch(s -> s.getId().equalsIgnoreCase(id));
    }
}
