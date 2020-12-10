DROP DATABASE IF EXISTS students_db;
CREATE DATABASE students_db;
USE students_db;

CREATE TABLE Students(
    studentID               INT AUTO_INCREMENT,
    firstName               VARCHAR(50) NOT NULL,
    lastName                VARCHAR(50) NOT NULL,
    studentDepartment       VARCHAR(100) NOT NULL,
    admitted                BOOLEAN NOT NULL,
    studentMail             VARCHAR(100) NOT NULL,
    PRIMARY KEY (studentID)
);