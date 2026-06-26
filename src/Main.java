
import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {
        int choice;
        do {
            printMenu();
            choice = getIntegerInput("Enter your choice: ");
            switch (choice) {
                case 1:
                    addNewRecord();
                    break;
                case 2:
                    displayAllRecords();
                    break;
                case 3:
                    updateRecord();
                    break;
                case 4:
                    deleteRecord();
                    break;
                case 5:
                    searchRecord();
                    break;
                case 6:
                    System.out.println("Exiting program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println(); // for better spacing
        } while (choice != 6);
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("======= STUDENT MANAGEMENT =======");
        System.out.println("1. Add new record");
        System.out.println("2. Display all records");
        System.out.println("3. Update a record");
        System.out.println("4. Delete a record");
        System.out.println("5. Search record by ID");
        System.out.println("6. Exit");
        System.out.println("==================================");
    }

    private static void addNewRecord() {
        System.out.println("--- Add New Student ---");
        String id;
        while (true) {
            id = getStringInput("Enter ID: ", false);
            try {
                if (!studentService.isIdExist(id)) {
                    break;
                }
                System.out.println("This ID already exists. Please enter a unique ID.");
            } catch (Exception e) {
                System.out.println("Could not check student ID: " + e.getMessage());
                return;
            }
        }

        String name = getStringInput("Enter Name: ", false);
        String email = getValidatedStringInput("Enter Email: ", Validation::isValidEmail, "Invalid email format.");
        String phone = getValidatedStringInput("Enter Phone (10 digits): ", Validation::isValidPhone, "Invalid phone format. Must be 10 digits.");
        double gpa = getGpaInput("Enter GPA (0.0 - 4.0): ", false);

        try {
            Student newStudent = new Student(id, name, email, phone, gpa);
            if (studentService.addStudent(newStudent)) {
                System.out.println("Student added successfully!");
            } else {
                System.out.println("This ID already exists. Please enter a unique ID.");
            }
        } catch (Exception e) {
            System.out.println("Could not add student: " + e.getMessage());
        }
    }

    private static void displayAllRecords() {
        System.out.println("--- All Student Records ---");
        try {
            List<Student> students = studentService.getAllStudents();
            if (students.isEmpty()) {
                System.out.println("No records found.");
            } else {
                students.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Could not display students: " + e.getMessage());
        }
    }

    private static void updateRecord() {
        System.out.println("--- Update Student Record ---");
        String id = getStringInput("Enter student ID to update: ", false);
        Optional<Student> studentOpt;

        try {
            studentOpt = studentService.findStudentById(id);
        } catch (Exception e) {
            System.out.println("Could not find student: " + e.getMessage());
            return;
        }

        if (studentOpt.isEmpty()) {
            System.out.println("Student with ID '" + id + "' not found.");
            return;
        }

        System.out.println("Enter new information (leave blank to keep current value):");
        Student currentStudent = studentOpt.get();

        String name = getStringInput("Enter Name [" + currentStudent.getName() + "]: ", true);
        String email = getValidatedStringInput("Enter Email [" + currentStudent.getEmail() + "]: ", Validation::isValidEmail, "Invalid email format.", true);
        String phone = getValidatedStringInput("Enter Phone [" + currentStudent.getPhone() + "]: ", Validation::isValidPhone, "Invalid phone format.", true);
        double gpa = getGpaInput("Enter GPA [" + currentStudent.getGpa() + "]: ", true);

        Student updatedStudent = new Student(
            id,
            Validation.isNotEmpty(name) ? name : currentStudent.getName(),
            Validation.isNotEmpty(email) ? email : currentStudent.getEmail(),
            Validation.isNotEmpty(phone) ? phone : currentStudent.getPhone(),
            gpa != -1 ? gpa : currentStudent.getGpa()
        );

        try {
            if (studentService.updateStudent(updatedStudent)) {
                System.out.println("Student record updated successfully!");
            } else {
                System.out.println("Student with ID '" + id + "' not found.");
            }
        } catch (Exception e) {
            System.out.println("Could not update student: " + e.getMessage());
        }
    }

    private static void deleteRecord() {
        System.out.println("--- Delete Student Record ---");
        String id = getStringInput("Enter student ID to delete: ", false);
        try {
            if (studentService.deleteStudent(id)) {
                System.out.println("Student with ID '" + id + "' deleted successfully.");
            } else {
                System.out.println("Student with ID '" + id + "' not found.");
            }
        } catch (Exception e) {
            System.out.println("Could not delete student: " + e.getMessage());
        }
    }

    private static void searchRecord() {
        System.out.println("--- Search Student By ID ---");
        String id = getStringInput("Enter ID to search: ", false);
        try {
            studentService.findStudentById(id)
                    .ifPresentOrElse(
                            System.out::println,
                            () -> System.out.println("No student found with ID: " + id)
                    );
        } catch (Exception e) {
            System.out.println("Could not search student: " + e.getMessage());
        }
    }

    // --- Input Helper Methods ---

    private static int getIntegerInput(String prompt) {
        int value = -1;
        while (true) {
            try {
                System.out.print(prompt);
                value = scanner.nextInt();
                scanner.nextLine(); // Consume newline left-over
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
    }

    private static double getGpaInput(String prompt, boolean allowEmpty) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (allowEmpty && input.trim().isEmpty()) {
                    return -1; // Sentinel value for no change
                }
                double gpa = Double.parseDouble(input);
                if (Validation.isValidGpa(gpa)) {
                    return gpa;
                } else {
                    System.out.println("Invalid GPA. Please enter a value between 0.0 and 4.0.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number for GPA.");
            }
        }
    }

    private static String getStringInput(String prompt, boolean allowEmpty) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (allowEmpty || Validation.isNotEmpty(input)) {
                return input;
            }
            System.out.println("Input cannot be empty.");
        }
    }

    @FunctionalInterface
    interface StringValidator { boolean validate(String s); }

    private static String getValidatedStringInput(String prompt, StringValidator validator, String errorMessage, boolean allowEmpty) {
        while (true) {
            String input = getStringInput(prompt, allowEmpty);
            if (allowEmpty && input.trim().isEmpty()) { return input; }
            if (validator.validate(input)) { return input; }
            System.out.println(errorMessage);
        }
    }

    private static String getValidatedStringInput(String prompt, StringValidator validator, String errorMessage) {
        return getValidatedStringInput(prompt, validator, errorMessage, false);
    }
}
