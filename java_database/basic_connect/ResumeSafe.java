package java_database.basic_connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumeSafe {

    // Credentials come from environment variables (never hardcode secrets)
    static final String URL = System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:3306/java_training");
    static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    static final String PASS = System.getenv("DB_PASS");

    public static void main(String[] args) {
        String keyword = args.length > 0 ? args[0] : "Ray";

        String sql = "SELECT id, name, surname, address FROM resume WHERE name = ?";

        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
                PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, keyword);
            try (ResultSet r = ps.executeQuery()) {
                int found = 0;
                while (r.next()) {
                    found++;
                    System.out.println(
                            "ID:" + r.getString("id")
                                    + "  Name:" + r.getString("name")
                                    + "  Surname:" + r.getString("surname")
                                    + "  Address:" + r.getString("address"));
                }
                IO.println("พบ " + found + " แถว");
            }

        } catch (SQLException e) {
            IO.println("Cant not connect database " + e.getMessage());
        }
    }
}
