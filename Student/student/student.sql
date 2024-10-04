CREATE TABLE Students (
    student_id INT AUTO_INCREMENT PRIMARY KEY, -- Auto-incremented ID for unique student identification
    student_name VARCHAR(100) NOT NULL,        -- Student's name
    father_name VARCHAR(100) NOT NULL,         -- Father's name
    mother_name VARCHAR(100) NOT NULL,         -- Mother's name
    gender ENUM('Male', 'Female') NOT NULL,    -- Gender as either 'Male' or 'Female'
    address VARCHAR(255) NOT NULL,             -- Address field
    parent_number1 VARCHAR(15) NOT NULL,       -- Parent's first phone number
    parent_number2 VARCHAR(15) NOT NULL,       -- Parent's second phone number
    student_number VARCHAR(15) NOT NULL,       -- Student's phone number
    student_email VARCHAR(100) NOT NULL UNIQUE,-- Student's email (unique)
    student_photo LONGBLOB NOT NULL,           -- Stores the uploaded student photo in binary format
    terms_accepted BOOLEAN NOT NULL            -- Checkbox confirmation for terms and conditions
);
SELECT * FROM Students;

