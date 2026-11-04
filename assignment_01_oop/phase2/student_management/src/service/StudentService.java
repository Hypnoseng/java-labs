package assignment_01_oop.phase2.student_management.src.service;

import java.util.ArrayList;
import java.util.List;

import assignment_01_oop.phase2.student_management.src.model.Student;;

public class StudentService {

    private final List<Student> students = new ArrayList<>();

    public boolean addStudent(Student student) {
        if (findStudentById(student.getStudentId()) != null) {
            return false;
        }

        students.add(student);
        return true;
    }

    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equalsIgnoreCase(studentId)) {
                return student;
            }
        }

        return null;
    }

    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public int getStudentCount() {
        return students.size();
    }

    
}
