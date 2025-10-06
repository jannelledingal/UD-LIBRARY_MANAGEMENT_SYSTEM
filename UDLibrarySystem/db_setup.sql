-- SQL script to create ud_library_db and tables (run in phpMyAdmin)
CREATE DATABASE IF NOT EXISTS ud_library_db;
USE ud_library_db;
CREATE TABLE admins (admin_id INT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(50), password VARCHAR(100));
INSERT INTO admins (username,password) VALUES ('jannelle','12345'),('alfert','12345'),('mark','12345'),('onez','12345'),('hannah','12345'),('axel','12345');
CREATE TABLE books (book_id INT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(150), author VARCHAR(100), category VARCHAR(100), shelf_number VARCHAR(50), isbn VARCHAR(50) UNIQUE, cover_path VARCHAR(255), availability ENUM('Available','Borrowed') DEFAULT 'Available');
CREATE TABLE requests (request_id INT AUTO_INCREMENT PRIMARY KEY, member_name VARCHAR(100), member_id VARCHAR(50), book_title VARCHAR(150), isbn VARCHAR(50), date_requested DATETIME DEFAULT CURRENT_TIMESTAMP, status ENUM('Pending','Approved','Rejected') DEFAULT 'Pending');
CREATE TABLE receipts (receipt_id INT AUTO_INCREMENT PRIMARY KEY, member_name VARCHAR(100), member_id VARCHAR(50), book_title VARCHAR(150), isbn VARCHAR(50), date_borrowed DATETIME DEFAULT CURRENT_TIMESTAMP, due_date DATETIME, status ENUM('Borrowed','Returned','Expired') DEFAULT 'Borrowed');
