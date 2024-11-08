package com.project.dashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewEventsPage extends JFrame {

    private JTable eventsTable;
    private DefaultTableModel tableModel;

    public ViewEventsPage() {
        setTitle("View Events");
        setSize(600, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create table model and JTable
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Event Name");
        tableModel.addColumn("Event Date");
        tableModel.addColumn("Event Location");

        eventsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(eventsTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data into the table
        loadEvents();

        // Close button to return to Admin Dashboard
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }

    private void loadEvents() {
        String query = "SELECT * FROM events";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String eventName = rs.getString("event_name");
                Date eventDate = rs.getDate("event_date");
                String eventLocation = rs.getString("event_location");

                tableModel.addRow(new Object[]{id, eventName, eventDate, eventLocation});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error fetching events: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ViewEventsPage().setVisible(true);
        });
    }
}
