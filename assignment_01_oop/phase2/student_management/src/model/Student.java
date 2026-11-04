package assignment_01_oop.phase2.student_management.src.model;

public class Student {
    private String studentId;
    private String name;
    private String major;
    private int age;
    private double gpa;

    public Student(String studentId, String name, String major,
            int age, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.age = age;
        this.gpa = gpa;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getMajor() {
        return major;
    }

    public int getAge() {
        return age;
    }

    public double getGpa() {
        return gpa;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public void showInformation() {
        System.out.println("Student ID: " + studentId);
        System.out.println("Name      : " + name);
        System.out.println("Major     : " + major);
        System.out.println("Age       : " + age);
        System.out.printf("GPA       : %.2f%n", gpa);
    }

    public void study() {
        System.out.println(name + " is studying.");
    }

    public void takeExam() {
        System.out.println(name + " is taking an exam.");
    }
}