package dao;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.InputStream;
import java.nio.file.Files;

public class PhotoDao {

    public static boolean savePhoto(String student_code, Path imPath) throws Exception {

        try (InputStream inputStream = Files.newInputStream(imPath)) {

            byte[] data = inputStream.readAllBytes();

            String sql = "REPLACE INTO student_photos (student_id, photo) " +
                    "SELECT id, ? FROM students " +
                    "WHERE student_code = ?";

            try (Connection c = Db.connect();
                    PreparedStatement ps = c.prepareStatement(sql);) {

                ps.setBytes(1, data);
                ps.setString(2, student_code);

                int rows = ps.executeUpdate();
                return rows > 0;
            }
        }
    }

    public static byte[] loadPhoto(String studentCode) throws Exception {

        String sql = """
                    SELECT p.photo
                    FROM student_photos p
                    JOIN students s ON s.id = p.student_id
                    WHERE s.student_code = ?
                """;

        try (
                Connection c = Db.connect();
                PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, studentCode);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("photo");
                }

                return null;

            }
        }
    }

    public static boolean deletePhoto(String studentCode) throws SQLException {

        String sql = """
                    DELETE p
                    FROM student_photos p
                    JOIN students s ON s.id = p.student_id
                    WHERE s.student_code = ?
                """;

        try (
        Connection c = Db.connect();
        PreparedStatement ps = c.prepareStatement(sql)
        ) {
            ps.setString(1, studentCode);

            int rows = ps.executeUpdate();

            return rows > 0;
        }
    }
}