package assignment_01_basic_oop.student_management.src.service;

import java.util.ArrayList;
import java.util.List;

import assignment_01_basic_oop.student_management.src.model.Student;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    // CREATE
    public boolean addStudent(Student student) {
        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }

        students.add(student);
        return true;
    }

    // READ BY ID
    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    // READ ALL
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public int getStudentCount() {
        return students.size();
    }

    // Update
    public boolean updateStudent(String studentId, String name,
            String major, int age, double gpa) {

        Student student = findStudentById(studentId);

        if (student == null) {
            return false;
        }

        student.setName(name);
        student.setMajor(major);
        student.setAge(age);
        student.setGpa(gpa);

        return true;
    }

    // DELETE
    public boolean deleteStudent(String studentId) {
        Student student = findStudentById(studentId);

        if (student == null) {
            return false;
        }

        students.remove(student);
        return true;
    }

}
