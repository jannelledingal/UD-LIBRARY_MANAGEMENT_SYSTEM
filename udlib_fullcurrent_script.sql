-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 10, 2025 at 08:09 PM
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
(2, 'Data Structures and Algorithms', 'Robert Lafore', NULL, NULL, 0, 'Computer Science', 'A-102', '9780672324536', 'Available', NULL),
(3, 'Database Management Systems', 'Raghu Ramakrishnan', NULL, NULL, 0, 'Database', 'A-103', '9780072465631', 'Available', NULL),
(4, 'Networking Fundamentals', 'James Kurose', NULL, NULL, 0, 'Networking', 'A-104', '9780132856201', 'Available', NULL),
(5, 'Software Engineering', 'Ian Sommerville', NULL, NULL, 0, 'Engineering', 'A-105', '9780133943030', 'Not Available', NULL),
(8, 'Introduction to Java Programming', 'Daniel Liang', NULL, NULL, 0, 'Programming', 'A-101', '9780134670942', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(11, 'Fiction ni alfert', 'Alfert', NULL, NULL, 0, 'Fiction', 'B-5', '9780739768482', 'Not Available', 'src\\udlib\\assets\\covers\\9780739768482.jpg'),
(13, 'To Kill a Mockingbird (1960)', 'Harper Lee', NULL, NULL, 0, 'Fiction', 'B-101', '978-0-06-112008-4', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(14, '1984 (1949)', 'George Orwell', NULL, NULL, 0, 'Fiction', 'B-102', '978-0-452-28423-4', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(15, 'The Great Gatsby (1925)', 'F. Scott Fitzgerald', NULL, NULL, 0, 'Fiction', 'B-103', '978-0-7432-7356-5', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(16, 'Pride and Prejudice (1813)', 'Jane Austen', NULL, NULL, 0, 'Fiction', 'B-104', '978-0-14-143951-8', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(17, 'Moby-Dick (1851)', 'Herman Melville', NULL, NULL, 0, 'Fiction', 'B-105', '978-0-14-243724-7', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(18, 'Harry Potter and the Sorcerer’s Stone (1997)', 'J.K. Rowling', NULL, NULL, 0, 'Fiction', 'B-106', '978-0-590-35340-3', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(19, 'The Hobbit (1937)', 'J.R.R. Tolkien', NULL, NULL, 0, 'Fiction', 'B-107', '978-0-618-00221-1', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(20, 'The Alchemist (1988)', 'Paulo Coelho', NULL, NULL, 0, 'Fiction', 'B-108', '978-0-06-112241-5', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(21, 'Brave New World (1932)', 'Aldous Huxley', NULL, NULL, 0, 'Fiction', 'B-109', '978-0-06-085052-4', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(22, 'Wuthering Heights (1847)', 'Emily Brontë', NULL, NULL, 0, 'Fiction', 'B-110', '978-0-14-143955-4', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(23, 'The Diary of a Young Girl (1947)', 'Anne Frank', NULL, NULL, 0, 'History', 'D-101', '978-0-553-29698-3', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(24, 'Guns, Germs, and Steel (1997)', 'Jared Diamond', NULL, NULL, 0, 'History', 'D-102', '978-0-393-31755-8', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(25, 'The Rise and Fall of the Third Reich (1960)', 'William L. Shirer', NULL, NULL, 0, 'History', 'D-103', '978-1-4391-5844-2', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(26, 'A People’s History of the United States (1980)', 'Howard Zinn', NULL, NULL, 0, 'History', 'D-101', '978-0-06-239734-2', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(27, 'Team of Rivals (2005)', 'Doris Kearns Goodwin', NULL, NULL, 0, 'History', 'D-102', '978-0-7432-7075-5', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(28, 'Night (1956)', 'Elie Wiesel', NULL, NULL, 0, 'History', 'D-103', '978-0-374-50001-6', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(29, 'The Guns of August (1962)', 'Barbara W. Tuchman', NULL, NULL, 0, 'History', 'D-101', '978-0-345-38623-6', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(30, 'The Immortal Life of Henrietta Lacks (2010)', 'Rebecca Skloot', NULL, NULL, 0, 'History', 'D-102', '978-1-4000-5218-9', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(31, 'The Wright Brothers (2015)', 'David McCullough', NULL, NULL, 0, 'History', 'D-103', '978-1-4767-2874-2', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(32, 'On the Origin of Species (1859)', 'Charles Darwin', NULL, NULL, 0, 'Science', 'E-101', '9780199572068', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(33, 'The Structure of Scientific Revolutions (1962)', 'Thomas S. Kuhn', NULL, NULL, 0, 'Science', 'E-102', '9780226458083', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(34, 'A Brief History of Time (1988)', 'Stephen Hawking', NULL, NULL, 0, 'Science', 'E-103', '9780553380163', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(35, 'The Selfish Gene (1976)', 'Richard Dawkins', NULL, NULL, 0, 'Science', 'E-104', '9780192860927', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(36, 'Cosmos (1980)', 'Carl Sagan', NULL, NULL, 0, 'Science', 'E-105', '9780345331359', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(37, 'The Gene: An Intimate History (2016)', 'Siddhartha Mukherjee', NULL, NULL, 0, 'Science', 'E-106', '9781476733524', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(38, 'Chaos: Making a New Science (1987)', 'James Gleick', NULL, NULL, 0, 'Science', 'E-107', '9780143113451', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(39, 'The Making of the Atomic Bomb (1986)', 'Richard Rhodes', NULL, NULL, 0, 'Science', 'E-108', '9781451641998', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(40, 'The Demon in the Machine (2019)', 'Paul Davies', NULL, NULL, 0, 'Science', 'E-109', '9780300246552', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(41, 'Euclid\'s Elements (c. 300 BCE)', 'Euclid', NULL, NULL, 0, 'Math', 'C-101', '9780486600877', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(42, 'Disquisitiones Arithmeticae (1801)', 'Carl Friedrich Gauss', NULL, NULL, 0, 'Math', 'C-102', '9780486600884', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(43, 'A Mathematician\'s Apology (1940)', 'G. H. Hardy', NULL, NULL, 0, 'Math', 'C-103', '9780521427060', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(44, 'How to Solve It (1945)', 'George Pólya', NULL, NULL, 0, 'Math', 'C-101', '9780691164077', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(45, 'Concrete Mathematics (1989)', 'Donald Knuth et al.', NULL, NULL, 0, 'Math', 'C-102', '9780201558029', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(46, 'Topology (1975)', 'James R. Munkres', NULL, NULL, 0, 'Math', 'C-103', '9780134689516', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(47, 'Linear Algebra Done Right (1995)', 'Sheldon Axler', NULL, NULL, 0, 'Math', 'C-101', '9783319110790', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(48, 'Introduction to the Theory of Computation (1996)', 'Michael Sipser', NULL, NULL, 0, 'Math', 'C-102', '9781133187790', 'Available', 'src/udlib/assets/covers/no_cover.png'),
(49, 'The Art and Craft of Problem Solving (1999)', 'Paul Zeitz', NULL, NULL, 0, 'Math', 'C-103', '9780471789016', 'Available', 'src/udlib/assets/covers/no_cover.png');

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
(4, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:50:50', NULL, 'Approved'),
(5, 'Kyla', '553434', 'Fiction ni alfert', '9780739768482', '2025-10-09 16:30:47', NULL, 'Approved'),
(6, 'Faisal', '553535', 'Software Engineering', '9780133943030', '2025-10-09 16:34:45', NULL, 'Approved'),
(7, 'Christine Araullo', '560143', 'Disquisitiones Arithmeticae (1801)', '9780486600884', '2025-10-11 00:06:17', NULL, 'Pending');

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
(4, 'alfert', '560111', 'Data Structures and Algorithms', '9780672324536', '2025-10-07 15:46:25', '2025-10-10 15:46:25', 'Returned'),
(5, 'onez', '560112', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:49:24', '2025-10-10 15:49:24', 'Borrowed'),
(6, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 15:51:13', '2025-10-10 15:51:13', 'Returned'),
(7, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-08 19:58:52', '2025-10-11 19:58:52', 'Returned'),
(8, 'alfert', '560111', 'Data Structures and Algorithms', '9780672324536', '2025-10-09 15:14:08', '2025-10-12 15:14:08', 'Borrowed'),
(9, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-09 16:12:58', '2025-10-12 16:12:58', 'Returned'),
(10, 'Kyla', '553434', 'Fiction ni alfert', '9780739768482', '2025-10-09 16:31:42', '2025-10-12 16:31:42', 'Borrowed'),
(11, 'Faisal', '553535', 'Software Engineering', '9780133943030', '2025-10-09 16:35:04', '2025-10-12 16:35:04', 'Borrowed');

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
(4, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-07 16:11:56', 0.00),
(5, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-08 20:25:24', 0.00),
(6, 'axel', '560113', 'Networking Fundamentals', '9780132856201', '2025-10-11 01:28:59', 0.00),
(7, 'alfert', '560111', 'Data Structures and Algorithms', '9780672324536', '2025-10-11 01:29:21', 25.00);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT for table `book_requests`
--
ALTER TABLE `book_requests`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `borrowed_books`
--
ALTER TABLE `borrowed_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `receipts`
--
ALTER TABLE `receipts`
  MODIFY `receipt_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `returned_books`
--
ALTER TABLE `returned_books`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
