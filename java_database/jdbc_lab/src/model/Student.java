package model;

import  java.sql.ResultSet;
import  java.sql.SQLException;
/** หนึ่งแถวของตาราง students (ไม่รวม created_at) */
public record Student(long id, String code, String firstName, String lastName, String email, Integer age) {

    /** อ่านแถวที่ ResultSet ชี้อยู่ตอนนี้ แล้วสร้างเป็น Student */
    public static Student from(ResultSet r) throws SQLException {
        int age = r.getInt("age");
        return new Student(
                r.getLong("id"),
                r.getString("student_code"),
                r.getString("first_name"),
                r.getString("last_name"),
                r.getString("email"),
                r.wasNull() ? null : age);   // age ว่างได้ getInt จะคืน 0 จึงต้องเช็ก wasNull
    }

    @Override public String toString() {
        return id + " | " + code + " | " + firstName + " " + lastName + " | " + email + " | " + age;
    }
}
