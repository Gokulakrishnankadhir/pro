package com.project.dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.project.utils.DBConnection;

public class AdminDashboard extends JFrame {

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton addEventButton = new JButton("Add Event");
        addEventButton.addActionListener(e -> new AddEventPage().setVisible(true));

        JButton updateEventButton = new JButton("Update Event");
        updateEventButton.addActionListener(new UpdateEventAction());

        JButton deleteEventButton = new JButton("Delete Event");
        deleteEventButton.addActionListener(new DeleteEventAction());

        setLayout(new GridLayout(3, 1, 10, 10));
        add(addEventButton);
        add(updateEventButton);
        add(deleteEventButton);
    }

    private class UpdateEventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String eventIdStr = JOptionPane.showInputDialog("Enter Event ID to Update:");
            if (eventIdStr != null && !eventIdStr.isEmpty()) {
                try {
                    int eventId = Integer.parseInt(eventIdStr);
                    String newEventName = JOptionPane.showInputDialog("Enter New Event Name:");
                    String newLocation = JOptionPane.showInputDialog("Enter New Event Location:");
                    String newDateStr = JOptionPane.showInputDialog("Enter New Event Date (YYYY-MM-DD):");
                    String newDescription = JOptionPane.showInputDialog("Enter New Event Description:");

                    String query = "UPDATE events SET event_name = ?, location = ?, event_date = ?, description = ? WHERE event_id = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query)) {

                        stmt.setString(1, newEventName);
                        stmt.setString(2, newLocation);
                        stmt.setDate(3, Date.valueOf(newDateStr));
                        stmt.setString(4, newDescription);
                        stmt.setInt(5, eventId);

                        int rowsUpdated = stmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Event updated successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Event not found or failed to update.");
                        }
                    }
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    private class DeleteEventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String eventIdStr = JOptionPane.showInputDialog("Enter Event ID to Delete:");
            if (eventIdStr != null && !eventIdStr.isEmpty()) {
                try {
                    int eventId = Integer.parseInt(eventIdStr);
                    String query = "DELETE FROM events WHERE event_id = ?";
                    try (Connection conn = DBConnection.getConnection();
                         PreparedStatement stmt = conn.prepareStatement(query)) {
                        stmt.setInt(1, eventId);

                        int rowsDeleted = stmt.executeUpdate();
                        if (rowsDeleted > 0) {
                            JOptionPane.showMessageDialog(null, "Event deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Event not found or failed to delete.");
                        }
                    }
                } catch (SQLException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
