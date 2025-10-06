-- SQL script to create ud_library_db and tables (run in phpMyAdmin)
CREATE DATABASE IF NOT EXISTS ud_library_db;
USE ud_library_db;
CREATE TABLE admins (admin_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50), password VARCHAR(100));
INSERT INTO admins (username,password) VALUES ('jannelle','12345'),('alfert','12345'),('mark','12345'),('onez','12345'),('hannah','12345'),('axel','12345');
CREATE TABLE books (book_id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(150), author VARCHAR(100), category VARCHAR(100), shelf_number VARCHAR(50), isbn VARCHAR(50) UNIQUE, cover_path VARCHAR(255), availability ENUM('Available','Borrowed') DEFAULT 'Available');
CREATE TABLE requests (request_id INT AUTO_INCREMENT PRIMARY KEY, member_name VARCHAR(100), member_id VARCHAR(50), book_title VARCHAR(150), isbn VARCHAR(50), date_requested DATETIME DEFAULT CURRENT_TIMESTAMP, status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending');
CREATE TABLE receipts (receipt_id INT AUTO_INCREMENT PRIMARY KEY, member_name VARCHAR(100), member_id VARCHAR(50), book_title VARCHAR(150), isbn VARCHAR(50), date_borrowed DATETIME DEFAULT CURRENT_TIMESTAMP, due_date DATETIME, status ENUM('Borrowed','Returned','Expired') DEFAULT 'Borrowed');


-- Table for storing book information
CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200),
    author VARCHAR(150),
    category VARCHAR(100),
    shelf_number VARCHAR(50),
    isbn VARCHAR(50) UNIQUE,
    availability VARCHAR(20) DEFAULT 'Available',
    cover_path VARCHAR(255)
);

-- Table for book requests by users
CREATE TABLE IF NOT EXISTS book_requests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ud_name VARCHAR(100),
    ud_id VARCHAR(50),
    book_title VARCHAR(200),
    book_isbn VARCHAR(50),
    request_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(20) DEFAULT 'Pending'
);

-- Table for borrowed books (approved requests)
CREATE TABLE IF NOT EXISTS borrowed_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ud_name VARCHAR(100),
    ud_id VARCHAR(50),
    book_title VARCHAR(200),
    book_isbn VARCHAR(50),
    borrow_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    due_date DATETIME,
    status VARCHAR(20) DEFAULT 'Borrowed'
);

-- Table for returned books
CREATE TABLE IF NOT EXISTS returned_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ud_name VARCHAR(100),
    ud_id VARCHAR(50),
    book_title VARCHAR(200),
    book_isbn VARCHAR(50),
    return_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    penalty DECIMAL(10,2) DEFAULT 0
);

-- Add a few sample books for testing
INSERT INTO books (title, author, category, shelf_number, isbn, availability)
VALUES 
('Introduction to Java Programming', 'Daniel Liang', 'Programming', 'A-101', '9780134670942', 'Available'),
('Data Structures and Algorithms', 'Robert Lafore', 'Computer Science', 'A-102', '9780672324536', 'Available'),
('Database Management Systems', 'Raghu Ramakrishnan', 'Database', 'A-103', '9780072465631', 'Available'),
('Networking Fundamentals', 'James Kurose', 'Networking', 'A-104', '9780132856201', 'Available'),
('Software Engineering', 'Ian Sommerville', 'Engineering', 'A-105', '9780133943030', 'Available');
