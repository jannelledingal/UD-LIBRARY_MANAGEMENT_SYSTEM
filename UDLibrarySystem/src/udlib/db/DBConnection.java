package udlib.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection connect() {
        try {
            String url = "jdbc:mysql://localhost:3306/ud_library_db";
            String user = "root";
            String pass = ""; 
            Connection conn = DriverManager.getConnection(url, user, pass);
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
