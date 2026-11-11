package dao;

import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    // INSERT

    public static long insert(String code,
            String first,
            String last,
            String email,
            int age) throws SQLException {
        String sql = "INSERT INTO students (student_code, " +
                "first_name, " +
                "last_name, " +
                "email, " +
                "age) VALUES (?, ?, ?, ?, ?)";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            p.setString(1, code);
            p.setString(2, first);
            p.setString(3, last);
            p.setString(4, email);
            p.setInt(5, age);
            p.executeUpdate();
            try (ResultSet keys = p.getGeneratedKeys()) {
                keys.next();
                return keys.getLong(1);
            }
        }
    }

    // ค้นหา (ตัวอย่าง 5.9) คืน null ถ้าไม่พบ
    public static Student findByCode(String code) throws SQLException {
        String sql = "SELECT * FROM students WHERE student_code = ?";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, code);

            try (ResultSet r = p.executeQuery()) {
                
                return r.next() ? Student.from(r) : null;

            }
        }

    }

    public static int deleteByCode(String code) throws SQLException {
        String sql = "DELETE FROM students WHERE student_code = ?";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql)) {
            p.setString(1, code);
            return p.executeUpdate();
        }
    }

    public static int update(
            String code,
            String first,
            String last,
            String email,
            int age) throws SQLException {

        String sql = "UPDATE students SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "email = ?, " +
                "age = ? " +
                "WHERE student_code = ?";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, first);
            p.setString(2, last);
            p.setString(3, email);
            p.setInt(4, age);
            p.setString(5, code);

            return p.executeUpdate();
        }
    }

    public static boolean existsByCode(String code) throws SQLException {
        String sql = "SELECT 1 FROM students WHERE student_code = ?";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql)) {

            p.setString(1, code);

            try (ResultSet rs = p.executeQuery()) {
                return rs.next();
            }
        }
    }

    // SELECT ALL
    public static List<Student> listAll() throws SQLException {
        String sql = "SELECT * FROM students ORDER BY id";

        List<Student> students = new ArrayList<>();

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql);
                ResultSet r = p.executeQuery()) {

            while (r.next()) {
                Student student = Student.from(r);
                students.add(student);
            }
        }

        return students;
    }

    // PrintALL
    public static void printAll() throws SQLException {
        String sql = "Select * from students Order By id";

        try (Connection c = Db.connect();
                PreparedStatement p = c.prepareStatement(sql);
                ResultSet r = p.executeQuery()) {

            while (r.next()) {
                long id = r.getLong("id");
                String student_code = r.getString("student_code");
                String first = r.getString("first_name");
                String last = r.getString("last_name");
                String email = r.getString("email");
                int age = r.getInt("age");

                IO.println(
                        id + " | "
                                + student_code + " | "
                                + first + " | "
                                + last + " | "
                                + email + " | "
                                + age + " | ");
            }
        } 
    }
}
