package com.project.dashboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DashboardPage extends JFrame {
    private String role;

    public DashboardPage(String role) {
        this.role = role;
        setTitle(role + " Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Section
        JLabel headerLabel = new JLabel(role + " Dashboard", JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(headerLabel, BorderLayout.NORTH);

        // Content Section
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        if (role.equals("Student")) {
            createStudentDashboard(contentPanel);
        } else if (role.equals("Admin")) {
            createAdminDashboard(contentPanel);
        }
        
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginPage().setVisible(true);
            }
        });
        add(logoutButton, BorderLayout.SOUTH);
    }

    private void createStudentDashboard(JPanel contentPanel) {
        JButton viewEventsButton = new JButton("View Available Events and Hackathons");
        viewEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to view available events
                JOptionPane.showMessageDialog(null, "View Available Events - Functionality Coming Soon!");
            }
        });
        contentPanel.add(viewEventsButton);

        JButton viewRegisteredEventsButton = new JButton("View Registered Events");
        viewRegisteredEventsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to view registered events
                JOptionPane.showMessageDialog(null, "View Registered Events - Functionality Coming Soon!");
            }
        });
        contentPanel.add(viewRegisteredEventsButton);
    }

    private void createAdminDashboard(JPanel contentPanel) {
        JButton addEventButton = new JButton("Add New Event / Hackathon");
        addEventButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to add new event
                JOptionPane.showMessageDialog(null, "Add Event - Functionality Coming Soon!");
            }
        });
        contentPanel.add(addEventButton);

        JButton viewRegisteredStudentsButton = new JButton("View Registered Students");
        viewRegisteredStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to view registered students
                JOptionPane.showMessageDialog(null, "View Registered Students - Functionality Coming Soon!");
            }
        });
        contentPanel.add(viewRegisteredStudentsButton);

        JButton manageEventRegistrationsButton = new JButton("Manage Event Registrations");
        manageEventRegistrationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic to manage event registrations
                JOptionPane.showMessageDialog(null, "Manage Event Registrations - Functionality Coming Soon!");
            }
        });
        contentPanel.add(manageEventRegistrationsButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                String role = "Student";  // Or "Admin" based on login
                new DashboardPage(role).setVisible(true);
            }
        });
    }
}
