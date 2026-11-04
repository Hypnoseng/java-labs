package assignment_01_oop.phase2.student_management;

import java.util.List;

import assignment_01_oop.phase2.student_management.src.model.Student;
import assignment_01_oop.phase2.student_management.src.service.StudentService;

public class Main {
    private static final StudentService studentService = new StudentService();

    public static void main(String[] args) {

        seedData();

        while (true) {
            showMenu();

            String choice = IO.readln("Enter choice: ");

            switch (choice) {
                case "1" -> createStudent();
                case "2" -> readAllStudents();
                case "3" -> searchStudent();
                // case "4" -> updateStudent();
                // case "5" -> deleteStudent();
                case "6" -> {
                    IO.println("Exiting Student Management System...");
                    return;
                }
                default -> IO.println("Invalid choice. Please try again.");
            }

            IO.println("");
        }
    }

    private static void showMenu() {
        IO.println("==================================");
        IO.println("     STUDENT MANAGEMENT SYSTEM");
        IO.println("==================================");
        IO.println("1. Add Student");
        IO.println("2. Show All Students");
        IO.println("3. Search Student");
        IO.println("4. Update Student");
        IO.println("5. Delete Student");
        IO.println("6. Exit");
        IO.println("----------------------------------");
    }

    private static void createStudent() {
        IO.println("\n--- Add Student ---");

        String studentId = IO.readln("Student ID: ");
        String name = IO.readln("Name: ");
        String major = IO.readln("Major: ");
        int age = readInt("Age: ");
        double gpa = readDouble("GPA: ");

        Student student = new Student(
                studentId, name, major, age, gpa);

        if (studentService.addStudent(student)) {
            IO.println("Student added successfully.");
        } else {
            IO.println("Student ID already exists.");
        }
    }

    private static void searchStudent() {
        IO.println("\n--- Search Student ---");
        String studentId = IO.readln("Enter Student ID: ");

        Student student = studentService.findStudentById(studentId);

        if (student == null) {
            IO.println("Student not found.");
        }else{
            student.showInformation();
        }
    }

    private static void readAllStudents() {
        IO.println("\n--- All Students ---");

        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            IO.println("NO students found.");
            return;
        }
        for (Student student : students) {
            student.showInformation();
            IO.println("----------------------------------");
        }

        IO.println("Total Students : " + studentService.getStudentCount());
    }

    private static String readString(String message) {
        return IO.readln(message).trim();
    }

    private static int readInt(String message) {
        while (true) {
            try {
                return Integer.parseInt(readString(message));
            } catch (NumberFormatException e) {
                IO.println("Please enter a valid interger.");
            }
        }
    }

    private static double readDouble(String message) {
        while (true) {
            try {
                return Double.parseDouble(readString(message));
            } catch (NumberFormatException e) {
                IO.println("Please enter a valid number.");
            }
        }
    }

    // Initial data
    private static void seedData() {
        studentService.addStudent(
                new Student("S001", "Ray", "Computer Science", 24, 3.50));

        studentService.addStudent(
                new Student("S002", "John", "Information Technology", 21, 3.25));

        studentService.addStudent(
                new Student("S003", "Alice", "Computer Science", 22, 3.80));

        studentService.addStudent(
                new Student("S004", "Bob", "Software Engineering", 20, 3.10));

        studentService.addStudent(
                new Student("S005", "Emma", "Information Technology", 23, 3.60));
    }

}
