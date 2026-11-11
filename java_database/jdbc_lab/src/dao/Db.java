package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    // ตั้งค่าผ่าน environment variable: DB_URL, DB_USER, DB_PASS (ห้าม hardcode รหัสผ่านลง git)
    static final String URL  = env("DB_URL", "jdbc:mysql://localhost:3306/cos2208?allowPublicKeyRetrieval=true&useSSL=false");
    static final String USER = env("DB_USER", "root");
    static final String PASS = env("DB_PASS", "");

    private static String env(String key, String fallback) {
        String v = System.getenv(key);
        return v != null ? v : fallback;
    }

    private Db() { }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
