# Xây dựng một chương trình Java quản lý sinh viên chạy trên console

## Yêu cầu thiết kế chương trình

### Thiết kế Class (Model) Student.

Class phải có ít nhất 5 thuộc tính:

- id
- name
- email
- phone
- gpa

**Yêu cầu:**

- private fields
- constructor
- getter / setter
- toString()

**Lưu trữ dữ liệu sử dụng ArrayList để lưu danh sách đối tượng**, ví dụ ArrayList<Student> students

### Menu chương trình

Chương trình phải có menu dạng:

1. Add new record
2. Display all records
3. Update a record
4. Delete a record
5. Search record
6. Exit

Menu phải chạy **lặp cho đến khi người dùng chọn Exit**.

### Chức năng CRUD

**Add**

- Nhập thông tin từ bàn phím
- Kiểm tra dữ liệu hợp lệ trước khi thêm

**Display**

- Hiển thị toàn bộ danh sách

**Update**

- Nhập id để tìm đối tượng
- Cho phép cập nhật thông tin

**Delete**

- Nhập id để xóa đối tượng

**Search**

- Tìm theo id hoặc name

### Validation dữ liệu

Chương trình phải có kiểm tra dữ liệu:

- **Email validation**
  - Đúng định dạng email
- **Phone validation**
  - Chỉ chứa số
  - Độ dài hợp lệ
- **Number validation**
  - Ví dụ: GPA từ 0–4
  - Price > 0
  - Quantity >= 0
- **String validation**
  - Không được để trống
- **ID validation**
  - Không được trùng

Có thể sử dụng:

- Regex
- try-catch
- phương thức kiểm tra riêng

### Tổ chức code

Khuyến khích chia thành nhiều class, ví dụ:

Model class: Student, ...

Service class: StudentService …

- add()
- update()
- delete()
- search()

Main class chứa menu chương trình

### Xử lý lỗi

Chương trình phải xử lý lỗi nhập liệu bằng try-catch, ví dụ:

- nhập chữ thay vì số
- nhập dữ liệu sai định dạng

**Yêu cầu bổ sung (khuyến khích):**

- Sort theo name hoặc id
- Lưu dữ liệu ra file
- Đọc dữ liệu từ file
- Unit test cho một số chức năng

## Tiêu chí đánh giá

**Tiêu chí đánh giá (ví dụ 10 điểm)**

- **Thiết kế class và OOP: 2 điểm**
- **Chức năng CRUD đầy đủ: 3 điểm**
- **Validation dữ liệu: 2 điểm**
- **Menu và xử lý nhập liệu: 2 điểm**
- **Code structure và readability: 1 điểm**
