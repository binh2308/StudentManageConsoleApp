

public class Student {
    private String id;
    private String name;
    private String email;
    private String phone;
    private double gpa;

    public Student(String id, String name, String email, String phone, double gpa) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gpa = gpa;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public String toCsvLine() {
        return String.join(",",
                id,
                name,
                email,
                phone,
                String.valueOf(gpa));
    }

    @Override
    public String toString() {
        return String.format("Student[ID=%-7s | Name=%-15s | Email=%-25s | Phone=%-12s | GPA=%.2f]", id, name, email, phone, gpa);
    }
}
