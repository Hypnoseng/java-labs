package gui;

import dao.PhotoDao;
import dao.StudentDao;
import model.Student;

import java.awt.*;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class StudentForm extends JFrame {

    // รูปภาพ
    private JButton btnChooseImage;     // ปุ่มเลือกไฟล์รูป
    private Path selectedImagePath;     // path ของรูปที่เลือกไว้
    private JLabel imageLabel;          // ป้ายแสดงรูปนักศึกษา (หรือข้อความ No Photo)

    // JTable
    private JTable tblStudents;             // ตารางแสดงรายชื่อนักศึกษา
    private DefaultTableModel tableModel;   // ข้อมูลของตาราง

    // ช่องกรอกข้อมูล
    private JTextField textCode;        // รหัสนักศึกษา
    private JTextField txtFirstName;    // ชื่อ
    private JTextField txtLastName;     // นามสกุล
    private JTextField txtEmail;        // อีเมล
    private JTextField txtAge;          // อายุ

    // ปุ่มสั่งงาน
    private JButton btnAdd;             // เพิ่มนักศึกษา
    private JButton btnUpdate;          // แก้ไขข้อมูล
    private JButton btnDelete;          // ลบนักศึกษาที่เลือกในตาราง
    private JButton btnSearch;          // ค้นหาตามรหัสนักศึกษา
    private JButton btnRefresh;         // โหลดตารางใหม่และล้างฟอร์ม

    // แถบแสดงสถานะด้านล่างหน้าต่าง
    private JLabel lblStatus;

    public StudentForm() {
        setTitle("Student Management");
        setSize(1000, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = createFormPanel();
        JPanel photoPanel = createPhotoPanel();
        JPanel buttonPanel = createButtonPanel();

        lblStatus = new JLabel("Ready");

        JScrollPane scrollPane = createTablePane();

        loadStudents();

        // จัดวางหน้าต่าง
        setLayout(new BorderLayout(10, 10));

        // รวม Form และ Button ไว้ด้านบน
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));

        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(photoPanel, BorderLayout.EAST);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // วาง JTable ไว้ด้านล่างของหน้าต่าง
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.SOUTH);
    }

    // ---------- สร้างส่วนต่าง ๆ ของหน้าจอ ----------

    // ฟอร์มกรอกข้อมูลนักศึกษา
    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        textCode = new JTextField();
        txtFirstName = new JTextField();
        txtLastName = new JTextField();
        txtEmail = new JTextField();
        txtAge = new JTextField();

        formPanel.add(new JLabel("Student Code: "));
        formPanel.add(textCode);

        formPanel.add(new JLabel("First Name:"));
        formPanel.add(txtFirstName);

        formPanel.add(new JLabel("Last Name:"));
        formPanel.add(txtLastName);

        formPanel.add(new JLabel("Email:"));
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Age:"));
        formPanel.add(txtAge);

        return formPanel;
    }

    // Image Panel
    private JPanel createPhotoPanel() {
        imageLabel = new JLabel("No Photo");
        imageLabel.setPreferredSize(new Dimension(200, 200));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel photoPanel = new JPanel(new BorderLayout());
        photoPanel.add(imageLabel, BorderLayout.CENTER);

        return photoPanel;
    }

    // Button Panel พร้อมผูก Event ให้แต่ละปุ่ม
    private JPanel createButtonPanel() {
        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnSearch = new JButton("Search");
        btnRefresh = new JButton("Refresh");
        btnChooseImage = new JButton("Choose Image");

        btnSearch.addActionListener(e -> searchStudent());
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteSelectedStudent());
        btnChooseImage.addActionListener(e -> chooseImage());

        btnRefresh.addActionListener(e -> {
            loadStudents();
            clearForm();
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        buttonPanel.add(btnRefresh);
        buttonPanel.add(btnChooseImage);

        return buttonPanel;
    }

    // JTable: ตารางรายชื่อ และ event เมื่อคลิกเลือกแถว
    private JScrollPane createTablePane() {
        String[] columns = {
                "ID", "Code", "First Name", "Last Name", "Email", "Age"
        };

        tableModel = new DefaultTableModel(columns, 0);

        tblStudents = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblStudents.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tblStudents.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = tblStudents.getSelectedRow();

                if (selectedRow != -1) {
                    // ดึง Student Code
                    String studentCode = tableModel
                            .getValueAt(selectedRow, 1)
                            .toString();

                    // แสดงข้อมูลใน Form
                    textCode.setText(tableModel.getValueAt(selectedRow, 1).toString());
                    txtFirstName.setText(tableModel.getValueAt(selectedRow, 2).toString());
                    txtLastName.setText(tableModel.getValueAt(selectedRow, 3).toString());

                    Object email = tableModel.getValueAt(selectedRow, 4);
                    txtEmail.setText(email == null ? "" : email.toString());

                    Object age = tableModel.getValueAt(selectedRow, 5);
                    txtAge.setText(age == null ? "" : age.toString());

                    // แสดงรูปภาพตาม Student Code
                    try {
                        displayPhoto(studentCode);
                    } catch (SQLException ex) {
                        lblStatus.setText("Photo error: " + ex.getMessage());
                    }
                }
            }
        });

        return new JScrollPane(tblStudents);
    }

    private void searchStudent() {
        String code = textCode.getText().trim();

        if (code.isEmpty()) {
            lblStatus.setText("Please enter Student code");
            return;
        }

        try {
            Student student = StudentDao.findByCode(code);

            // JTable
            tableModel.setRowCount(0);

            if (student == null) {
                lblStatus.setText("Student not found");

                // ล้างรูปเดิมเมื่อค้นหาไม่พบ
                imageLabel.setIcon(null);
                imageLabel.setText("No Photo");
                return;
            }

            textCode.setText(student.code());
            txtFirstName.setText(student.firstName());
            txtLastName.setText(student.lastName());
            txtEmail.setText(student.email() == null ? "" : student.email());
            txtAge.setText(student.age() == null ? "" : String.valueOf(student.age()));

            // แสดงรูปของนักศึกษาที่ค้นพบ
            displayPhoto(student.code());

            // JTable
            tableModel.addRow(new Object[] {
                    student.id(),
                    student.code(),
                    student.firstName(),
                    student.lastName(),
                    student.email(),
            });

            lblStatus.setText("Found student: " + student.code());

        } catch (SQLException e) {
            lblStatus.setText("Database error");
            e.printStackTrace();
        }
    }

    private void addStudent() {
        String code = textCode.getText().trim();
        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String ageText = txtAge.getText().trim();

        if (code.isEmpty() || first.isEmpty()
                || last.isEmpty() || email.isEmpty()
                || ageText.isEmpty()) {

            lblStatus.setText("Please fill in all fields");
            return;
        }

        int age;
        try {
            age = Integer.parseInt(ageText);
            if (age <= 0) {
                lblStatus.setText("Age must be greater than zero \"0\" ");
                return;
            }
        } catch (NumberFormatException e) {
            lblStatus.setText("Age must be a number");
            return;
        }

        try {
            long id = StudentDao.insert(code, first, last, email, age);

            lblStatus.setText("Student added. Id: " + id);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                lblStatus.setText("Student code or email already exists");
            } else {
                lblStatus.setText("Database error");
                e.printStackTrace();
            }
        }
    }

    private void updateStudent() {
        String code = textCode.getText().trim();
        String first = txtFirstName.getText().trim();
        String last = txtLastName.getText().trim();
        String email = txtEmail.getText().trim();
        String ageText = txtAge.getText().trim();

        if (code.isEmpty() || first.isEmpty() || last.isEmpty()) {
            lblStatus.setText("Code, First Name, and Last Name are required.");
            return;
        }

        Integer age = null;

        if (!ageText.isEmpty()) {
            try {
                age = Integer.parseInt(ageText);

                if (age <= 0) {
                    lblStatus.setText("Age must be greater than zero.");
                    return;
                }
            } catch (NumberFormatException e) {
                lblStatus.setText("Age must be a valid integer.");
                return;
            }
        }

        try {
            int rows = StudentDao.update(code, first, last, email, age);

            if (rows > 0) {
                lblStatus.setText("Student updated successfully.");
            } else {
                lblStatus.setText("Student not found.");
            }

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                lblStatus.setText("Student code or email already exists");
            } else {
                lblStatus.setText("Database error");
                e.printStackTrace();
            }
        }
    }

    private void deleteSelectedStudent() {
        int selectedRow = tblStudents.getSelectedRow();

        if (selectedRow == -1) {
            lblStatus.setText("Please select a student.");
            return;
        }

        String id = tableModel.getValueAt(selectedRow, 1).toString();

        // ยืนยันก่อนลบ
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete Student " + id + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            int rows = StudentDao.deleteByCode(id);

            if (rows > 0) {
                lblStatus.setText("Student deleted successfully");
                clearForm();
            } else {
                lblStatus.setText("Student not found");
            }
        } catch (SQLException e) {
            lblStatus.setText("Database error: " + e.getMessage());
        }
    }

    private void displayPhoto(String studentCode) throws SQLException {
        try {
            byte[] photo = PhotoDao.loadPhoto(studentCode);

            if (photo != null) {
                ImageIcon icon = new ImageIcon(photo);

                Image image = icon.getImage().getScaledInstance(
                        200,
                        200,
                        Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(image));
            } else {
                imageLabel.setIcon(null);
                imageLabel.setText("No Photo");
            }

        } catch (Exception e) {
            imageLabel.setIcon(null);
            imageLabel.setText("Photo error");
            lblStatus.setText("Photo error: " + e.getMessage());
        }
    }

    private void clearForm() {
        textCode.setText("");
        txtFirstName.setText("");
        txtLastName.setText("");
        txtEmail.setText("");
        txtAge.setText("");
    }

    private void loadStudents() {
        tableModel.setRowCount(0);

        try {
            List<Student> students = StudentDao.listAll();

            for (Student student : students) {
                tableModel.addRow(new Object[] {
                        student.id(),
                        student.code(),
                        student.firstName(),
                        student.lastName(),
                        student.email(),
                        student.age()
                });
            }
            lblStatus.setText("Loaded " + students.size() + " students.");

        } catch (SQLException e) {
            lblStatus.setText("Database error: " + e.getMessage());
        }
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentForm form = new StudentForm();
            form.setVisible(true);
        });
    }
}
