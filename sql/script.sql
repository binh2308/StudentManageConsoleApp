CREATE TABLE IF NOT EXISTS students (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    gpa DOUBLE PRECISION NOT NULL CHECK (gpa >= 0 AND gpa <= 4)
);

CREATE OR REPLACE PROCEDURE insert_student(
    IN p_id VARCHAR(10),
    IN p_name VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_phone VARCHAR(20),
    IN p_gpa DOUBLE PRECISION
)
LANGUAGE plpgsql
AS $$
BEGIN
    INSERT INTO students(id, name, email, phone, gpa)
    VALUES (p_id, p_name, p_email, p_phone, p_gpa);
END;
$$;

CREATE OR REPLACE PROCEDURE get_all_students(INOUT p_cursor REFCURSOR)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN p_cursor FOR
        SELECT id, name, email, phone, gpa
        FROM students
        ORDER BY id;
END;
$$;

CREATE OR REPLACE PROCEDURE update_student(
    IN p_id VARCHAR(10),
    IN p_name VARCHAR(100),
    IN p_email VARCHAR(100),
    IN p_phone VARCHAR(20),
    IN p_gpa DOUBLE PRECISION
)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE students
    SET name = p_name,
        email = p_email,
        phone = p_phone,
        gpa = p_gpa
    WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE delete_student(IN p_id VARCHAR(10))
LANGUAGE plpgsql
AS $$
BEGIN
    DELETE FROM students WHERE id = p_id;
END;
$$;

CREATE OR REPLACE PROCEDURE find_student_by_id(
    IN p_id VARCHAR(10),
    INOUT p_cursor REFCURSOR
)
LANGUAGE plpgsql
AS $$
BEGIN
    OPEN p_cursor FOR
        SELECT id, name, email, phone, gpa
        FROM students
        WHERE id = p_id;
END;
$$;