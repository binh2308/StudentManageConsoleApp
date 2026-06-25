# Xây dựng một chương trình Java quản lý sinh viên chạy trên console 3.0

## Yêu cầu chức năng

Sinh viên phải xây dựng hệ thống CRUD kết nối với database. Các thao tác CRUD phải được thực hiện thông qua Stored Procedure thay vì viết trực tiếp SQL trong code Java.

## Menu chương trình

1. Add new record
2. Display all records
3. Update a record
4. Delete a record
5. Search record by ID
6. Exit
7. Thiết kế Database Table

Sinh viên phải tạo database và table cho entity của mình.

Ví dụ bảng students

```text
id VARCHAR(10) PRIMARY KEY
name VARCHAR(100)
email VARCHAR(100)
phone VARCHAR(20)
gpa DOUBLE
```

## Stored Procedures

Sinh viên phải tạo ít nhất 5 stored procedures cho các chức năng sau:

### 1. Insert record

```sql
CREATE PROCEDURE insert_student(
	IN p_id VARCHAR(10),
	IN p_name VARCHAR(100),
	IN p_email VARCHAR(100),
	IN p_phone VARCHAR(20),
	IN p_gpa DOUBLE
)

BEGIN
	INSERT INTO students(id, name, email, phone, gpa)
	VALUES(p_id, p_name, p_email, p_phone, p_gpa);
END;
```

### 2. Get all records

```sql
CREATE PROCEDURE get_all_students()
BEGIN
	SELECT * FROM students;
END;
```

### 3. Update record

```sql
CREATE PROCEDURE update_student(
	IN p_id VARCHAR(10),
	IN p_name VARCHAR(100),
	IN p_email VARCHAR(100),
	IN p_phone VARCHAR(20),
	IN p_gpa DOUBLE
)

BEGIN
UPDATE students
SET name = p_name, email = p_email, phone = p_phone, gpa = p_gpa
WHERE id = p_id;
END;
```

### 4. Delete record

```sql
CREATE PROCEDURE delete_student(
IN p_id VARCHAR(10)
)

BEGIN
     DELETE FROM students WHERE id = p_id;
END;
```

### 5. Search by ID

```sql
CREATE PROCEDURE find_student_by_id(
IN p_id VARCHAR(10)
)

BEGIN
     SELECT * FROM students WHERE id = p_id;
END;
```

## Sử dụng Stored Procedure trong Java

Sinh viên phải sử dụng CallableStatement để gọi stored procedure.

- Ví dụ: CallableStatement stmt = connection.prepareCall("{call insert_student(?,?,?,?,?)}");

Không được viết SQL CRUD trực tiếp trong Java code.

## Thiết kế lớp trong chương trình

Chương trình nên có cấu trúc:

1. Model 
   - Entity class (Student, Product, Customer…)
2. Database 
   - DBConnection (tạo kết nối database)
3. DAO: EntityDAO 
   - add()
   - getAll()
   - update()
   - delete()
   - findById()
4. Main 
   - Menu console và xử lý nhập dữ liệu

## Validation dữ liệu

Các validation từ bài trước vẫn phải áp dụng:

- Email đúng định dạng
- Phone chỉ chứa số
- ID không được trùng
- Numeric value hợp lệ (ví dụ GPA 0–4)

## Unit Testing (bắt buộc)

Sinh viên phải viết Unit Test cho các phương thức trong DAO hoặc Service bằng JUnit.

Ít nhất phải có 5 test cases, ví dụ:

1. testInsertRecord() 
   - kiểm tra thêm dữ liệu thành công
2. testFindById() 
   - kiểm tra tìm đúng record
3. testUpdateRecord() 
   - kiểm tra update thành công
4. testDeleteRecord() 
   - kiểm tra xóa dữ liệu
5. testValidation() 
   - kiểm tra validation (email hoặc số)

Unit test phải:

- chạy được
- có assert rõ ràng

Ví dụ: assertEquals("S001", student.getId());

## Demo yêu cầu

Sinh viên phải demo:

- Tạo database
- Tạo table
- Tạo stored procedures
- Chạy chương trình Java
- Thực hiện CRUD
- Chạy Unit Test

## Tiêu chí đánh giá

- Thiết kế database và stored procedures: 2 điểm
- Kết nối JDBC: 2 điểm
- CRUD sử dụng stored procedure: 3 điểm
- Unit test: 2 điểm
- Code structure và validation: 1 điểm
