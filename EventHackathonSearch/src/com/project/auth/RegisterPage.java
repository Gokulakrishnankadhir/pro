package com.project.auth;

import com.project.utils.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.*;

public class RegisterPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;
    private JTextField collegeField;
    private JTextField emailField;

    public RegisterPage() {
        setTitle("Register Page");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));

        // Username Label and Field
        add(new JLabel("Username:"));
        usernameField = new JTextField(20);
        add(usernameField);

        // Password Label and Field
        add(new JLabel("Password:"));
        passwordField = new JPasswordField(20);
        add(passwordField);

        // Role Dropdown
        add(new JLabel("Role:"));
        String[] roles = { "Student", "Admin" };
        roleCombo = new JComboBox<>(roles);
        add(roleCombo);

        // College Label and Field
        add(new JLabel("College:"));
        collegeField = new JTextField(20);
        add(collegeField);

        // Email Label and Field
        add(new JLabel("Email:"));
        emailField = new JTextField(20);
        add(emailField);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        add(registerButton);
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleCombo.getSelectedItem();
            String college = collegeField.getText();
            String email = emailField.getText();

            if (registerUser(username, password, role, college, email)) {
                JOptionPane.showMessageDialog(null, "Registration successful!");
                dispose(); // Close registration form after successful registration
            } else {
                JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
            }
        }

        private boolean registerUser(String username, String password, String role, String college, String email) {
            String query = "INSERT INTO users (username, password, role, college, email) VALUES (?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);
                stmt.setString(4, college);
                stmt.setString(5, email);

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0; // Returns true if the user was successfully registered
            } catch (SQLException ex) {
                ex.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterPage().setVisible(true);
        });
    }
}
