-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 07, 2025 at 02:44 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ud_library_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `books`
--

CREATE TABLE `books` (
  `id` int(11) NOT NULL,
  `title` varchar(200) DEFAULT NULL,
  `author` varchar(150) DEFAULT NULL,
  `publisher` varchar(100) DEFAULT NULL,
  `year` varchar(10) DEFAULT NULL,
  `quantity` int(11) DEFAULT 0,
  `category` varchar(100) DEFAULT NULL,
  `shelf_number` varchar(50) DEFAULT NULL,
  `isbn` varchar(50) DEFAULT NULL,
  `availability` varchar(20) DEFAULT 'Available',
  `cover_path` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `books`
--

INSERT INTO `books` (`id`, `title`, `author`, `publisher`, `year`, `quantity`, `category`, `shelf_number`, `isbn`, `availability`, `cover_path`) VALUES
(2, 'Data Structures and Algorithms', 'Robert Lafore', NULL, NULL, 0, 'Computer Science', 'A-102', '9780672324536', 'Not Available', NULL),
(3, 'Database Management Systems', 'Raghu Ramakrishnan', NULL, NULL, 0, 'Database', 'A-103', '9780072465631', 'Available', NULL),
(4, 'Networking Fundamentals', 'James Kurose', NULL, NULL, 0, 'Networking', 'A-104', '9780132856201', 'Available', NULL),
(5, 'Software Engineering', 'Ian Sommerville', NULL, NULL, 0, 'Engineering', 'A-105', '9780133943030', 'Available', NULL),
(8, 'Introduction to Java Programming', 'Daniel Liang', NULL, NULL, 0, 'Programming', 'A-101', '9780134670942', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(9, 'ambot', 'alfert', NULL, NULL, 0, 'Academic', 'A-000', '9784682648750', 'Available', 'src\\udlib\\assets\\covers\\9784682648750.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `book_requests`
--

CREATE TABLE `book_requests` (
  `id` int(11) NOT NULL,
  `ud_name` varchar(100) DEFAULT NULL,
  `ud_id` varchar(50) DEFAULT NULL,
  `book_title` varchar(200) DEFAULT NULL,
  `book_isbn` varchar(50) DEFAULT NULL,
  `request_date` datetime DEFAULT current_timestamp(),
  `due_date` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Pending'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `book_requests`
--

INSERT INTO `book_requests` (`id`, `ud_name`, `ud_id`, `book_title`, `book_isbn`, `request_date`, `due_date`, `status`) VALUES
(1, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 12:45:53', NULL, 'Approved'),
(2, 'alfert', '560111', 'Data Structures and Algorithms', '9780672324536', '2025-10-07 15:46:05', NULL, 'Approved'),
(3, 'onez', '560112', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:49:04', NULL, 'Approved'),
(4, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:50:50', NULL, 'Rejected');

-- --------------------------------------------------------

--
-- Table structure for table `borrowed_books`
--

CREATE TABLE `borrowed_books` (
  `id` int(11) NOT NULL,
  `ud_name` varchar(100) DEFAULT NULL,
  `ud_id` varchar(50) DEFAULT NULL,
  `book_title` varchar(200) DEFAULT NULL,
  `book_isbn` varchar(50) DEFAULT NULL,
  `borrow_date` datetime DEFAULT current_timestamp(),
  `due_date` datetime DEFAULT NULL,
  `status` varchar(20) DEFAULT 'Borrowed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `borrowed_books`
--

INSERT INTO `borrowed_books` (`id`, `ud_name`, `ud_id`, `book_title`, `book_isbn`, `borrow_date`, `due_date`, `status`) VALUES
(1, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 12:46:29', '2025-10-10 12:46:29', 'Returned'),
(2, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 13:11:40', '2025-10-10 13:11:40', 'Returned'),
(3, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 15:42:28', '2025-10-10 15:42:28', 'Returned'),
(4, 'alfert', '560111', 'Data Structures and Algorithms', '9780672324536', '2025-10-07 15:46:25', '2025-10-10 15:46:25', 'Borrowed'),
(5, 'onez', '560112', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:49:24', '2025-10-10 15:49:24', 'Borrowed'),
(6, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:51:13', '2025-10-10 15:51:13', 'Returned');

-- --------------------------------------------------------

--
-- Table structure for table `receipts`
--

CREATE TABLE `receipts` (
  `receipt_id` int(11) NOT NULL,
  `member_name` varchar(100) NOT NULL,
  `member_id` varchar(50) NOT NULL,
  `book_title` varchar(200) NOT NULL,
  `isbn` varchar(50) NOT NULL,
  `date_borrowed` datetime DEFAULT current_timestamp(),
  `due_date` datetime DEFAULT NULL,
  `status` enum('Borrowed','Returned','Expired') DEFAULT 'Borrowed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `returned_books`
--

CREATE TABLE `returned_books` (
  `id` int(11) NOT NULL,
  `ud_name` varchar(100) DEFAULT NULL,
  `ud_id` varchar(50) DEFAULT NULL,
  `book_title` varchar(200) DEFAULT NULL,
  `book_isbn` varchar(50) DEFAULT NULL,
  `return_date` datetime DEFAULT current_timestamp(),
  `penalty` decimal(10,2) DEFAULT 0.00
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Dumping data for table `returned_books`
--

INSERT INTO `returned_books` (`id`, `ud_name`, `ud_id`, `book_title`, `book_isbn`, `return_date`, `penalty`) VALUES
(1, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 13:14:56', 0.00),
(2, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 16:11:44', 0.00),
(3, 'Jannelle', '560136', 'Database Management Systems', '9780072465631', '2025-10-07 16:11:48', 0.00),
(4, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 16:11:56', 0.00);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `books`
--
ALTER TABLE `books`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `isbn` (`isbn`);

--
-- Indexes for table `book_requests`
--
ALTER TABLE `book_requests`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `borrowed_books`
--
ALTER TABLE `borrowed_books`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `receipts`
--
ALTER TABLE `receipts`
  ADD PRIMARY KEY (`receipt_id`);

--
-- Indexes for table `returned_books`
--
ALTER TABLE `returned_books`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `books`
--
ALTER TABLE `books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `book_requests`
--
ALTER TABLE `book_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `borrowed_books`
--
ALTER TABLE `borrowed_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `receipts`
--
ALTER TABLE `receipts`
  MODIFY `receipt_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `returned_books`
--
ALTER TABLE `returned_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
