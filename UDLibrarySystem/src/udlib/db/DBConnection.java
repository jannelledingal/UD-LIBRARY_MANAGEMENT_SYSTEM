package udlib.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ✅ load driver

            String url = "jdbc:mysql://localhost:3306/ud_library_db?useSSL=false&serverTimezone=UTC";
            String user = "root";
            String pass = ""; // leave blank for default XAMPP

            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Database connected successfully!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("❌ Database connection failed: " + e.getMessage());
            return null;
        }
    }
}
