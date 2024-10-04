CREATE DATABASE certificates_db;

USE certificates_db;

CREATE TABLE certificates (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    course VARCHAR(100),
    grade VARCHAR(10)
);
SELECT * FROM certificates;