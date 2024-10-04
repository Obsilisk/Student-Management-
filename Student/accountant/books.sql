CREATE DATABASE books;
use books;
CREATE TABLE booktable (
book_id INT,
title VARCHAR(100),
author VARCHAR(100),
genre VARCHAR(100),
price FLOAT,
published_year YEAR,
in_stock BOOLEAN
);
INSERT INTO booktable VALUES
(20,'ABC','ESWAR','MATHS',500,2024,TRUE);
SELECT * FROM booktable;
INSERT INTO booktable VALUES
(21,'ABC','SHANMUKH','MATHS',500,2024,FALSE);
