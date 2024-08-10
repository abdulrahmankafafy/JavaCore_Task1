package org.example;

import org.example.MainFrame;
import org.example.dao.framework.DepartmentDAO;
import org.example.dao.implementation.DepartmentDAOImpl;
import org.example.dao.framework.UserProfileDAO;
import org.example.dao.implementation.UserProfileDAOImpl;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            UserProfileDAO userProfileDAO = new UserProfileDAOImpl();
            DepartmentDAO departmentDAO = new DepartmentDAOImpl();
            new MainFrame(userProfileDAO, departmentDAO);
        });
    }
}
