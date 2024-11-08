package com.project.dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.project.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDashboard extends JFrame {
    public StudentDashboard() {
        setTitle("Student Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton viewEventsButton = new JButton("View Available Events");
        viewEventsButton.addActionListener(new ViewEventsAction());

        JButton registerEventButton = new JButton("Register for Event");
        registerEventButton.addActionListener(new RegisterEventAction());

        setLayout(new GridLayout(3, 1, 10, 10));
        add(viewEventsButton);
        add(registerEventButton);
    }

    private class ViewEventsAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String query = "SELECT * FROM events";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                StringBuilder eventsList = new StringBuilder("Available Events:\n");
                while (rs.next()) {
                    eventsList.append("Event Name: ").append(rs.getString("event_name"))
                            .append(", Date: ").append(rs.getString("event_date"))
                            .append(", Location: ").append(rs.getString("location"))
                            .append("\n");
                }
                JOptionPane.showMessageDialog(null, eventsList.toString());
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }

    private class RegisterEventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Ask for the event ID the student wants to register for
            String eventIdStr = JOptionPane.showInputDialog("Enter Event ID to Register:");
            if (eventIdStr != null && !eventIdStr.isEmpty()) {
                int eventId = Integer.parseInt(eventIdStr);

                // Assuming we have the student ID available after login
                int studentId = 1; // Placeholder for student ID (you need to retrieve this after login)

                // Register the student for the event
                String query = "INSERT INTO registrations (student_id, event_id) VALUES (?, ?)";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, studentId);
                    stmt.setInt(2, eventId);

                    int rowsInserted = stmt.executeUpdate();
                    if (rowsInserted > 0) {
                        JOptionPane.showMessageDialog(null, "Registration successful!");
                    } else {
                        JOptionPane.showMessageDialog(null, "Registration failed. Please try again.");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentDashboard().setVisible(true));
    }
}