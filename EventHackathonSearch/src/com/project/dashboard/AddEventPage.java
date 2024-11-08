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
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AddEventPage extends JFrame {

    private JTextField eventNameField;
    private JTextField eventLocationField;
    private JTextField eventDateField;
    private JTextField collegeField;
    private JTextField posterField;
    private JTextArea eventDescriptionField;
    private JTextField createdByField;

    public AddEventPage() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Add Event");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));

        add(new JLabel("Event Name:"));
        eventNameField = new JTextField(20);
        add(eventNameField);

        add(new JLabel("Event Location:"));
        eventLocationField = new JTextField(20);
        add(eventLocationField);

        add(new JLabel("Event Date (YYYY-MM-DD):"));
        eventDateField = new JTextField(20);
        add(eventDateField);

        add(new JLabel("College:"));
        collegeField = new JTextField(20);
        add(collegeField);

        add(new JLabel("Poster URL:"));
        posterField = new JTextField(20);
        add(posterField);

        add(new JLabel("Event Description:"));
        eventDescriptionField = new JTextArea(3, 20);
        add(new JScrollPane(eventDescriptionField));

        add(new JLabel("Created By:"));
        createdByField = new JTextField(20);
        add(createdByField);

        JButton addButton = new JButton("Add Event");
        addButton.addActionListener(new AddEventAction());
        add(addButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dispose());
        add(cancelButton);
    }

    private class AddEventAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String eventName = eventNameField.getText().trim();
            String eventLocation = eventLocationField.getText().trim();
            String eventDate = eventDateField.getText().trim();
            String college = collegeField.getText().trim();
            String poster = posterField.getText().trim();
            String eventDescription = eventDescriptionField.getText().trim();
            String createdBy = createdByField.getText().trim();

            // Check if any required field is empty
            if (eventName.isEmpty() || eventLocation.isEmpty() || eventDate.isEmpty() ||
                college.isEmpty() || eventDescription.isEmpty() || createdBy.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            // Validate the event date format
            try {
                LocalDate.parse(eventDate);
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(null, "Invalid date format. Please use YYYY-MM-DD.");
                return;
            }

            // Attempt to add the event to the database
            if (addEvent(eventName, eventLocation, eventDate, college, poster, eventDescription, createdBy)) {
                JOptionPane.showMessageDialog(null, "Event added successfully!");
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Error adding event.");
            }
        }

        private boolean addEvent(String eventName, String eventLocation, String eventDate, String college,
                                 String poster, String eventDescription, String createdBy) {
            String query = "INSERT INTO events (event_name, location, event_date, college, poster, details, created_by) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, eventName);
                stmt.setString(2, eventLocation);
                stmt.setDate(3, Date.valueOf(eventDate));  // Convert string to SQL Date
                stmt.setString(4, college);
                stmt.setString(5, poster);
                stmt.setString(6, eventDescription);  // Use 'details' column for description
                stmt.setString(7, createdBy);

                int rowsInserted = stmt.executeUpdate();
                return rowsInserted > 0;

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
                ex.printStackTrace();
                return false;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddEventPage().setVisible(true));
    }
}
