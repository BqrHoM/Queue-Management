package tools;

import java.sql.*;

public class Database {
    static String url = "jdbc:mysql://localhost:3306/queue_manager";
    static String user = "root";
    static String password = "147852369+/";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);

    }
}
