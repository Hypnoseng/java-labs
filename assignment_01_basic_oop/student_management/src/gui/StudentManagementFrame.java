package assignment_01_basic_oop.student_management.src.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import assignment_01_basic_oop.student_management.src.model.Student;
import assignment_01_basic_oop.student_management.src.service.StudentService;

public class StudentManagementFrame extends JFrame {

    private final StudentService studentService = new StudentService();

    private JTextField txtStudentId;
    private JTextField txtName;
    private JTextField txtMajor;
    private JTextField txtAge;
    private JTextField txtGpa;

    private JTable studentTable;
    private DefaultTableModel tableModel;

    public StudentManagementFrame() {
        setTitle("Student Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        seedData();
        initComponents();
        refreshTable();

        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Main Layout
        setLayout(new BorderLayout(10, 10));

        // =========================
        // Title
        // =========================
        JLabel lblTitle = new JLabel(
                "Student Management System",
                SwingConstants.CENTER);

        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));

        add(lblTitle, BorderLayout.NORTH);

        // =========================
        // Form
        // =========================
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(
                BorderFactory.createTitledBorder("Student Information"));

        txtStudentId = new JTextField();
        txtName = new JTextField();
        txtMajor = new JTextField();
        txtAge = new JTextField();
        txtGpa = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(txtStudentId);

        formPanel.add(new JLabel("Name:"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Major:"));
        formPanel.add(txtMajor);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);

        formPanel.add(new JLabel("GPA:"));
        formPanel.add(txtGpa);

        // =========================
        // Buttons
        // =========================
        JButton btnAdd = new JButton("Add");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear");
        JButton btnReadAll = new JButton("Read All");
        JButton btnSearch = new JButton("Search by ID");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        buttonPanel.add(btnReadAll);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // Form + Buttons
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        // =========================
        // JTable
        // =========================
        String[] columns = {
                "Student ID",
                "Name",
                "Major",
                "Age",
                "GPA"
        };

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        studentTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(studentTable);

        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Student List"));

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(
                BorderFactory.createEmptyBorder(0, 15, 15, 15));

        tablePanel.add(scrollPane, BorderLayout.CENTER);

        add(tablePanel, BorderLayout.CENTER);

        // =========================
        // Event Listeners
        // =========================
        btnReadAll.addActionListener(e -> readAllStudents());
        btnSearch.addActionListener(e -> searchStudentById());
        txtStudentId.addActionListener(e -> searchStudentById()); // Enter = search
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearForm());

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedStudent();
            }
        });
    }

    // =========================
    // CREATE
    // =========================
    private void addStudent() {
        try {
            String studentId = txtStudentId.getText().trim();
            String name = txtName.getText().trim();
            String major = txtMajor.getText().trim();

            int age = Integer.parseInt(txtAge.getText().trim());
            double gpa = Double.parseDouble(txtGpa.getText().trim());

            if (studentId.isEmpty() || name.isEmpty() || major.isEmpty()) {
                showMessage("Please fill in all fields.");
                return;
            }

            if (age <= 0 || gpa < 0 || gpa > 4.0) {
                showMessage("Age or GPA is invalid.");
                return;
            }

            Student student = new Student(
                    studentId, name, major, age, gpa);

            if (studentService.addStudent(student)) {
                refreshTable();
                clearForm();
                showMessage("Student added successfully.");
            } else {
                showMessage("Student ID already exists.");
            }

        } catch (NumberFormatException e) {
            showMessage("Age must be an integer and GPA must be a number.");
        }
    }

    // =========================
    // READ ALL
    // =========================
    private void readAllStudents() {
        clearForm();
        refreshTable();

        if (studentService.getStudentCount() == 0) {
            showMessage("No students found.");
        }
    }

    // =========================
    // READ BY ID
    // =========================
    private void searchStudentById() {
        String studentId = txtStudentId.getText().trim();

        if (studentId.isEmpty()) {
            showMessage("Please enter a Student ID to search.");
            return;
        }

        Student student = studentService.findStudentById(studentId);

        if (student == null) {
            showMessage("Student not found.");
            return;
        }

        // Show only the matched student in the table, then load it into the form
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[] {
                student.getStudentId(),
                student.getName(),
                student.getMajor(),
                student.getAge(),
                student.getGpa()
        });
        studentTable.setRowSelectionInterval(0, 0);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);

        for (Student student : studentService.getAllStudents()) {
            Object[] row = {
                    student.getStudentId(),
                    student.getName(),
                    student.getMajor(),
                    student.getAge(),
                    student.getGpa()
            };

            tableModel.addRow(row);
        }
    }

    // =========================
    // UPDATE
    // =========================
    private void updateStudent() {
        try {
            String studentId = txtStudentId.getText().trim();
            String name = txtName.getText().trim();
            String major = txtMajor.getText().trim();

            int age = Integer.parseInt(txtAge.getText().trim());
            double gpa = Double.parseDouble(txtGpa.getText().trim());

            if (studentId.isEmpty() || name.isEmpty() || major.isEmpty()) {
                showMessage("Please fill in all fields.");
                return;
            }

            if (age <= 0 || gpa < 0 || gpa > 4.0) {
                showMessage("Age or GPA is invalid.");
                return;
            }

            boolean updated = studentService.updateStudent(
                    studentId, name, major, age, gpa);

            if (updated) {
                refreshTable();
                clearForm();
                showMessage("Student updated successfully.");
            } else {
                showMessage("Student not found.");
            }

        } catch (NumberFormatException e) {
            showMessage("Age must be an integer and GPA must be a number.");
        }
    }

    // =========================
    // DELETE
    // =========================
    private void deleteStudent() {
        String studentId = txtStudentId.getText().trim();

        if (studentId.isEmpty()) {
            showMessage("Please select a student.");
            return;
        }

        int confirm = confirmMessage(
                "Are you sure you want to delete this student?",
                "Confirm Delete");

        if (confirm == JOptionPane.YES_OPTION) {
            if (studentService.deleteStudent(studentId)) {
                refreshTable();
                clearForm();
                showMessage("Student deleted successfully.");
            } else {
                showMessage("Student not found.");
            }
        }
    }

    // =========================
    // Load Selected Row
    // =========================
    private void loadSelectedStudent() {
        int selectedRow = studentTable.getSelectedRow();

        if (selectedRow == -1) {
            return;
        }

        txtStudentId.setText(
                tableModel.getValueAt(selectedRow, 0).toString());

        txtName.setText(
                tableModel.getValueAt(selectedRow, 1).toString());

        txtMajor.setText(
                tableModel.getValueAt(selectedRow, 2).toString());

        txtAge.setText(
                tableModel.getValueAt(selectedRow, 3).toString());

        txtGpa.setText(
                tableModel.getValueAt(selectedRow, 4).toString());

        // Prevent changing Student ID while editing
        txtStudentId.setEditable(false);
    }

    // =========================
    // Clear Form
    // =========================
    private void clearForm() {
        txtStudentId.setText("");
        txtName.setText("");
        txtMajor.setText("");
        txtAge.setText("");
        txtGpa.setText("");

        txtStudentId.setEditable(true);

        studentTable.clearSelection();
        txtStudentId.requestFocus();
    }

    // =========================
    // Message Dialog
    // =========================
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(
                null,
                centeredLabel(message),
                "Student Management",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private int confirmMessage(String message, String title) {
        return JOptionPane.showConfirmDialog(
                null,
                centeredLabel(message),
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
    }

    private JLabel centeredLabel(String text) {
        return new JLabel(text, SwingConstants.CENTER);
    }

    // Initial data
    private void seedData() {
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
