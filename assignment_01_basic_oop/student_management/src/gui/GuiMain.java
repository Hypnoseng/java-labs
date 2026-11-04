package assignment_01_basic_oop.student_management.src.gui;

import javax.swing.SwingUtilities;

public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementFrame frame = new StudentManagementFrame();

            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
