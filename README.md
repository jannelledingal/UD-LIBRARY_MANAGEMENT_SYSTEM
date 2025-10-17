# 📚 University of Davao Library Management System (UD-LMS)

### 🏫 A Project for the University of Mindanao – College of Computing Education  
**Course:** CCE105 (1st Semester, S.Y. 2025–2026)  
**Proponents:** Hannah Kaye F. Basan, Jannelle Jean R. Dingal, Axel P. Dos Pueblos, Milalyn M. Oñez, Alfert V. Ramos Jr., Rodmark B. Tapia  
**Instructor:** Altheae Owe  

---

## 📖 System Overview

The **University of Davao Library Management System (UD-LMS)** is a **desktop-based Java application** designed to automate and simplify library operations such as **book management, searching, borrowing, returning, and reporting**.

It replaces traditional manual methods with a more efficient, data-driven system. The project also integrates **custom algorithms (QuickSort and LinearSearch)** to demonstrate essential **Computer Science principles** within a practical library context.

---

## 🖼️ System Preview

*(Add screenshots here — e.g., Home Page, Admin Login, Manage Books, Reports, etc.)*  

---

## ✨ Features

- 🔍 **Book Search:** Keyword-based lookup using the LinearSearch algorithm  
- 📚 **Book Management:** Add, edit, delete, and organize book records efficiently  
- ⚡ **Sorting Algorithm:** QuickSort implementation for fast alphabetical ordering  
- 🧾 **Reports & Analytics:** Summaries of borrowed, returned, and active books  
- 👩‍💻 **Admin Panel:** Secure interface for managing library data  
- 🧠 **Data Structures Integration:** Queue, Array, and ArrayList in key modules  
- 🖥️ **Runtime Performance:** Algorithm running time shown in the terminal  

---

## 🧩 Project Structure

- UDLibrarySystem
   - src
      - udlib.assets
        - ud_logo.png
      - udlib.assets.covers
        - 9784682648750.png
        - no_cover1.png
      - udlib.db
        - DBConnection.java
      - udlib.ui
        - AdminDashboardFrame.java
        - AdminLoginFrame.java
        - BookDetailsFrame.java
        - BookRequestsFrame.java
        - HomeFrame.java
        - ManageBooksFrame.java
        - ReceiptFrame.java
        - ReportsFrame.java
        - ReturnedBooksFrame.java
        - SearchFrame.java
      - module-info.java
        - UDLibrarySystem
   - Referenced Libraries
        - mysql-connector-j-9.4.0.java
   - scripts
        - ud_library_db.sql
       
---

## 🗝️ Key File Descriptions

| File                       | Description                                                                                  |
| -------------------------- | -------------------------------------------------------------------------------------------- |
| `HomeFrame.java`           | Main entry point of the system; provides navigation to Admin and Student modules.            |
| `AdminLoginFrame.java`     | Handles administrator authentication and improved UI layout based on panel feedback.         |
| `AdminDashboardFrame.java` | Central control panel for managing books, requests, and reports.                             |
| `ManageBooksFrame.java`    | Enables adding, editing, deleting, and organizing book records (includes category dropdown). |
| `SearchFrame.java`         | Implements LinearSearch for finding books by title, author, or ISBN.                         |
| `BookDetailsFrame.java`    | Displays detailed book information and availability status.                                  |
| `BookRequestsFrame.java`   | Shows pending and approved borrowing requests using Queue logic.                             |
| `ReturnedBooksFrame.java`  | Displays history of all returned books and their timestamps.                                 |
| `ReportsFrame.java`        | Generates analytics and system summary reports for administrators.                           |
| `ReceiptFrame.java`        | Displays borrowing or returning receipt details.                                             |
| `DBConnection.java`        | Establishes and manages connection between Java Swing and MySQL database.                    |
| `module-info.java`         | Declares module metadata for the Java project (UDLibrarySystem).                             |
| `ud_library_db.sql`        | SQL script for initializing and importing the library database schema and data.              |

---

## 🛠️ Technologies Used

| Component            | Technology              |
| -------------------- | ----------------------- |
| Programming Language | Java                    |
| GUI Framework        | Java Swing              |
| Database             | MySQL (via phpMyAdmin)  |
| IDE                  | Eclipse                 |
| Version Control      | Git & GitHub            |
| Diagramming          | Canva                   |
| Algorithms           | QuickSort, LinearSearch |
| Data Structures      | Array, ArrayList, Queue |

---

## ⚙️ Running the Application

### 🧾 Prerequisites
- **Java JDK 21+**
- **Eclipse IDE**
- **XAMPP (Apache & MySQL)**
- **MySQL Connector JAR (mysql-connector-j-9.4.0.jar)**

### ▶️ Steps to Run
1. Launch **XAMPP** and start **Apache** and **MySQL**.  
2. Open **phpMyAdmin**, then import the SQL script: `ud_library_db.sql`
3. Open the project in **Eclipse IDE**.  
4. Add the MySQL connector JAR to the project’s **Referenced Libraries**.  
5. Run the application from: `HomeFrame.java`
6. Use the default admin credentials: `Username: jannelle` `Password: 12345`

---

## 🧱 Revisions Based on Panel Recommendations 

| No. | Recommendation                                                           | Revision Implemented                                                                      |
| --- | ------------------------------------------------------------------------ | ----------------------------------------------------------------------------------------- |
|  1  | Make the “Category” field (in Manage Books form) a drop-down.            | Replaced text input with a **JComboBox** containing predefined categories.                |
|  2  | Remove system running time from the interface (UI); keep it in terminal. | Removed all runtime labels from GUI; logs now display only in the **console**.            |
|  3  | Improve Admin Login Frame (spacing between labels and text fields).      | Adjusted layout for clearer alignment and better usability.                               |
|  4  | Increase padding between bottom buttons.                                 | Added uniform spacing to prevent button overlap and improve UI clarity.                   |
|  5  | Ensure consistent size for tables and forms.                             | Fixed frame dimensions; content now adjusts internally instead of resizing the entire UI. |

---
