package com.project.auth;

import com.project.dashboard.AdminDashboard;
import com.project.dashboard.StudentDashboard;
import com.project.utils.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleCombo;

    public LoginPage() {
        setTitle("Login Page");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(5, 2, 10, 10));

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

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        add(loginButton);

        // Register Button
        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(e -> new RegisterPage().setVisible(true)); // Opens the RegisterPage when clicked
        add(registerButton);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String role = (String) roleCombo.getSelectedItem();

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            if (authenticateUser(username, password, role)) {
                JOptionPane.showMessageDialog(null, "Login successful as " + role);

                if ("Student".equals(role)) {
                    new StudentDashboard().setVisible(true);
                } else if ("Admin".equals(role)) {
                    new AdminDashboard().setVisible(true);
                }
                dispose(); // Close the LoginPage after successful login
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials. Please try again.");
            }
        }

        private boolean authenticateUser(String username, String password, String role) {
            String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, role);

                ResultSet rs = stmt.executeQuery();
                return rs.next(); // True if user exists, false otherwise
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                ex.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
    }
} 