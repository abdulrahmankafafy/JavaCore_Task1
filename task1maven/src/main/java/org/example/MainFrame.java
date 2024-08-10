package org.example;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.example.dao.framework.UserProfileDAO;
import org.example.dao.framework.DepartmentDAO;
import org.example.entity.UserProfile;
import org.example.entity.Department;
import org.example.model.UserModel;
import org.example.utilities.NationalIdUtils;

public class MainFrame extends JFrame {

    private final UserProfileDAO userProfileDAO;
    private final DepartmentDAO departmentDAO;

    public MainFrame(UserProfileDAO userProfileDAO, DepartmentDAO departmentDAO) {
        this.userProfileDAO = userProfileDAO;
        this.departmentDAO = departmentDAO;

        // Set frame properties
        setTitle("User and Department Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create UI components
        initUI();

        setVisible(true);
    }

    private void initUI() {
        // Use a tabbed pane to separate user and department management
        JTabbedPane tabbedPane = new JTabbedPane();

        // User management panel
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout());
        userPanel.add(createUserManagementPanel(), BorderLayout.CENTER);

        // Department management panel
        JPanel departmentPanel = new JPanel();
        departmentPanel.setLayout(new BorderLayout());
        departmentPanel.add(createDepartmentManagementPanel(), BorderLayout.CENTER);

        // Add panels to tabbed pane
        tabbedPane.addTab("Users", userPanel);
        tabbedPane.addTab("Departments", departmentPanel);

        // Add tabbed pane to frame
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createUserManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create buttons for user actions
        JButton addUserButton = new JButton("Add User");
        JButton updateUserButton = new JButton("Update User");
        JButton deleteUserButton = new JButton("Delete User");
        JButton viewUsersButton = new JButton("View All Users");
        JButton viewTopSalariesButton = new JButton("View Top 5 Salaries"); // New button

        // Add action listeners to buttons
        addUserButton.addActionListener(e -> addUser());
        updateUserButton.addActionListener(e -> updateUser());
        deleteUserButton.addActionListener(e -> deleteUser());
        viewUsersButton.addActionListener(e -> viewAllUsers());
        viewTopSalariesButton.addActionListener(e -> viewTopFiveSalaries()); // Action listener for new button

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addUserButton);
        buttonPanel.add(updateUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(viewUsersButton);
        buttonPanel.add(viewTopSalariesButton); // Add new button to panel

        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private JPanel createDepartmentManagementPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Create buttons for department actions
        JButton addDepartmentButton = new JButton("Add Department");
        JButton updateDepartmentButton = new JButton("Update Department");
        JButton deleteDepartmentButton = new JButton("Delete Department");
        JButton viewDepartmentsButton = new JButton("View All Departments"); // Create the view departments button

        // Add action listeners to buttons
        addDepartmentButton.addActionListener(e -> addDepartment());
        updateDepartmentButton.addActionListener(e -> updateDepartment());
        deleteDepartmentButton.addActionListener(e -> deleteDepartment());
        viewDepartmentsButton.addActionListener(e -> viewAllDepartments()); // Set action listener

        // Create button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addDepartmentButton);
        buttonPanel.add(updateDepartmentButton);
        buttonPanel.add(deleteDepartmentButton);
        buttonPanel.add(viewDepartmentsButton); // Add view departments button to the panel

        panel.add(buttonPanel, BorderLayout.NORTH);

        return panel;
    }

    private void addUser() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JTextField passwordField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField nationalIdField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField departmentIdField = new JTextField();
        JTextField salaryField = new JTextField();

        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Address:"));
        panel.add(addressField);
        panel.add(new JLabel("National ID:"));
        panel.add(nationalIdField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Department ID:"));
        panel.add(departmentIdField);
        panel.add(new JLabel("Salary:"));
        panel.add(salaryField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = passwordField.getText();
                String address = addressField.getText();
                String nationalId = nationalIdField.getText();
                String phone = phoneField.getText();
                String departmentIdText = departmentIdField.getText();
                String salaryText = salaryField.getText();

                // Validate input
                validateUserInput(name, username, password, address, nationalId, phone, departmentIdText, salaryText);

                int departmentId = Integer.parseInt(departmentIdText);
                double salary = Double.parseDouble(salaryText);

                LocalDate birthDate = NationalIdUtils.extractBirthDateFromNationalId(nationalId);

                // Check for unique National ID
                if (userProfileDAO.getUserProfileByNationalId(nationalId) != null) {
                    throw new IllegalArgumentException("National ID must be unique. This ID is already in use.");
                }

                UserProfile newUser = new UserProfile();
                newUser.setName(name);
                newUser.setUserName(username);
                newUser.setPassword(password);
                newUser.setAddress(address);
                newUser.setNationalId(nationalId);
                newUser.setPhone(phone);
                newUser.setDepartmentId(departmentId);
                newUser.setSalary(salary);
                newUser.setBirthDate(birthDate);

                userProfileDAO.addUserProfile(newUser);
                JOptionPane.showMessageDialog(this, "User added successfully!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateUser() {
        // Create a dialog for updating a user
        String userIdStr = JOptionPane.showInputDialog(this, "Enter User ID to Update:");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            UserProfile user = userProfileDAO.getUserProfileById(userId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField nameField = new JTextField(user.getName());
            JTextField usernameField = new JTextField(user.getUserName());
            JTextField passwordField = new JTextField(user.getPassword());
            JTextField addressField = new JTextField(user.getAddress());
            JTextField nationalIdField = new JTextField(user.getNationalId());
            JTextField phoneField = new JTextField(user.getPhone());
            JTextField departmentIdField = new JTextField(String.valueOf(user.getDepartmentId()));
            JTextField salaryField = new JTextField(String.valueOf(user.getSalary()));

            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Username:"));
            panel.add(usernameField);
            panel.add(new JLabel("Password:"));
            panel.add(passwordField);
            panel.add(new JLabel("Address:"));
            panel.add(addressField);
            panel.add(new JLabel("National ID:"));
            panel.add(nationalIdField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);
            panel.add(new JLabel("Department ID:"));
            panel.add(departmentIdField);
            panel.add(new JLabel("Salary:"));
            panel.add(salaryField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update User", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    String username = usernameField.getText();
                    String password = passwordField.getText();
                    String address = addressField.getText();
                    String nationalId = nationalIdField.getText();
                    String phone = phoneField.getText();
                    String departmentIdText = departmentIdField.getText();
                    String salaryText = salaryField.getText();

                    // Validate input
                    validateUserInput(name, username, password, address, nationalId, phone, departmentIdText, salaryText);

                    int departmentId = Integer.parseInt(departmentIdText);
                    double salary = Double.parseDouble(salaryText);

                    LocalDate birthDate = NationalIdUtils.extractBirthDateFromNationalId(nationalId);

                    // Check for unique National ID
                    if (!nationalId.equals(user.getNationalId()) && userProfileDAO.getUserProfileByNationalId(nationalId) != null) {
                        throw new IllegalArgumentException("National ID must be unique. This ID is already in use.");
                    }

                    user.setName(name);
                    user.setUserName(username);
                    user.setPassword(password);
                    user.setAddress(address);
                    user.setNationalId(nationalId);
                    user.setPhone(phone);
                    user.setDepartmentId(departmentId);
                    user.setSalary(salary);
                    user.setBirthDate(birthDate);

                    userProfileDAO.updateUserProfile(user);
                    JOptionPane.showMessageDialog(this, "User updated successfully!");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid User ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void validateUserInput(String name, String username, String password, String address,
                                   String nationalId, String phone, String departmentId, String salary) throws IllegalArgumentException {
        if (isNullOrEmpty(name)) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (isNullOrEmpty(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty.");
        }
        if (isNullOrEmpty(password)) {
            throw new IllegalArgumentException("Password cannot be null or empty.");
        }
        if (isNullOrEmpty(address)) {
            throw new IllegalArgumentException("Address cannot be null or empty.");
        }
        if (isNullOrEmpty(nationalId)) {
            throw new IllegalArgumentException("National ID cannot be null or empty.");
        }
        if (isNullOrEmpty(phone)) {
            throw new IllegalArgumentException("Phone number cannot be null or empty.");
        }
        if (isNullOrEmpty(departmentId)) {
            throw new IllegalArgumentException("Department ID cannot be null or empty.");
        }
        if (isNullOrEmpty(salary)) {
            throw new IllegalArgumentException("Salary cannot be null or empty.");
        }

        // Validate phone number format
        if (!isValidPhoneNumber(phone)) {
            throw new IllegalArgumentException("Invalid phone number format. Must start with +20 or 002 and be 13 or 14 digits long.");
        }
    }

    private boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    private boolean isValidPhoneNumber(String phone) {
        return (phone.startsWith("+20") || phone.startsWith("002")) && (phone.length() == 13 || phone.length() == 14) && phone.matches("\\+?\\d+");
    }

    private void deleteUser() {
        // Create a dialog to confirm user deletion
        String userIdStr = JOptionPane.showInputDialog(this, "Enter User ID to Delete:");
        if (userIdStr == null || userIdStr.trim().isEmpty()) {
            return;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            UserProfile user = userProfileDAO.getUserProfileById(userId);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                userProfileDAO.deleteUserProfile(userId);
                JOptionPane.showMessageDialog(this, "User deleted successfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid User ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllUsers() {
        // Display all users in a text area
        List<UserProfile> users = userProfileDAO.getAllUserProfiles();
        if (users.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (UserProfile user : users) {
            sb.append(user).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "All Users", JOptionPane.INFORMATION_MESSAGE);
    }

    private void viewTopFiveSalaries() {
        List<UserModel> topUsers = userProfileDAO.getTopFiveSalaries();
        StringBuilder sb = new StringBuilder();

        if (topUsers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No users found!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Separate lists for departments
        List<UserModel> department1Users = new ArrayList<>();
        List<UserModel> department2Users = new ArrayList<>();

        // Categorize users by department
        for (UserModel user : topUsers) {
            if (user.getDepartmentId() == 1) {
                department1Users.add(user);
            } else if (user.getDepartmentId() == 2) {
                department2Users.add(user);
            }
        }

        // Append department 1 users
        sb.append("Top 5 Salaries in Department 1:\n");
        for (UserModel user : department1Users) {
            sb.append("Name: ").append(user.getName())
                    .append(", Age: ").append(user.getAge())
                    .append(", Salary: ").append(user.getSalary())
                    .append(", Department: ").append(user.getDepartmentName())
                    .append(", User ID: ").append(user.getUserId())
                    .append("\n");
        }

        // Append department 2 users
        sb.append("\nTop 5 Salaries in Department 2:\n");
        for (UserModel user : department2Users) {
            sb.append("Name: ").append(user.getName())
                    .append(", Age: ").append(user.getAge())
                    .append(", Salary: ").append(user.getSalary())
                    .append(", Department: ").append(user.getDepartmentName())
                    .append(", User ID: ").append(user.getUserId())
                    .append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "Top 5 Salaries by Department", JOptionPane.INFORMATION_MESSAGE);
    }

    private void addDepartment() {
        // Create a dialog for adding a new department
        JPanel panel = new JPanel(new GridLayout(0, 1));
        JTextField departmentNameField = new JTextField();
        JTextField userIdField = new JTextField();

        panel.add(new JLabel("Department Name:"));
        panel.add(departmentNameField);
        panel.add(new JLabel("User ID:"));
        panel.add(userIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "Add Department", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            // Validate input and create department
            try {
                String departmentName = departmentNameField.getText();
                String userIdText = userIdField.getText();

                if (isNullOrEmpty(departmentName)) {
                    throw new IllegalArgumentException("Department name cannot be null or empty.");
                }
                if (isNullOrEmpty(userIdText)) {
                    throw new IllegalArgumentException("User ID cannot be null or empty.");
                }

                int userId = Integer.parseInt(userIdText);

                Department newDepartment = new Department();
                newDepartment.setDepartmentName(departmentName);
                newDepartment.setUserId(userId);

                departmentDAO.addDepartment(newDepartment);
                JOptionPane.showMessageDialog(this, "Department added successfully!");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error adding department: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void updateDepartment() {
        // Create a dialog for updating a department
        String departmentIdStr = JOptionPane.showInputDialog(this, "Enter Department ID to Update:");
        if (departmentIdStr == null || departmentIdStr.trim().isEmpty()) {
            return;
        }

        try {
            int departmentId = Integer.parseInt(departmentIdStr);
            Department department = departmentDAO.getDepartmentById(departmentId);
            if (department == null) {
                JOptionPane.showMessageDialog(this, "Department not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JPanel panel = new JPanel(new GridLayout(0, 1));
            JTextField departmentNameField = new JTextField(department.getDepartmentName());
            JTextField userIdField = new JTextField(String.valueOf(department.getUserId()));

            panel.add(new JLabel("Department Name:"));
            panel.add(departmentNameField);
            panel.add(new JLabel("User ID:"));
            panel.add(userIdField);

            int result = JOptionPane.showConfirmDialog(this, panel, "Update Department", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String departmentName = departmentNameField.getText();
                    String userIdText = userIdField.getText();

                    if (isNullOrEmpty(departmentName)) {
                        throw new IllegalArgumentException("Department name cannot be null or empty.");
                    }
                    if (isNullOrEmpty(userIdText)) {
                        throw new IllegalArgumentException("User ID cannot be null or empty.");
                    }

                    int userId = Integer.parseInt(userIdText);

                    department.setDepartmentName(departmentName);
                    department.setUserId(userId);

                    departmentDAO.updateDepartment(department);
                    JOptionPane.showMessageDialog(this, "Department updated successfully!");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Error updating department: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Department ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDepartment() {
        // Create a dialog to confirm department deletion
        String departmentIdStr = JOptionPane.showInputDialog(this, "Enter Department ID to Delete:");
        if (departmentIdStr == null || departmentIdStr.trim().isEmpty()) {
            return;
        }

        try {
            int departmentId = Integer.parseInt(departmentIdStr);
            Department department = departmentDAO.getDepartmentById(departmentId);
            if (department == null) {
                JOptionPane.showMessageDialog(this, "Department not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this department?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                departmentDAO.deleteDepartment(departmentId);
                JOptionPane.showMessageDialog(this, "Department deleted successfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid Department ID!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void viewAllDepartments() {
        // Display all departments in a text area
        List<Department> departments = departmentDAO.getAllDepartments();
        if (departments.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No departments found!", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (Department department : departments) {
            sb.append(department).append("\n");
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "All Departments", JOptionPane.INFORMATION_MESSAGE);
    }
}
