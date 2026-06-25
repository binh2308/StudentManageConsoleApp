# Xây dựng một chương trình Java quản lý sinh viên chạy trên console 2.0

## Yêu cầu chức năng mới

Chương trình cần bổ sung các chức năng sau:

1. Save data to file: Cho phép lưu toàn bộ danh sách đối tượng vào file.
2. Load data from file: Chương trình phải có khả năng đọc dữ liệu từ file và khôi phục danh sách đối tượng.

Ví dụ menu:

1. Add new record
2. Display all records
3. Update a record
4. Delete a record
5. Search record
6. Save data to file
7. Load data from file
8. Exit

Yêu cầu:

- Khi người dùng chọn Save, toàn bộ danh sách phải được ghi vào file.
- File có thể đặt tên theo entity, ví dụ:
  - students.txt
  - products.txt
  - customers.txt

Yêu cầu:

- Khi chọn Load, chương trình đọc file và tạo lại các object trong ArrayList.
- Nếu file không tồn tại, chương trình phải xử lý lỗi.

Tự động lưu khi thoát (khuyến khích):

- Khi người dùng chọn Exit:
- Chương trình tự động lưu dữ liệu vào file.

Định dạng dữ liệu trong file: Sinh viên có thể lưu dữ liệu theo dạng text.

Ví dụ:

```text
Student

S001,Nguyen Van A,[a@gmail.com](<mailto:a@gmail.com>),0912345678,3.5

S002,Tran Thi B,[b@gmail.com](<mailto:b@gmail.com>),0987654321,3.8

Product

P01,Laptop,1200,5,Electronics

P02,Mouse,20,50,Accessories
```

Yêu cầu:

- Mỗi dòng là một object
- Các thuộc tính cách nhau bằng dấu phẩy

1. Yêu cầu kỹ thuật Java I/O

Sinh viên phải sử dụng một trong các cách sau:

- File
- FileReader / FileWriter
- BufferedReader / BufferedWriter

Hoặc có thể sử dụng:

- PrintWriter
- Scanner để đọc file

## Thiết kế phương thức xử lý file

Chương trình cần có các method như:

- saveToFile(): ghi toàn bộ danh sách vào file
- loadFromFile(): đọc file và tạo lại danh sách object
- parseData(): chuyển dữ liệu từ String → Object

## Xử lý lỗi file

Chương trình phải xử lý các lỗi như:

- File không tồn tại
- File rỗng
- Dữ liệu sai định dạng

Phải sử dụng try-catch.

## Ví dụ luồng hoạt động chương trình

```text
Program start
→ Load data from file
→ Hiển thị menu
→ Người dùng thao tác CRUD
→ Người dùng chọn Exit
→ Save data to file
```

## Điểm cộng (khuyến khích)

Sinh viên có thể làm thêm:

- Lưu dữ liệu dạng CSV

## Tiêu chí đánh giá bổ sung

- Đọc file đúng: 2 điểm
- Ghi file đúng: 2 điểm
- Thiết kế code rõ ràng: 1 điểm
